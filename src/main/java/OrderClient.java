import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client{
    private static final String PATH_ORDER_CREATING = "/api/v1/orders";
    private static final String PATH_ORDER_DELETE = "/api/v1/orders/cancel";
    private static final String PATH_ORDER_NUMBER = "/api/v1/orders/track?t=";
    private static final String PATH_ORDER_ID = "/api/v1/orders/accept/";
    private static final String PATH_ORDER = "/api/v1/orders?courierId=";

    @Step("Создание заказа")
    public ValidatableResponse postOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH_ORDER_CREATING)
                .then();
    }
    @Step("Удаление заказа")
    public ValidatableResponse deleteOrder(int track) {
        return given()
                .spec(getSpec())
                .when()
                .put(PATH_ORDER_DELETE)
                .then();
    }
    @Step("Получение списка заказов")
    public ValidatableResponse getOrders(int courierId) {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH_ORDER + courierId)
                .then();
    }
    @Step("Получение заказа по номеру")
    public ValidatableResponse getOrdersNumber(int track){
        return given()
                .spec(getSpec())
                .when()
                .get(PATH_ORDER_NUMBER + track)
                .then();
    }
    @Step("Принять заказ")
    public ValidatableResponse putOrderId(int id,int courierId){
        return given()
                .spec(getSpec())
                .when()
                .put(PATH_ORDER_ID + id + "?courierId=" + courierId)
                .then();
    }
}
