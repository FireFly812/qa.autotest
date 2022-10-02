package gb.springboot.qa.autotest.lesson2;

import gb.springboot.qa.autotest.lesson2.rest.dto.Game;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class CrudOperationForGames {

    Map<Integer, Game> data = new HashMap<>();

    @PostConstruct
    void init() {
        data.put(1, new Game(1, "Duke Nukem","shooter",false));
        data.put(2, new Game(2, "Doom II","shooter",false));
        data.put(3, new Game(3, "CS GO","tactical shooter", false));
        data.put(4, new Game(4, "LineAge II","Massively multiplayer online role-playing game", true));
        data.put(5, new Game(5, "World of Warcraft","Massively multiplayer online role-playing game", true));

    }


    @GetMapping("/{id}")
    public Game get(@PathVariable int id) {
        return data.get(id);
    }

    @GetMapping("/all")
    public List<Game> get() {
        return new ArrayList<>(data.values());
    }

    @PostMapping()
    public void save(@RequestBody Game game) {
        int id = data.size() + 1;
        game.setId(id);
        data.put(id, game);
    }

    @PutMapping("/{id}")
    public void change(@PathVariable int id, @RequestBody Game gameChanging) {
        Game game = data.get(id);
        game.setName(gameChanging.getName());
        game.setGenre(gameChanging.getGenre());
        game.setMmo(gameChanging.isMmo());
        data.put(id, game);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        data.remove(id);
    }
}
