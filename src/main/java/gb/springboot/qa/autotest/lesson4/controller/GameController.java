package gb.springboot.qa.autotest.lesson4.controller;

import gb.springboot.qa.autotest.lesson2.rest.dto.Game;
import gb.springboot.qa.autotest.lesson4.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game-rest")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    void save(@RequestBody Game game) {
        gameService.save(game);
    }

    @PutMapping
    void update(@RequestBody Game game) {
        gameService.update(game);
    }

    @GetMapping("/{id}")
    Game getUserById(@PathVariable Integer id) {
        return gameService.getById(id);
    }

    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable Integer id) {
        gameService.delete(id);
    }
}
