package pl.agh.edu.to.project.back.chart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    private List<Question> save(List<Question> questions) {
        return questionRepository.saveAll(questions);
    }
}
