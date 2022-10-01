package gb.springboot.qa.autotest.lesson4;

import gb.springboot.qa.autotest.lesson2.rest.dto.Game;
import gb.springboot.qa.autotest.lesson4.entity.GameEntity;
import gb.springboot.qa.autotest.lesson4.mapper.GameMapper;
import gb.springboot.qa.autotest.lesson4.mapper.GameMapperImpl;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class GameMapperUnitTest {
    GameMapper gameMapper = new GameMapperImpl();

    @Test
    void convertEntityToDtoTest() {
        //pre-condition
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("Duke Nukem");
        gameEntity.setGenre("shooter");
        gameEntity.setMmo(false);
        gameEntity.setId(10);

        //step
        Game game = gameMapper.entityToDto(gameEntity);

        //assert
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(game.getId()).isEqualTo(gameEntity.getId());
            s.assertThat(game.getName()).isEqualTo(gameEntity.getName());
            s.assertThat(game.getGenre()).isEqualTo(gameEntity.getGenre());
            s.assertThat(game.isMmo()).isEqualTo(gameEntity.isMmo());
        });
    }

    @Test
    void convertDtoToEntityTest() {
        //pre-condition
        Game game = new Game();
        game.setName("Duke Nukem");
        game.setGenre("shooter");
        game.setMmo(false);

        //step
        GameEntity gameEntity = gameMapper.dtoToEntity(game);

        //assert
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(gameEntity.getName()).isEqualTo(game.getName());
            s.assertThat(gameEntity.getGenre()).isEqualTo(game.getGenre());
            s.assertThat(gameEntity.isMmo()).isEqualTo(game.isMmo());
        });
    }
}
