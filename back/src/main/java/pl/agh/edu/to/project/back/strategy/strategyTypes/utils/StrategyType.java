package pl.agh.edu.to.project.back.strategy.strategyTypes.utils;

import pl.agh.edu.to.project.back.chest.Chest;
import pl.agh.edu.to.project.back.strategy.strategyTypes.DefaultStrategy;
import pl.agh.edu.to.project.back.strategy.strategyTypes.TopPercentageAwardStrategy;
import pl.agh.edu.to.project.back.strategy.strategyTypes.TopPerformersAwardStrategy;

import java.util.LinkedHashMap;

public enum StrategyType {
    TOP_PERFORMERS,
    TOP_PERCENTAGE,
    DEFAULT;

    public AwardSelectionStrategy getAwardSelectionStrategy(LinkedHashMap<Chest, Integer> parameters) {
        switch (this.ordinal()) {
            case 0 -> {
                return new TopPerformersAwardStrategy(parameters);
            }
            case 1 -> {
                return new TopPercentageAwardStrategy(parameters);
            }
            case 2 -> {
                return new DefaultStrategy();
            }
            default -> {
                return null;
            }
        }
    }
}
