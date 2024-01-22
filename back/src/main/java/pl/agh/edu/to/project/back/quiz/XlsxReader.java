package pl.agh.edu.to.project.back.quiz;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.agh.edu.to.project.back.award.Award;
import pl.agh.edu.to.project.back.chart.Question;
import pl.agh.edu.to.project.back.form.Form;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class XlsxReader {

    private String startTime = "0";
    private String endTime = "0";
    private String nickID = "-1";

    private String nick = "";
    private float points = -1;
    private String awardName = "";
    private int timestamp = -1;
    private final ArrayList<String> columnsNames;
    private final ArrayList<Integer> columnsIndex = new ArrayList<>();
    private ArrayList<String> priorities = new ArrayList<>();
    private ArrayList<Award> awardsList;

    private ArrayList<Integer> questionsIndexes;
    private ArrayList<Question> questions = new ArrayList<>();
    private HashMap<String, Set<String>> questionsWithAnswers = new HashMap<>();
    private HashMap<Question, Integer> numOfCorrectAnswersPerQuestion = new HashMap<>();

    public XlsxReader(@Value("${columns.names}") String[] columnsNames, ArrayList<Award> awardsList, @Value("${questions.indexes}") Integer[] questionsIndexes) {
        this.columnsNames = new ArrayList<>(Arrays.asList(columnsNames));
        this.awardsList = awardsList;
        this.questionsIndexes  = new ArrayList<>(Arrays.asList(questionsIndexes));
    }


    private String getTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString, formatter);
        Date date = Date.from(zonedDateTime.toInstant());

        int hours = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private int timeDifference(String startTime, String endTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.parse(startTime, timeFormatter);
        LocalTime time2 = LocalTime.parse(endTime, timeFormatter);

        long secondsDifference = time1.until(time2, ChronoUnit.SECONDS);

        return (int) secondsDifference;

    }

    private void findSpecificIndex(Row row, String name) {
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !columnsIndex.contains(i) && name.equals(cell.getStringCellValue())) {
                columnsIndex.add(i);
                break;
            }
        }
    }

    private void findIndexes(Row row) {
        for (String name : columnsNames) {
            findSpecificIndex(row, name);
        }
    }

    private void findQuestions(Row row){
        for (Integer idx : questionsIndexes){
            Cell cell = row.getCell(idx);
            if (cell != null){
                Question question = new Question(cell.getStringCellValue());
                questions.add(question);
                questionsWithAnswers.put(question.getTitle(), new HashSet<>());
            }
        }
    }

    // TODO: refactor
    private List<String> findAnswersForQuestion(Row row) {
        List<String> formAnswers = new ArrayList<>();
        for (int questionId = 0; questionId < questionsIndexes.size(); questionId++) {
            Cell cell = row.getCell(questionsIndexes.get(questionId));

            if (cell != null) {
                String answer = cell.getStringCellValue();
                if (!answer.isEmpty()) {
                    Question question = questions.get(questionId);
                    String[] splitAnswers = answer.split(";");
                    Collections.addAll(formAnswers, splitAnswers);

                    // Get unique answers for question
                    for (String splitAnswer : splitAnswers) {
                        questionsWithAnswers.get(question.getTitle()).add(splitAnswer.trim());
                    }

                    // Count correct answers for Question
                    Cell pointsCell = row.getCell(questionsIndexes.get(questionId) + 1);
                    if (pointsCell != null && pointsCell.getNumericCellValue() == 1.0) {
                        numOfCorrectAnswersPerQuestion.put(question, numOfCorrectAnswersPerQuestion.getOrDefault(question, 0) + 1);
                    }
                }
            }
        }
        return formAnswers;
    }

    private void setQuestionAnswersAndSuccessRate(int formSize) {
        for (Question question : questions) {
            question.setSuccessRate((numOfCorrectAnswersPerQuestion.get(question) * 100.0f) / formSize);
            question.setAnswers(questionsWithAnswers.get(question.getTitle()));
        }
    }

    private Iterator<Row> creatingIterator(FileInputStream fis) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);

        return sheet.iterator();
    }

    //TODO: final
    private void checkCell(Row row) {
        for (int index : columnsIndex) {
            Cell cell = row.getCell(index);

            if (index == columnsIndex.get(0)) {
                nickID = "[ " + cell + " ] ";
            } else if (index == columnsIndex.get(1)) {
                startTime = getTime(String.valueOf(cell.getDateCellValue()));
            } else if (index == columnsIndex.get(2)) {
                endTime = getTime(String.valueOf(cell.getDateCellValue()));
                timestamp = timeDifference(startTime, endTime);

            } else if (index == columnsIndex.get(3)) {
                points = Float.parseFloat(cell.toString());

            } else if (index == columnsIndex.get(4)) {
                nick = nickID + cell;

            } else {
                String fullName = cell.toString();
                createPriority(fullName);
                String[] allAwards = fullName.split("\\p{Punct}");
                awardName = allAwards.length > 0 ? allAwards[0].trim().toLowerCase() : fullName.trim().toLowerCase();
            }
        }
    }

    private void createPriority(String fullName) {
        priorities = new ArrayList<>();
        String[] allAwards = fullName.split(";");
        Pattern pattern = Pattern.compile("([^\\(]+)");

        for (String award : allAwards) {
            Matcher matcher = pattern.matcher(award);
            if (matcher.find()) {
                priorities.add(matcher.group(1).trim().toLowerCase());
            }
        }
    }

    private Award createAward(String name) {

        for (Award award : awardsList){
            if (award.getName().equals(name)){
                return award;
            }
        }
        return null;

    }

    public Quiz read(FileInputStream fis) throws IOException {
        Quiz quiz = new Quiz("New quiz");
        Iterator<Row> rowIterator = creatingIterator(fis);
        Row row1 = rowIterator.next();
        findIndexes(row1);
        findQuestions(row1);

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            checkCell(row);
            List<String> formAnswers = findAnswersForQuestion(row);
            quiz.addForm(new Form(nick, points, timestamp, endTime, createAward(awardName), priorities, formAnswers));
        }

        setQuestionAnswersAndSuccessRate(quiz.getForms().size());
        quiz.setQuestions(questions);
        QuizStrategyConfiguration quizStrategyConfiguration = new QuizStrategyConfiguration(quiz);
        quizStrategyConfiguration.SortingAndStrategy();

        return quiz;
    }
}
