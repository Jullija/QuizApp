package pl.agh.edu.to.project.back.strategy;

import org.springframework.web.bind.annotation.*;
import pl.agh.edu.to.project.back.chest.Chest;
import pl.agh.edu.to.project.back.chest.ChestRepository;
import pl.agh.edu.to.project.back.strategy.strategyTypes.utils.StrategyType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "strategies")
public class StrategyController {
    private final StrategyService strategyService;
    private final ChestRepository chestRepository;

    public StrategyController(StrategyService strategyService, ChestRepository chestRepository) {
        this.strategyService = strategyService;
        this.chestRepository = chestRepository;
    }

    @GetMapping()
    public List<Strategy> getAllStrategies() {
        return strategyService.getStrategies();
    }

    @GetMapping("/types")
    public List<StrategyType> getAllStrategyTypes() {
        return strategyService.getStrategyTypes();
    }

    @PostMapping("/{strategyType}")
    public Strategy addStrategy(@PathVariable String strategyType, @RequestBody Map<Chest, Integer> mapParameters) {
        System.out.println(mapParameters);

        Map<Chest, Integer> map2 = new HashMap<>();
        for(Map.Entry<Chest, Integer> entry : mapParameters.entrySet()) {
            Chest chest = entry.getKey();
            Integer number = entry.getValue();
            map2.put(chestRepository.findByName(chest.getName()), number);
        }

        Strategy strategy = new Strategy("", StrategyType.valueOf(strategyType), map2);
        return this.strategyService.saveStrategy(strategy);
    }
}
