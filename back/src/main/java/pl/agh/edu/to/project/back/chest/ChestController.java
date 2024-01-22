package pl.agh.edu.to.project.back.chest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.edu.to.project.back.exception.ChestAlreadyExists;

import java.util.List;

@RestController
@RequestMapping(path = "chests")
public class ChestController {

    private final ChestService chestService;

    public ChestController(ChestService chestService) {
        this.chestService = chestService;
    }

    @GetMapping
    public List<Chest> getAllChests() {
        return chestService.getChests();
    }

    @PostMapping("/chest")
    public Chest createChest(@RequestBody Chest chest) throws ChestAlreadyExists {
        if (chest.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add a chest without name");
        }

        return this.chestService.saveChest(chest);
    }
}
