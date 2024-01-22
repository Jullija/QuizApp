package pl.agh.edu.to.project.back.chest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChestRepository extends JpaRepository<Chest, Integer> {
    Chest findByName(String name);
}
