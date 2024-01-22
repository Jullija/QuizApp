package pl.agh.edu.to.project.back.strategy.strategyTypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.agh.edu.to.project.back.chest.Chest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class TopPerformersAwardStrategyTest {
    private TopPerformersAwardStrategy strategy;
    private LinkedHashMap<Chest, Integer> parameters;
    private List<Chest> chestList;

    @BeforeEach
    public void setUp() {
        chestList = List.of(new Chest("Gold"), new Chest("Silver"), new Chest("Bronze"));

        parameters = new LinkedHashMap<>();
        parameters.put(chestList.get(0), 5);
        parameters.put(chestList.get(1), 10);
        parameters.put(chestList.get(2), 15);

        strategy = new TopPerformersAwardStrategy(parameters);
    }

    @Test
    public void numOfWinnersForEachCategoryWhenLessAwardsThanWinners() {
        // arrange
        int numOfQuizWinners = 40;
        Chest noAwardChest;

        // act
        LinkedHashMap<Chest, Integer> winnersPerCategory = strategy.getNumOfWinnersForEachCategory(parameters, numOfQuizWinners);
        List<Map.Entry<Chest, Integer>> winnersPerCatSet = new ArrayList<>(winnersPerCategory.entrySet());
        noAwardChest = winnersPerCatSet.get(winnersPerCatSet.size()-1).getKey();

        // assert
        assertEquals(5, (int)winnersPerCategory.get(chestList.get(0)));
        assertEquals(10, (int)winnersPerCategory.get(chestList.get(1)));
        assertEquals(15, (int)winnersPerCategory.get(chestList.get(2)));
        assertEquals(10, (int)winnersPerCategory.get(noAwardChest));
    }

    @Test
    public void numOfWinnersForEachCategoryWhenLessWinnersThanAwards() {
        // arrange
        int numOfQuizWinners = 10;

        // act
        LinkedHashMap<Chest, Integer> winnersPerCategory = strategy.getNumOfWinnersForEachCategory(parameters, numOfQuizWinners);

        // assert
        assertEquals(winnersPerCategory.size(), 2);
        assertEquals(5, (int)winnersPerCategory.get(chestList.get(0)));
        assertEquals(5, (int)winnersPerCategory.get(chestList.get(1)));
    }
}