import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GettingListOrdersTest {
    private OrderClient orderClient;
    private Order order;
    private CourierClient courierClient;
    private int id;
    private int track;
    private int courierId;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Список заказов")
    @Description(" Проверка,что в тело ответа возвращается список заказов")
    public void listOrders(){

        Courier courier = new Courier("Serg", "1234", "Olala");//соз кур
        ValidatableResponse responseCreate = courierClient.create(courier); //ручка соз кур


        Credentials credential = new Credentials("Serg", "1234"); //вхожу с лог и пар
        ValidatableResponse responseLogin = courierClient.login(credential);//ручка входа
        courierId = responseLogin.extract().path("id");

        Order order = new Order(
                "Анна",
                "Петрова",
                "Вологда, ул Конева 12",
                "Сокольники",
                "+7 911 911 11 11",
                "3",
                "2022-11-30",
                "Комментарий",
                List.of("BLACK"));

        ValidatableResponse responseCreate1 = orderClient.postOrder(order);
        track = responseCreate1.extract().path("track");

        //получить заказ по номеру Дергаем ручку
        ValidatableResponse responseGetOrdersNumber = orderClient.getOrdersNumber(track);

        //принять заказ дергаем ручку
        ValidatableResponse responsePutOrderId = orderClient.putOrderId(id,courierId);

        //получить список
        ValidatableResponse responseGetOrders = orderClient.getOrders(courierId);


        int statusCode = responseGetOrders.extract().statusCode();
        List<Object> orders = responseGetOrders.extract().path("orders");

        assertNotNull(orders);
        assertEquals(200, statusCode);
    }
    @After
    public void cleanUp() {
        orderClient.deleteOrder(track);
        courierClient.delete(courierId);
    }
}
