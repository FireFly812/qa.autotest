package gb.springboot.qa.autotest.lesson4.repository;

import gb.springboot.qa.autotest.lesson4.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<GameEntity, Integer> {

    Optional<GameEntity> findByName(String name);
}
