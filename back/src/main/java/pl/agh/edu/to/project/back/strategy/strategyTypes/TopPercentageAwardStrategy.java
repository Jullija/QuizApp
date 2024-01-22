package pl.agh.edu.to.project.back.strategy.strategyTypes;

import pl.agh.edu.to.project.back.chest.Chest;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.TopNumberAwardSelectionStrategy;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.StrategyType;

import java.util.*;

// strategy focuses on a number of top quiz performers (best time and 100% correctness)
public class TopPercentageAwardStrategy extends TopNumberAwardSelectionStrategy {
    public TopPercentageAwardStrategy(LinkedHashMap<Chest, Integer> parameters) {
        super(parameters);
    }

    @Override
    public LinkedHashMap<Chest, Integer>getNumOfWinnersForEachCategory(LinkedHashMap<Chest, Integer> parameters, int numOfQuizWinners) {
        LinkedHashMap<Chest, Integer> chestWinnersMap = new LinkedHashMap<>();
        int totalAllocatedWinners = 0;

        for (Map.Entry<Chest, Integer> entry : parameters.entrySet()) {
            Chest chest = entry.getKey();
            int percentageForChest = entry.getValue();
            int winnersForThisChest = (int) (numOfQuizWinners * (percentageForChest / 100.0));

            // Adjust if total winners exceed numOfQuizWinners
            winnersForThisChest = Math.min(winnersForThisChest, numOfQuizWinners - totalAllocatedWinners);
            chestWinnersMap.put(chest, winnersForThisChest);
            totalAllocatedWinners += winnersForThisChest;

            if (totalAllocatedWinners >= numOfQuizWinners) {
                break;
            }
        }

        // In case there are still some quiz winners left unallocated,
        // add them to a 'no award' category.
        int unallocatedWinners = numOfQuizWinners - totalAllocatedWinners;
        if (unallocatedWinners > 0) {
            Chest noAwardChest = new Chest("no_award_chest");
            chestWinnersMap.put(noAwardChest, unallocatedWinners);
        }

        return chestWinnersMap;
    }

    @Override
    public StrategyType getName() {
        return StrategyType.TOP_PERCENTAGE;
    }
}
