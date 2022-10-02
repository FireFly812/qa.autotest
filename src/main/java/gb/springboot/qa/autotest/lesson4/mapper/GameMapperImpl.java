package gb.springboot.qa.autotest.lesson4.mapper;


import gb.springboot.qa.autotest.lesson2.rest.dto.Game;
import gb.springboot.qa.autotest.lesson4.entity.GameEntity;
import org.springframework.stereotype.Component;

@Component
public class GameMapperImpl implements GameMapper {

    @Override
    public GameEntity dtoToEntity(Game gameDto) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName(gameDto.getName());
        gameEntity.setGenre(gameDto.getGenre());
        gameEntity.setMmo(gameDto.isMmo());
        return gameEntity;
    }

    @Override
    public Game entityToDto(GameEntity gameEntity) {
        Game game = new Game();
        game.setId(gameEntity.getId());
        game.setName(gameEntity.getName());
        game.setGenre(gameEntity.getGenre());
        game.setMmo(gameEntity.isMmo());
        return game;
    }
}
