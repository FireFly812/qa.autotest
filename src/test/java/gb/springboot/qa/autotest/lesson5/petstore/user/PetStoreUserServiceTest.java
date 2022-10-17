package gb.springboot.qa.autotest.lesson5.petstore.user;

import gb.springboot.qa.autotest.lesson5.petstore.config.PetStoreTestConfig;
import gb.springboot.qa.autotest.lesson5.restassured.petstore.EndPoints;
import gb.springboot.qa.autotest.lesson5.restassured.petstore.dto.PetStoreUserDto;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PetStoreTestConfig.class)
@TestPropertySource(locations = "/application.properties")
public class PetStoreUserServiceTest {

    @Autowired
    private RequestSpecification requestSpecification;

    @Test
    void saveUserAndGetByUserNameTest() {

        //pre-condition - create user
        PetStoreUserDto userDto = PetStoreUserDto.builder()
                .firstName("firstName")
                .id(100)
                .lastName("lastName")
                .username("userName")
                .email("email@email.com")
                .password("qwerty")
                .phone("phone")
                .userStatus(5)
                .build();

        // step 1 - post user
        requestSpecification
                .body(userDto)
                .post(EndPoints.USER.getUrl())
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());

        // step - 2 get user by username
        PetStoreUserDto userFromService = requestSpecification
                .get(EndPoints.USER.getUrl() + "/" + userDto.getUsername())
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PetStoreUserDto.class);

        //assert response
        Assertions.assertThat(userFromService).isEqualTo(userDto);
    }

    @Test
    void deleteUserTest() {

        //pre-condition - create user
        PetStoreUserDto userDto = PetStoreUserDto.builder()
                .firstName("firstName")
                .id(200)
                .lastName("lastName")
                .username("userDelete")
                .email("email@email.com")
                .password("qwerty")
                .phone("phone")
                .userStatus(5)
                .build();

        //post user
        requestSpecification
                .body(userDto)
                .post(EndPoints.USER.getUrl())
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());

        //delete user by username
        requestSpecification
                .delete(EndPoints.USER.getUrl() + "/" + userDto.getUsername())
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());

        //get user again and assert that response code is 404
        requestSpecification
                .get(EndPoints.USER.getUrl() + "/" + userDto.getUsername())
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
    void updateUserTest() {
        PetStoreUserDto userDto = PetStoreUserDto.builder()
                .firstName("firstName")
                .id(200)
                .lastName("lastName")
                .username("userName")
                .email("email@email.com")
                .password("qwerty")
                .phone("phone")
                .userStatus(5)
                .build();

        // step 1 - post user
        requestSpecification
                .body(userDto)
                .post(EndPoints.USER.getUrl())
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());

        //step 2 - update user by userName
        PetStoreUserDto userUpdateDto = PetStoreUserDto.builder()
                .firstName("firstName1")
                .id(200)
                .lastName("lastName1")
                .username("userName1")
                .email("email@email1.com")
                .password("qwerty1")
                .phone("phone1")
                .userStatus(5)
                .build();

        requestSpecification
                .body(userUpdateDto)
                .put(EndPoints.USER.getUrl() + "/" + userUpdateDto.getUsername())
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());

        // step - 3 get user by username
        PetStoreUserDto userFromService = requestSpecification
                .get(EndPoints.USER.getUrl() + "/" + userUpdateDto.getUsername())
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PetStoreUserDto.class);

        //assert response
        Assertions.assertThat(userFromService).isEqualTo(userUpdateDto);
    }
}

