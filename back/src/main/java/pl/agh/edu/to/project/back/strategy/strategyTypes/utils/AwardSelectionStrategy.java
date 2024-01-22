package pl.agh.edu.to.project.back.strategy.strategyTypes.utils;

import pl.agh.edu.to.project.back.award.Award;
import pl.agh.edu.to.project.back.award.AwardRepository;
import pl.agh.edu.to.project.back.form.Form;
import pl.agh.edu.to.project.back.quiz.Quiz;

import java.util.HashMap;
import java.util.List;

public interface AwardSelectionStrategy {
    Quiz setStrategy(Quiz quiz, AwardRepository awardRepository);

    void setAwards(List<Form> forms, HashMap<String, Award> awardHashMap);

    void setEmptyAwards(List<Form> forms, int startFromFormId, HashMap<String, Award> awardNameMap);

    void sortForms(List<Form> forms);

    StrategyType getName();
}
