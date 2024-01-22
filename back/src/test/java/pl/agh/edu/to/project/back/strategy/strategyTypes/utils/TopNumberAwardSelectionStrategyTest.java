package pl.agh.edu.to.project.back.strategy.strategyTypes.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.agh.edu.to.project.back.award.Award;
import pl.agh.edu.to.project.back.chest.Chest;
import pl.agh.edu.to.project.back.form.Form;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


class TopNumberAwardSelectionStrategyTest {

    private static class TestTopNumberAwardSelectionStrategy extends TopNumberAwardSelectionStrategy {

        public TestTopNumberAwardSelectionStrategy(LinkedHashMap<Chest, Integer> parameters) {
            super(parameters);
        }

        @Override
        public LinkedHashMap<Chest, Integer> getNumOfWinnersForEachCategory(LinkedHashMap<Chest, Integer> parameters, int numOfWinners) {
            return parameters;
        }

        @Override
        public StrategyType getName() {
            return null;
        }
    }

    private TestTopNumberAwardSelectionStrategy strategy;
    private HashMap<String, Award> awards;
    private Chest goldenChest;
    private Chest silverChest;
    private Chest bronzeChest;

    @BeforeEach
    public void setUp() {
        this.createChests();
        this.createAwards();

        // strategy params
        LinkedHashMap<Chest, Integer> parameters = new LinkedHashMap<>();
        parameters.put(goldenChest, 5);
        parameters.put(silverChest, 10);
        parameters.put(bronzeChest, 15);

        strategy = new TestTopNumberAwardSelectionStrategy(parameters);
    }

    @Test
    public void testSortForms() {
        // arrange
        List<Form> forms = this.createForms();
        boolean correctlySorted = true;

        // act
        strategy.sortForms(forms);

        //assert
        for (int i = 1; i < forms.size(); i++) {
            Form prev = forms.get(i - 1);
            Form curr = forms.get(i);

            if (prev.getPoints() < curr.getPoints() ||
                    (prev.getPoints() == curr.getPoints() && prev.getEndTime().compareTo(curr.getEndTime()) > 0)) {
                correctlySorted = false;
                break;
            }
        }

        assertTrue(correctlySorted);
    }

    @Test
    public void testSetAwards() {
        // arrange
        List<Form> forms = this.createForms();
        strategy.sortForms(forms);

        // act
        strategy.setAwards(forms, awards);

        // assert

        // 'marchewka' award for first 5 winners (goldenChest)
        assertEquals("marchewka", forms.get(0).getAward().getName());
        assertEquals("marchewka", forms.get(4).getAward().getName());

        // 'sianko' award for next 10 winners (silverChest)
        assertEquals("sianko", forms.get(5).getAward().getName());
        assertEquals("sianko", forms.get(14).getAward().getName());

        // 'weterynarz' award for next 15 winners (bronzeChest)
        assertEquals("weterynarz", forms.get(15).getAward().getName());
        assertEquals("weterynarz", forms.get(29).getAward().getName());

        // remaining winners should get '-'
        assertEquals("-", forms.get(30).getAward().getName());
        assertEquals("-", forms.get(39).getAward().getName());
    }

    @Test
    public void testGetFormAwardPrioritiesByChest() {
        // arrange
        List<String> awardPriorities = List.of("marchewka", "sianko", "weterynarz");

        // act
        Award selectedFormAwardByGolden = strategy.getFormAwardPrioritiesByChest(goldenChest, awardPriorities, awards);
        Award selectedFormAwardBySilver = strategy.getFormAwardPrioritiesByChest(silverChest, awardPriorities, awards);
        Award selectedFormAwardByBronze = strategy.getFormAwardPrioritiesByChest(bronzeChest, awardPriorities, awards);

        // assert
        assertEquals("marchewka", selectedFormAwardByGolden.getName());
        assertEquals("sianko", selectedFormAwardBySilver.getName());
        assertEquals("weterynarz", selectedFormAwardByBronze.getName());
    }

    @Test
    public void testGetFormAwardPrioritiesByChestWhenNoAwardAndChestMatch() {
        // arrange
        List<String> awardPriorities = List.of("marchewka");

        // act
        Award selectedFormAward = strategy.getFormAwardPrioritiesByChest(bronzeChest, awardPriorities, awards);

        // assert
        assertEquals("-", selectedFormAward.getName());
    }

    private void createAwards() {
        awards = new HashMap<>();
        awards.put("marchewka", new Award("marchewka", "", List.of(goldenChest)));
        awards.put("sianko", new Award("sianko", "", List.of(goldenChest, silverChest)));
        awards.put("weterynarz", new Award("weterynarz", "", List.of(goldenChest, silverChest, bronzeChest)));
        awards.put("-", new Award("-", "", Collections.emptyList()));
    }

    private void createChests() {
        goldenChest = new Chest("Gold");
        silverChest = new Chest("Silver");
        bronzeChest = new Chest("Bronze");
    }

    private List<Form> createForms() {
        int numOfWinners = 80;
        List<Form> forms = new ArrayList<>();
        Random random = new Random();
        List<String> awardPriorities = List.of("marchewka", "sianko", "weterynarz");

        for (int i = 0; i < numOfWinners; i++) {
            Form form = new Form();
            form.setPoints(i%2);
            form.setEndTime(String.valueOf(1000 + random.nextInt(1000)));
            form.setAwardPriority(awardPriorities);
            forms.add(form);
        }

        return forms;
    }

}
