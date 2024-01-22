package pl.agh.edu.to.project.back.strategy.strategyTypes;

import pl.agh.edu.to.project.back.chest.Chest;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.TopNumberAwardSelectionStrategy;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.StrategyType;

import java.util.LinkedHashMap;
import java.util.Map;

// strategy focuses on a top percentage of participants with best time and 100% correctness
public class TopPerformersAwardStrategy extends TopNumberAwardSelectionStrategy {
    public TopPerformersAwardStrategy(LinkedHashMap<Chest, Integer> parameters) {
        super(parameters);
    }

    @Override
    public LinkedHashMap<Chest, Integer>getNumOfWinnersForEachCategory(LinkedHashMap<Chest, Integer> parameters, int numOfQuizWinners) {
        LinkedHashMap<Chest, Integer> chestWinnersMap = new LinkedHashMap<>();
        int totalAllocatedWinners = 0;

        for (Map.Entry<Chest, Integer> entry : parameters.entrySet()) {
            int maxWinnersForChest = entry.getValue();
            int winnersForThisChest = Math.min(maxWinnersForChest, numOfQuizWinners - totalAllocatedWinners);
            chestWinnersMap.put(entry.getKey(), winnersForThisChest);
            totalAllocatedWinners += winnersForThisChest;

            if (totalAllocatedWinners >= numOfQuizWinners) {
                break;
            }
        }

        // In case there are still some quiz winners left unallocated,
        // add them to a 'no award' category.
        if (totalAllocatedWinners < numOfQuizWinners) {
            int remainingWinners = numOfQuizWinners - totalAllocatedWinners;
            Chest noAwardChest = new Chest("no_award_chest");
            chestWinnersMap.put(noAwardChest, remainingWinners);
        }

        return chestWinnersMap;
    }

    @Override
    public StrategyType getName() {
        return StrategyType.TOP_PERFORMERS;
    }
}
