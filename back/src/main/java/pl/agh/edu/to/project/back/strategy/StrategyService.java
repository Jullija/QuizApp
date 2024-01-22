package pl.agh.edu.to.project.back.strategy;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.StrategyType;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StrategyService {
    private final StrategyRepository strategyRepository;

    public StrategyService(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    public List<Strategy> getStrategies() {
        return strategyRepository.findAll();
    }

    public Strategy getStrategyById(int id) {
        return this.strategyRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Strategy " + id + " not found"));
    }

    public List<StrategyType> getStrategyTypes() {
        List<StrategyType> result = strategyRepository.findAll().stream()
                .map(Strategy::getType)
                .distinct()
                .collect(Collectors.toList());

        result.remove(StrategyType.DEFAULT);
        return result;
    }

    public Strategy saveStrategy(Strategy strategy) {
        strategyRepository.save(strategy);
        strategy.setStrategyName("added strategy " + strategy.getId());

        return this.strategyRepository.save(strategy);
    }
}
