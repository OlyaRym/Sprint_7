import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class CreatingCourierTest {
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:курьера можно создать")
    public void courierCanBeCreated(){
        Courier courier = new Courier("annaolga", "1256", "annyn");

        ValidatableResponse responseCreate = courierClient.create(courier); //дернет ручку
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));  // получили логин
        id = responseLogin.extract().path("id"); //вернет значение из json, кот. лежит по ключ.id

        int statusCode = responseCreate.extract().statusCode();
        assertEquals(201, statusCode);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:запрос возвращает правильный код ответа")
    public void requestReturnsCorrectResponseCode201(){
        Courier courier = new Courier("annaolga", "1256", "annyn");

        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");

        int statusCode = responseCreate.extract().statusCode();
        assertEquals(201, statusCode);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:успешный запрос возвращает ok: true")
    public void successfulRequestReturnsOkTrue(){
        Courier courier = new Courier("annaolga", "1256", "annyn");

        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");

        boolean isCourierCreated = responseCreate.extract().path("ok");
        assertEquals(true, isCourierCreated);

    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:нельзя создать двух одинаковых курьеров")
    public void youCannotCreateTwoIdenticalCouriers(){

        Courier courier = new Courier("olgaanna", "1256", "annyn");

        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");

        Courier courier1 = new Courier("olgaanna", "1256", "annyn");
        ValidatableResponse responseCreate1 = courierClient.create(courier1);

        int statusCode = responseCreate1.extract().statusCode();
        assertEquals(409, statusCode);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void errorReturnedWhenCreatingIdenticalLogins (){
        Courier courier = new Courier("olgaanna", "1256", "annyn");
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");

        Courier courier1 = new Courier("olgaanna", "1256", "annyn");
        ValidatableResponse responseCreate1 = courierClient.create(courier1);
        int statusCode = responseCreate1.extract().statusCode();
        assertEquals(409, statusCode);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:чтобы создать курьера, нужно передать в ручку все обязательные поля и если одного из полей нет, запрос возвращает ошибку")
    public void creatingCourierWithOnlyAllFields(){
        Courier courier = new Courier("olgaanna", "1256", "annyn");
        courier.setPassword("");
        ValidatableResponse responseCreate = courierClient.create(courier);

        String courierCreated = responseCreate.extract().path("message");
        int statusCode = responseCreate.extract().statusCode();

        assertEquals("Недостаточно данных для создания учетной записи", courierCreated);
        assertEquals(400, statusCode);
    }
    @After
    public void cleanUp(){
        courierClient.delete(id);
    }
}
