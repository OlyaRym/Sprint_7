import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private static final String PATH = "/api/v1/courier";
    public static final String PATH_LOGIN = "/api/v1/courier/login";
    public static final String PATH_DELETE = "/api/v1/courier/";

    @Step("Создать курьера")
    public ValidatableResponse create(Courier courier){
        return given()
                .spec(getSpec()) //настройки куда отправлять запрос
                .body(courier)
                .post(PATH)
                .then();
    }
    @Step("Войти уже созданному курьеру")
    public ValidatableResponse login(Credentials credential) {
        return given()
                .spec(getSpec())
                .body(credential)
                .when()
                .post(PATH_LOGIN)
                .then();
    }
    @Step("Удаление курьера")
    public ValidatableResponse delete(int id){
        return given()
                .spec(getSpec())
                .when()
                .delete(PATH_DELETE + id)
                .then();
    }
}
