package pl.agh.edu.to.project.back.chest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.edu.to.project.back.award.Award;
import pl.agh.edu.to.project.back.exception.AwardAlreadyExists;
import pl.agh.edu.to.project.back.exception.ChestAlreadyExists;

import java.util.List;

@Service
public class ChestService {

    private final ChestRepository chestRepository;

    public ChestService(ChestRepository chestRepository) {
        this.chestRepository = chestRepository;
    }

    public List<Chest> getChests() {
        return chestRepository.findAll();
    }

    public Chest saveChest(Chest chest) throws ChestAlreadyExists {
        if (chestRepository.findByName(chest.getName()) != null) {
            throw new ChestAlreadyExists("Chest " + chest.getName() + " already exists");
        }

        return chestRepository.save(chest);
    }

}
