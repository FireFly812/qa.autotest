package gb.springboot.qa.autotest.lesson5.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import gb.springboot.qa.autotest.lesson2.rest.dto.Game;
import gb.springboot.qa.autotest.lesson4.service.GameService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameIntegrationWithMockTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    @MockBean
    private GameService gameService;

    private Game game;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @BeforeAll
    void init() {
        game = new Game();
        game.setId(1);
        game.setName("Duke Nukem");
        game.setGenre("shooter");
        game.setMmo(false);

        Mockito.when(gameService.getById(Mockito.anyInt())).thenReturn(game);
        Mockito.doNothing().when(gameService).delete(Mockito.anyInt());
    }

    @Test
    void getGameTest() throws Exception {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/game-rest/" + new Random().nextInt(5)))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .GET()
                .build();

        //step 1
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);

        //assert
        Game userResponse = objectMapper.readValue(response.body(), Game.class);
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(game.getName()).isEqualTo(userResponse.getName());
            s.assertThat(game.getGenre()).isEqualTo(userResponse.getGenre());
            s.assertThat(game.isMmo()).isEqualTo(userResponse.isMmo());
        });
    }

    @Test
    void updateGameTest() throws Exception {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/game-rest/" + new Random().nextInt(5)))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
    }
}
