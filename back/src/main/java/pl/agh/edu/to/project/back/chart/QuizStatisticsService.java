package pl.agh.edu.to.project.back.chart;

import org.springframework.stereotype.Service;
import pl.agh.edu.to.project.back.form.Form;
import pl.agh.edu.to.project.back.quiz.Quiz;
import pl.agh.edu.to.project.back.quiz.QuizRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizStatisticsService {
    private final QuizRepository quizRepository;

    public QuizStatisticsService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    // TODO: tests
//    @Async
    public List<Question> getStatisticsForQuizAnswers(int id) {
        Quiz quiz = quizRepository.getReferenceById(id);
        List<Form> forms = quiz.getForms();
        List<Question> questionsList = quiz.getQuestions();

        Map<String, Integer> answerCounts = countAnswerSelections(forms);
        calculateAnswerSelectionRatio(questionsList, answerCounts, forms.size());

        return questionsList;
    }

    private Map<String, Integer> countAnswerSelections(List<Form> forms) {
        Map<String, Integer> answerCounts = new HashMap<>();

        for (Form form : forms) {
            List<String> formSelectedAnswers = form.getAnswers();
            for (String answer : formSelectedAnswers) {
                answerCounts.put(answer, answerCounts.getOrDefault(answer, 0) + 1);
            }
        }

        return answerCounts;
    }

    private void calculateAnswerSelectionRatio(List<Question> questions, Map<String, Integer> answerCounts, int formsSize) {
        HashMap<String, Question> QAMap = getQAMap(questions);

        for (Map.Entry<String, Integer> entry : answerCounts.entrySet()) {
            String answerText = entry.getKey();
            int count = entry.getValue();
            float percentage = (count * 100.0f) / formsSize;

            Question question = QAMap.get(answerText);
            if (question != null) {
                question.addAnswerForStats(new Answer(answerText, percentage));
            }
        }
    }

    // Get Question object related to answer
    private HashMap<String, Question> getQAMap(List<Question> questions) {
        HashMap<String, Question> QAMap = new HashMap<>();

        for (Question question : questions) {
            for (String answer : question.getAnswers()) {
                QAMap.put(answer, question);
            }
        }
        return QAMap;
    }
}
