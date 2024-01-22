package pl.agh.edu.to.project.back.strategy;

import pl.agh.edu.to.project.back.award.AwardRepository;
import pl.agh.edu.to.project.back.chest.Chest;
import pl.agh.edu.to.project.back.chest.ChestRepository;
import pl.agh.edu.to.project.back.quiz.Quiz;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.AwardSelectionStrategy;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.StrategyParamsConverter;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.StrategyType;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Strategy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)

    private StrategyType type;
    private String parameters;
    @Transient
    private AwardSelectionStrategy strategyAwardSelector;
    @Transient
    private StrategyParamsConverter strategyParamsConverter = new StrategyParamsConverter();

    public Strategy() {
    }

    public Strategy(String name, StrategyType strategyType){
        this.name = name;
        this.type = strategyType;
        this.parameters = "";
    }
    public Strategy(String name, StrategyType strategyType, Map<Chest, Integer> parameters){
        this.name = name;
        this.type = strategyType;
        this.parameters = strategyParamsConverter.convertToDatabaseColumn(parameters);
    }

    public Strategy(String name, StrategyType strategyType, String parameters){
        this.name = name;
        this.type = strategyType;
        this.parameters = parameters;
    }

    public Quiz setStrategy(Quiz quiz, AwardRepository awardRepository, ChestRepository chestRepository) {
        LinkedHashMap<Chest, Integer> convertedParams = new LinkedHashMap<>();

        if (!this.parameters.isEmpty()) {
            convertedParams = new StrategyParamsConverter(chestRepository).convertToEntityAttribute(this.parameters);
        }
        this.strategyAwardSelector = type.getAwardSelectionStrategy(convertedParams);

        if (this.strategyAwardSelector == null) {
            throw new IllegalStateException("StrategyAwardSelector is empty - unable to set strategy for quiz " + quiz.getId());
        }
        return this.strategyAwardSelector.setStrategy(quiz, awardRepository);
    }

    public int getId() {
        return id;
    }

    public void setId(int strategyID) {
        this.id = strategyID;
    }

    public String getName() {
        return name;
    }

    public StrategyType getType() {
        return type;
    }

    public void setStrategyName(String name) {
        this.name = name;
    }
}
