package gb.springboot.qa.autotest.lesson7;

import gb.springboot.qa.autotest.lesson7.config.GeekBrainsTestConfig;
import gb.springboot.qa.autotest.lesson7.page.GeekBrainsMainPage;
import gb.springboot.qa.autotest.lesson7.page.JavaProfessionPage;
import gb.springboot.qa.autotest.lesson7.page.JavaQaAutomationProfessionPage;
import gb.springboot.qa.autotest.lesson7.page.MobileApplicationPage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GeekBrainsTestConfig.class)
@TestPropertySource(locations = "/application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GeekBrainsSearchInfoTest {

    @Autowired
    private GeekBrainsMainPage mainPage;

    @Autowired
    private JavaQaAutomationProfessionPage javaQaAutomationProfessionPage;

    @Autowired
    private JavaProfessionPage javaProfessionPage;

    @Autowired
    private MobileApplicationPage mobileApplicationPage;


    @Test
    @DisplayName("Course selection Java testing automation")
    public void getJavaQaAutomationProfessionTest() {

        mainPage.getMainPage()
                .search("Java")
                .getProfession("Автоматизация тестирования на Java");

        Assertions.assertThat(javaQaAutomationProfessionPage.getFormOffer().getText())
                .containsIgnoringCase("Освоите основы Java, научитесь автоматизировать тестирование Web UI и бэкенда на Java.");

    }

    @Test
    @DisplayName("Course selection Programmer Java")
    public void getJavaProfessionTest() {
        mainPage.getMainPage()
                .search("Java")
                .getProfession("Программист Java");

        Assertions.assertThat(javaProfessionPage.getFormOffer().getText())
                .containsIgnoringCase("Пройдите обучение на инженера-программиста на Java.");

    }

    @Test
    @DisplayName("course selection Mobile applications in Java")
    public void getJavaMobileApplicationTest() {
        mainPage.getMainPage()
                .search("Java")
                .getProfession("Мобильные приложения на Java");

        Assertions.assertThat(mobileApplicationPage.getFormOffer().getText())
                .containsIgnoringCase("Создай с нуля интересную викторину и разработай своё приложение для android-смартфона");

    }
}
