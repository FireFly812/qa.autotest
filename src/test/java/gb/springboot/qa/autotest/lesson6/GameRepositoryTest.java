package gb.springboot.qa.autotest.lesson6;

import gb.springboot.qa.autotest.lesson4.entity.GameEntity;
import gb.springboot.qa.autotest.lesson4.repository.GameRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void getGameByNameAndGenreTest() {

        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("Duke Nukem");
        gameEntity.setGenre("shooter");
        gameEntity.setMmo(false);

        testEntityManager.persistAndFlush(gameEntity);
        Optional<GameEntity> entity = gameRepository.findByNameAndGenre("Duke Nukem","shooter");
        Assertions.assertThat(entity.isPresent()).isTrue();

        SoftAssertions.assertSoftly(s -> {
            s.assertThat(entity.get().getName()).isEqualTo("Duke Nukem");
            s.assertThat(entity.get().getGenre()).isEqualTo("shooter");
            s.assertThat(entity.get().isMmo()).isEqualTo(false);
        });
    }
}
