package pl.agh.edu.to.project.back.award;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.edu.to.project.back.exception.AwardAlreadyExists;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AwardService {
    private final AwardRepository awardRepository;

    public AwardService(AwardRepository awardRepository) {
        this.awardRepository = awardRepository;

    }

    public List<Award> getAwards() {
        return awardRepository.findAll();
    }

    public Award saveAward(Award award) throws AwardAlreadyExists {
        if (awardRepository.findByName(award.getName()) != null) {
            throw new AwardAlreadyExists("Award " + award.getName() + " already exists");
        }

        return awardRepository.save(award);
    }
}
