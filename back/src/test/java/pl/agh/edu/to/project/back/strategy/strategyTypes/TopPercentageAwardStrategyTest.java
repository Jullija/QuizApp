package pl.agh.edu.to.project.back.strategy.strategyTypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.agh.edu.to.project.back.chest.Chest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class TopPercentageAwardStrategyTest {
    private TopPercentageAwardStrategy strategy;
    private LinkedHashMap<Chest, Integer> parameters;
    private List<Chest> chestList;

    @BeforeEach
    public void setUp() {
        chestList = List.of(new Chest("Gold"), new Chest("Silver"), new Chest("Bronze"));

        parameters = new LinkedHashMap<>();
        parameters.put(chestList.get(0), 20);
        parameters.put(chestList.get(1), 50);
        parameters.put(chestList.get(2), 30);

        strategy = new TopPercentageAwardStrategy(parameters);
    }

    @Test
    public void numOfWinnersForEachCategoryWhenPercentagesSumUpTo100() {
        // arrange
        int numOfQuizWinners = 85;
        Chest noAwardChest;

        // act
        LinkedHashMap<Chest, Integer> winnersPerCategory = strategy.getNumOfWinnersForEachCategory(parameters, numOfQuizWinners);
        List<Map.Entry<Chest, Integer>> winnersPerCatSet = new ArrayList<>(winnersPerCategory.entrySet());
        noAwardChest = winnersPerCatSet.get(winnersPerCatSet.size()-1).getKey();

        // assert
        assertEquals(17, (int)winnersPerCategory.get(chestList.get(0)));
        assertEquals(42, (int)winnersPerCategory.get(chestList.get(1)));
        assertEquals(25, (int)winnersPerCategory.get(chestList.get(2)));
        assertEquals(1, (int)winnersPerCategory.get(noAwardChest));
    }

    @Test
    public void numOfWinnersForEachCategoryWhenPercentagesSumExceeds100() {
        // arrange
        Chest additionalChest = new Chest("Platinum");
        parameters.put(additionalChest, 20);
        int numOfQuizWinners = 100;

        // act
        LinkedHashMap<Chest, Integer> winnersPerCategory = strategy.getNumOfWinnersForEachCategory(parameters, numOfQuizWinners);

        // assert
        assertEquals(winnersPerCategory.size(), 3);
        assertEquals(20, (int)winnersPerCategory.get(chestList.get(0)));
        assertEquals(50, (int)winnersPerCategory.get(chestList.get(1)));
        assertEquals(30, (int)winnersPerCategory.get(chestList.get(2)));
    }
}