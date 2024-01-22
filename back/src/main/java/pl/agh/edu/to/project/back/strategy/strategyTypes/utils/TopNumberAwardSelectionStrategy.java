package pl.agh.edu.to.project.back.strategy.strategyTypes.utils;

import pl.agh.edu.to.project.back.award.Award;
import pl.agh.edu.to.project.back.award.AwardRepository;
import pl.agh.edu.to.project.back.chest.Chest;
import pl.agh.edu.to.project.back.form.Form;
import pl.agh.edu.to.project.back.quiz.Quiz;

import java.util.*;

public abstract class TopNumberAwardSelectionStrategy implements AwardSelectionStrategy {
    protected LinkedHashMap<Chest, Integer> parameters;

    public TopNumberAwardSelectionStrategy(LinkedHashMap<Chest, Integer> parameters) {
        this.parameters = parameters;
    }

    public Quiz setStrategy(Quiz quiz, AwardRepository awardRepository) {
        List<Form> forms = quiz.getForms();

        sortForms(forms);

        HashMap<String, Award> awardHashMap = getAwardsChestList(awardRepository);

        setAwards(forms, awardHashMap);

        quiz.setForms(forms);

        return quiz;
    }

    public void setAwards(List<Form> forms, HashMap<String, Award> awardHashMap) {
        int participantsWithCorrectAnswer = getNumOfFormsWithCorrectAnswers(forms);

        LinkedHashMap<Chest, Integer> numOfAwardWinnersForEachChest = getNumOfWinnersForEachCategory(parameters, participantsWithCorrectAnswer);

        int firstFormIdForCategory = 0;

        // Iterate over the chest categories and its number of winners
        for (Map.Entry<Chest, Integer> entry : numOfAwardWinnersForEachChest.entrySet()) {
            Chest chestCategory = entry.getKey();
            int numOfCategoryWinners = entry.getValue();

            setAwardsForThisCategoryWinners(chestCategory, firstFormIdForCategory, numOfCategoryWinners, forms, awardHashMap);
            firstFormIdForCategory += numOfCategoryWinners;
        }

        setEmptyAwards(forms, firstFormIdForCategory, awardHashMap);
    }


    public void setEmptyAwards(List<Form> forms, int startFromFormId, HashMap<String, Award> awardHashMap) {
        int numOfQuizParticipants = forms.size();

        for (int i = startFromFormId; i < numOfQuizParticipants; i++) {
            forms.get(i).setAward(awardHashMap.get("-"));
        }
    }


    // set awards based on user's preferences and category (chest)
    public void setAwardsForThisCategoryWinners(Chest chest, int formId, int numOfWinners, List<Form> forms, HashMap<String, Award> awardHashMap) {
        int numOfQuizParticipants = forms.size();

        for (int i = formId; i < Math.min(numOfWinners + formId, numOfQuizParticipants); i++) {
            Form form = forms.get(i);
            form.setAward(getFormAwardPrioritiesByChest(chest, form.getAwardPriority(), awardHashMap));
        }
    }

    // sort by points and endTime
    public void sortForms(List<Form> forms) {
        forms.sort(Comparator
                .comparing(Form::getPoints, Comparator.reverseOrder())
                .thenComparing(Form::getEndTime));
    }

    public int getNumOfFormsWithCorrectAnswers(List<Form> forms) {
        // TODO: get real max points
        float maxPoints = forms.get(0).getPoints();
        int correctAnswers = 0;

        for (Form form : forms) {
            if (form.getPoints() == maxPoints) {
                correctAnswers += 1;
            }
        }

        return correctAnswers;
    }

    // get awards from db
    public HashMap<String, Award> getAwardsChestList(AwardRepository awardRepository) {
        List<Award> awardList = awardRepository.findAll();

        HashMap<String, Award> awardHashMap = new HashMap<>();

        for (Award award : awardList) {
            awardHashMap.put(award.getName(), award);
        }

        return awardHashMap;
    }

    // calculate award for user based on their preferences (awardPriorities) and chest they won
    public Award getFormAwardPrioritiesByChest(Chest chest, List<String> awardPriorities, HashMap<String, Award> awards) {
        for (String awardName : awardPriorities) {
            Award award = awards.get(awardName);
            if (award != null && award.getChest().contains(chest)) {
                return award;
            }
        }

        return awards.get("-");
    }

    public abstract LinkedHashMap<Chest, Integer> getNumOfWinnersForEachCategory(LinkedHashMap<Chest, Integer> parameters, int numOfWinners);
}
