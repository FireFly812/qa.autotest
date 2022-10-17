package gb.springboot.qa.autotest.lesson5.petstore.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("gb.springboot.qa.autotest.lesson5.restassured.petstore")
public class PetStoreTestConfig {
}
