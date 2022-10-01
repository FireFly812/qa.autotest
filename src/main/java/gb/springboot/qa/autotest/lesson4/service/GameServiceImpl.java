package gb.springboot.qa.autotest.lesson4.service;

import gb.springboot.qa.autotest.lesson2.rest.dto.Game;
import gb.springboot.qa.autotest.lesson4.entity.GameEntity;
import gb.springboot.qa.autotest.lesson4.mapper.GameMapper;
import gb.springboot.qa.autotest.lesson4.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final GameMapper gameMapper;
    private final GameRepository gameRepository;

    @Override
    @Transactional
    public void save(Game gameDto) {
        GameEntity entity = gameMapper.dtoToEntity(gameDto);
        gameRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(Game gameDto) {
        GameEntity entity = gameRepository.findById(gameDto.getId())
                .orElseThrow();
        entity.setName(gameDto.getName());
        entity.setGenre(gameDto.getGenre());
        entity.setMmo(gameDto.isMmo());
    }

    @Override
    @Transactional(readOnly = true)
    public Game getById(Integer id) {
        GameEntity entity = gameRepository.findById(id)
                .orElseThrow();
        return gameMapper.entityToDto(entity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        gameRepository.deleteById(id);
    }
}
