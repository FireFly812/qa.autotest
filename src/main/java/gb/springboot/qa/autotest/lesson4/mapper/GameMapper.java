package gb.springboot.qa.autotest.lesson4.mapper;

import gb.springboot.qa.autotest.lesson2.rest.dto.Game;
import gb.springboot.qa.autotest.lesson4.entity.GameEntity;

public interface  GameMapper {

    GameEntity dtoToEntity(Game gameDto);

    Game entityToDto(GameEntity gameEntity);
}
