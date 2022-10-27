package gb.springboot.qa.autotest.lesson4;


import com.fasterxml.jackson.databind.ObjectMapper;
import gb.springboot.qa.autotest.lesson2.rest.dto.Game;
import gb.springboot.qa.autotest.lesson4.entity.GameEntity;
import gb.springboot.qa.autotest.lesson4.mapper.GameMapper;
import gb.springboot.qa.autotest.lesson4.repository.GameRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMapper gameMapper;

    @LocalServerPort
    private int port;

    private Game game;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @BeforeEach
    public void setUp() {
        game = new Game();
        game.setId(1);
        game.setName("Duke Nukem");
        game.setGenre("shooter");
        game.setMmo(false);
    }

    private void assertGameEntity() {
        GameEntity gameEntity = gameRepository.findAll().stream()
                .findFirst()
                .orElseThrow();
        assertEqGameEntity(gameEntity, game);
    }

    private void assertEqGameEntity(GameEntity gameEntity, Game game) {
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(gameEntity.getId()).isEqualTo(game.getId());
            s.assertThat(gameEntity.getName()).isEqualTo(game.getName());
            s.assertThat(gameEntity.getGenre()).isEqualTo(game.getGenre());
            s.assertThat(gameEntity.isMmo()).isEqualTo(game.isMmo());
        });
    }

    @Test
    @DisplayName("Checking save game")
    void saveGameTest() throws Exception {
        //Given
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/game-rest"))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(game)))
                .build();

        //When
        request(httpRequest);

        //Then
        assertGameEntity();
    }

    @Test
    @DisplayName("Checking get gane")
    void getGameTest() throws Exception {
        //Given
        GameEntity gameEntity = gameRepository.saveAndFlush(gameMapper.dtoToEntity(game));

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/game-rest/" + gameEntity.getId()))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .GET()
                .build();

        //When
        HttpResponse<String> response = request(httpRequest);

        //Then
        Game responseGame = objectMapper.readValue(response.body(), Game.class);
        assertEqGameEntity(gameEntity, responseGame);
    }

    @Test
    @DisplayName("Checking update gane")
    void updateGameTest() throws Exception {
        //Given
        gameRepository.saveAndFlush(gameMapper.dtoToEntity(game));
        game.setMmo(true);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/game-rest"))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(game)))
                .build();

        //When
        request(httpRequest);

        //Then
        assertGameEntity();
    }

    @Test
    @DisplayName("Checking delete game")
    void deleteGameTest() throws Exception {
        //Given
        gameRepository.saveAndFlush(gameMapper.dtoToEntity(game));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/game-rest/" + game.getId()))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .DELETE()
                .build();

        //When
        request(httpRequest);

        //Then
        Assertions.assertThat(gameRepository.findAll().stream()
                .findFirst().isEmpty()).isTrue();
    }

    private HttpResponse<String> request(HttpRequest httpRequest) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        return response;
    }
}
