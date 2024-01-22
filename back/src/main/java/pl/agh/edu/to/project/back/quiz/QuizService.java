package pl.agh.edu.to.project.back.quiz;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.edu.to.project.back.award.Award;
import pl.agh.edu.to.project.back.award.AwardRepository;
import pl.agh.edu.to.project.back.chart.QuestionRepository;
import pl.agh.edu.to.project.back.chest.ChestRepository;
import pl.agh.edu.to.project.back.form.Form;
import pl.agh.edu.to.project.back.form.FormRepository;
import pl.agh.edu.to.project.back.strategy.Strategy;
import pl.agh.edu.to.project.back.strategy.StrategyRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final FormRepository formRepository;
    private final AwardRepository awardRepository;
    private final StrategyRepository strategyRepository;
    private final ChestRepository chestRepository;
    private final QuestionRepository questionRepository;

    public QuizService(QuizRepository quizRepository,
                       FormRepository formRepository,
                       AwardRepository awardRepository,
                       StrategyRepository strategyRepository,
                       ChestRepository chestRepository,
                       QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.formRepository = formRepository;
        this.awardRepository = awardRepository;
        this.strategyRepository = strategyRepository;
        this.chestRepository = chestRepository;
        this.questionRepository = questionRepository;
    }

    public List<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuiz(int quizId) {
        return quizRepository.findById(quizId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz " + quizId + " not found"));
    }

    @Transactional
    public ResponseEntity<Quiz> updateQuizStrategy(Quiz quiz) {
        Strategy strategy = quiz.getStrategyID();
        Quiz updatedQuiz = strategy.setStrategy(quiz, awardRepository, chestRepository);

        this.saveQuizWithUpdatedAwards(updatedQuiz);
        return ResponseEntity.ok(updatedQuiz);
    }

    @Transactional
    public void saveQuizWithUpdatedAwards(Quiz quiz) {
        setQuizAwards(quiz);

        formRepository.saveAll(quiz.getForms());
        quizRepository.save(quiz);
    }

    private void setQuizAwards(Quiz quiz) {
        List<Form> forms = quiz.getForms();
        List<Award> awards = awardRepository.findAll();

        for (Form form : forms) {
            if (form.getAward() != null){
                String awardName = form.getAward().getName();
                Optional<Award> award = awards.stream()
                        .filter(a -> a.getName().equals(awardName))
                        .findFirst();

                if (award.isPresent()){
                    form.setAward(award.get());
                } else {
                    form.setAward(null);
                }
            }else{
                form.setAward(null);
            }
        }
    }

    @Transactional
    public void saveQuizWithFormsAndAwards(Quiz quiz) {
        setQuizAwards(quiz);

        // TODO: default strategy obligatory
        if (quiz.getStrategyID() == null) {
            Optional<Strategy> strategy = strategyRepository.findById(10);
            strategy.ifPresent(quiz::setStrategyID);
        } else {
            Optional<Strategy> strategy = strategyRepository.findById(quiz.getStrategyID().getId());
            strategy.ifPresent(quiz::setStrategyID);
        }

        questionRepository.saveAll(quiz.getQuestions());
        formRepository.saveAll(quiz.getForms());
        quizRepository.save(quiz);
    }

}
