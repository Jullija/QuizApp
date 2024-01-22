package pl.agh.edu.to.project.back.chest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChestConfiguration {

    @Bean
    CommandLineRunner commandLineRunner2(ChestRepository chestRepository) {
        return args -> {
            if (chestRepository.count() == 0) {
                Chest goldChest = new Chest("złota");
                Chest silverChest = new Chest("srebrna");
                Chest brownChest = new Chest("brązowa");
                chestRepository.saveAll(List.of(goldChest, silverChest, brownChest));
            }
        };

    }
}
