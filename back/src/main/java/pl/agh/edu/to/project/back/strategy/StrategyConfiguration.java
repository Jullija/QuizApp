package pl.agh.edu.to.project.back.strategy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agh.edu.to.project.back.chest.Chest;
import pl.agh.edu.to.project.back.chest.ChestRepository;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.StrategyType;

import java.util.LinkedHashMap;
import java.util.List;

@Configuration
public class StrategyConfiguration {

    @Bean
    CommandLineRunner commandLineRunnerStrategy(StrategyRepository strategyRepository,
                                                ChestRepository chestRepository) {
        return args -> {
            if (strategyRepository.count() == 0) {
                List<Chest> chestList = chestRepository.findAll();

                // configuration for TopPerformersAwardStrategy
                LinkedHashMap<Chest, Integer> numericalParams = new LinkedHashMap<>();
                numericalParams.put(chestList.get(0), 1);
                numericalParams.put(chestList.get(1), 3);
                numericalParams.put(chestList.get(2), 10);

                // configuration for TopPercentageAwardStrategy
                LinkedHashMap<Chest, Integer> percentageParams = new LinkedHashMap<>();
                percentageParams.put(chestList.get(0), 20);
                percentageParams.put(chestList.get(1), 30);
                percentageParams.put(chestList.get(2), 40);

                Strategy defaultStrategy = new Strategy(
                        StrategyType.DEFAULT.name(),
                        StrategyType.DEFAULT);
                Strategy strategyByNumbers = new Strategy(
                        StrategyType.TOP_PERFORMERS.name(),
                        StrategyType.TOP_PERFORMERS,
                        numericalParams);
                Strategy strategyByPercents = new Strategy(
                        "TOP_PERCENTAGE",
                        StrategyType.TOP_PERCENTAGE,
                        percentageParams);

                strategyRepository.saveAll(List.of(defaultStrategy, strategyByNumbers, strategyByPercents));
            }
        };
    }
}
