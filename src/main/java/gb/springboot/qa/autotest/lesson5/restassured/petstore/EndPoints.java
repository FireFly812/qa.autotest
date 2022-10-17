package gb.springboot.qa.autotest.lesson5.restassured.petstore;

public enum EndPoints {

    USER("/user"),
    STORE("/store"),
    PET("/pet");

    private final String url;

    EndPoints(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
