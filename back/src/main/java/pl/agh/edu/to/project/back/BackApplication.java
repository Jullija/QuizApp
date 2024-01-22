package pl.agh.edu.to.project.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import pl.agh.edu.to.project.back.award.AwardConfiguration;
import pl.agh.edu.to.project.back.chest.ChestConfiguration;
import pl.agh.edu.to.project.back.quiz.Exporter;
import pl.agh.edu.to.project.back.strategy.StrategyConfiguration;

@SpringBootApplication(scanBasePackages = {
        "pl.agh.edu.to.project.back.configuration",
        "pl.agh.edu.to.project.back.quiz",
        "pl.agh.edu.to.project.back.form",
        "pl.agh.edu.to.project.back.chest",
        "pl.agh.edu.to.project.back.award",
        "pl.agh.edu.to.project.back.strategy",
        "pl.agh.edu.to.project.back.chart"
})
@Import({ChestConfiguration.class, AwardConfiguration.class, StrategyConfiguration.class})
public class BackApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

}
