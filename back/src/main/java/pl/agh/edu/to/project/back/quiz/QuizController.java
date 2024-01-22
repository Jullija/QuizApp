package pl.agh.edu.to.project.back.quiz;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.agh.edu.to.project.back.award.AwardService;
import pl.agh.edu.to.project.back.chart.Question;
import pl.agh.edu.to.project.back.chart.QuizStatisticsService;
import pl.agh.edu.to.project.back.strategy.Strategy;
import pl.agh.edu.to.project.back.strategy.StrategyService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "quizzes")
public class QuizController {
    private final QuizService quizService;
    private final FileUploader fileUploader;
    private final StrategyService strategyService;
    private final AwardService awardService;
    private final QuizStatisticsService quizStatisticsService;
    private final Exporter exporter;

    public QuizController(QuizService quizService, StrategyService strategyService, AwardService awardService, FileUploader fileUploader, QuizStatisticsService quizStatisticsService, Exporter exporter) {
        this.quizService = quizService;
        this.fileUploader = fileUploader;
        this.strategyService = strategyService;
        this.awardService = awardService;
        this.quizStatisticsService = quizStatisticsService;
        this.exporter = exporter;
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getQuizzes();
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        return this.quizService.getQuiz(id);
    }

    @GetMapping("/statistics/{id}")
    public List<Question> getQuizStatistics(@PathVariable int id) {
        return this.quizStatisticsService.getStatisticsForQuizAnswers(id);
    }

    @PutMapping("/updatedQuiz/{id}")
    public ResponseEntity<Quiz> updateQuizStrategy(@PathVariable int id, @RequestBody int strategyId) {
        Quiz updatedQuiz = this.quizService.getQuiz(id);
        Strategy newStrategy = this.strategyService.getStrategyById(strategyId);

        updatedQuiz.setStrategyID(newStrategy);
        return quizService.updateQuizStrategy(updatedQuiz);
    }

    @PostMapping("/inputFile")
    public ResponseEntity<Quiz> handleFileUpload(@RequestPart("file") MultipartFile file) {
        return this.fileUploader.uploadFile(file, quizService, awardService);
    }

    @GetMapping("/exportQuiz/{id}")
    public ResponseEntity<Quiz> exportQuiz(@PathVariable int id) throws IOException {
        Quiz quiz = this.quizService.getQuiz(id);
        this.exporter.generateFile(quiz);

        // TODO: handle file qenerate error and quiz not found error

        // TODO: response to do

        // TODO: response type change

        // TODO: change strategy error
        return ResponseEntity.ok(quiz);
    }
}

