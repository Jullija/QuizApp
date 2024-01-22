package pl.agh.edu.to.project.back.strategy.strategyTypes;

import pl.agh.edu.to.project.back.award.Award;
import pl.agh.edu.to.project.back.award.AwardRepository;
import pl.agh.edu.to.project.back.form.Form;
import pl.agh.edu.to.project.back.quiz.Quiz;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.AwardSelectionStrategy;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.StrategyType;

import java.util.HashMap;
import java.util.List;

public class DefaultStrategy implements AwardSelectionStrategy {
    @Override
    public Quiz setStrategy(Quiz quiz, AwardRepository awardRepository) {
        return quiz;
    }

    @Override
    public void setAwards(List<Form> forms, HashMap<String, Award> awardHashMap) {

    }

    @Override
    public void setEmptyAwards(List<Form> forms, int startFromFormId, HashMap<String, Award> awardHashMap) {

    }

    @Override
    public void sortForms(List<Form> forms) {

    }

    @Override
    public StrategyType getName() {
        return StrategyType.DEFAULT;
    }
}
