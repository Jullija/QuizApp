package pl.agh.edu.to.project.back.award;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.edu.to.project.back.exception.AwardAlreadyExists;

import java.util.List;

@RestController
@RequestMapping(path = "awards")
public class AwardController {

    private final AwardService awardService;

    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @GetMapping
    public List<Award> getAllAwards() {
        return awardService.getAwards();
    }

    @PostMapping()
    public Award createAward(@RequestBody Award award) throws AwardAlreadyExists {
        if (award.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add an award without name");
        } else if (award.getChest().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add an award without chest");
        }

        return this.awardService.saveAward(award);
    }
}
