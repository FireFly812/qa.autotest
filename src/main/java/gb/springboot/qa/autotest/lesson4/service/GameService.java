package gb.springboot.qa.autotest.lesson4.service;

import gb.springboot.qa.autotest.lesson2.rest.dto.Game;

public interface GameService {
    void save(Game gameDto);

    void update(Game userDto);

    Game getById(Integer id);

    void delete(Integer id);
}
