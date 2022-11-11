import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CourierLoginTest {
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:курьер может авторизоваться")
    public void courierCanLogIn() {

        Courier courier = new Courier("olser", "1234", "Olala");
        ValidatableResponse responseCreate = courierClient.create(courier);


        Credentials credential = new Credentials("olser", "1234");
        ValidatableResponse responseLogin = courierClient.login(credential);
        id = responseLogin.extract().path("id");
        int statusCode = responseLogin.extract().statusCode();
        assertEquals(200, statusCode);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:система вернёт ошибку, если неправильно указать логин")
    public void systemWillReturnErrorIfTheLoginOrPasswordIncorrectLogin() {
        Courier courier = new Courier("catt", "1234", "Olala");
        ValidatableResponse responseCreate = courierClient.create(courier);

        Credentials credential = new Credentials("cat", "1234");
        ValidatableResponse responseLogin = courierClient.login(credential);

        int statusCode = responseLogin.extract().statusCode();
        assertEquals(404, statusCode);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:система вернёт ошибку, если неправильно указать пароль")
    public void systemWillReturnErrorIfTheLoginOrPasswordIncorrectPassword() {
        Courier courier = new Courier("catt", "1234", "Olala");
        ValidatableResponse responseCreate = courierClient.create(courier);

        Credentials credential = new Credentials("catt", "1200");
        ValidatableResponse responseLogin = courierClient.login(credential);

        int statusCode = responseLogin.extract().statusCode();
        assertEquals(404, statusCode);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:успешный запрос возвращает id")
    public void successfulRequestReturnsId() {
        Courier courier = new Courier("olser", "1234", "Olala");
        ValidatableResponse responseCreate = courierClient.create(courier);

        Credentials credential = new Credentials("olser", "1234");
        ValidatableResponse responseLogin = courierClient.login(credential);
        id = responseLogin.extract().path("id");
        int courierCreated = responseLogin.extract().path("id");
        assertEquals(id, courierCreated);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:если какого-то поля нет, запрос возвращает ошибку")
    public void ifThereIsNoFieldRequestReturnsError() {
        Courier courier = new Courier("catt", "1234", "Olala");
        ValidatableResponse responseCreate = courierClient.create(courier);

        Credentials credential = new Credentials("", "1234");
        ValidatableResponse responseLogin = courierClient.login(credential);

        int statusCode = responseLogin.extract().statusCode();
        String courierCreated = responseLogin.extract().path("message");
        assertEquals("Недостаточно данных для входа", courierCreated);
        assertEquals(400, statusCode);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:для авторизации нужно передать все обязательные поля")
    public void forAuthorizationYouNeedPassAllRequiredFields (){
        Courier courier = new Courier("catt", "1234", "Olala");
        ValidatableResponse responseCreate = courierClient.create(courier);

        Credentials credential = new Credentials("catt", "");
        ValidatableResponse responseLogin = courierClient.login(credential);

        int statusCode = responseLogin.extract().statusCode();
        assertEquals(400, statusCode);
    }
    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void ifYouLogInUnderNonExistentUserRequestReturnsError (){

        Credentials credential = new Credentials("nnnnn", "1256");
        ValidatableResponse responseLogin = courierClient.login(credential);

        int statusCode = responseLogin.extract().statusCode();
        String courierCreated = responseLogin.extract().path("message");
        assertEquals("Учетная запись не найдена", courierCreated);
        assertEquals(404, statusCode);
    }
    @After
    public void cleanUp() {
        courierClient.delete(id);
    }
}
