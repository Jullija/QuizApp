package pl.agh.edu.to.project.back.quiz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.edu.to.project.back.award.Award;
import pl.agh.edu.to.project.back.award.AwardRepository;
import pl.agh.edu.to.project.back.award.AwardService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

@Service
public class FileUploader {

    @Value("${columns.names}")
    private String[] columnsNames;

    @Value("${questions.indexes}")
    private Integer[] questionsIndexes;
    private ArrayList<Award> awardsList;


    private void createAwards(AwardService awardService){
        awardsList = (ArrayList<Award>) awardService.getAwards();
    }


    public ResponseEntity<Quiz> uploadFile(MultipartFile file, QuizService quizService, AwardService awardService) {
        if (file.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot upload an empty file");
        }

        try {
            File tempFile = File.createTempFile("temp", null);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            createAwards(awardService);

            XlsxReader reader = new XlsxReader(columnsNames, awardsList, questionsIndexes);
            try (FileInputStream fis = new FileInputStream(tempFile)) {
                Quiz quiz = reader.read(fis);
                quizService.saveQuizWithFormsAndAwards(quiz);

                return ResponseEntity.ok().body(quiz);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
