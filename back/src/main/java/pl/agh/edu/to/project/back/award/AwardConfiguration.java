package pl.agh.edu.to.project.back.award;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agh.edu.to.project.back.chest.Chest;
import pl.agh.edu.to.project.back.chest.ChestRepository;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AwardConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(AwardRepository awardRepository, ChestRepository chestRepository) {
        return args -> {
            if (awardRepository.count() == 0) {
                Chest goldChest = chestRepository.findByName("złota");
                Chest silverChest = chestRepository.findByName("srebrna");
                Chest brownChest = chestRepository.findByName("brązowa");

                Award carrot = new Award("marchewka", "+10% do lab", List.of(goldChest, silverChest));
                Award labCarrot = new Award("marchewka lab", "+10% do lab", List.of(goldChest, silverChest));
                Award projCarrot = new Award("marchewka projektowa", "+10% do sumy za projekt (maks. 40%)", List.of(goldChest, silverChest));
                Award medicine = new Award("lekarstwo", "odrobienie 2pkt lab", List.of(goldChest, silverChest, brownChest));
                Award doctor = new Award("weterynarz", "odrobienie 1 kartkówki", List.of(goldChest, silverChest, brownChest));
                Award hay = new Award("rabat na sianko", "darmowa kartkówka", List.of(goldChest));


                awardRepository.saveAll(List.of(labCarrot, projCarrot, carrot, medicine, doctor, hay));
            }
        };
    }
}
