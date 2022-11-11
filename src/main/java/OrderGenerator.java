import java.util.List;

public class OrderGenerator {
    public static Order getOrderWithBlackColor() {

        return new Order(
                "Ольга",
                "Иванова",
                "Вологда, ул Конева 12",
                "Сокольники",
                "+7 911 911 11 11",
                "3",
                "2022-11-30",
                "Комментарий",
                List.of("BLACK")
        );
    }
    public static Order getOrderWithGrayColor() {

        return new Order(
                "Ольга",
                "Иванова",
                "Вологда, ул Конева 12",
                "Сокольники",
                "+7 911 911 11 11",
                "3",
                "2022-11-30",
                "Комментарий",
                List.of("GRAY")
        );
    }
    public static Order getOrderWithTwoColor() {

        return new Order(
                "Ольга",
                "Иванова",
                "Вологда, ул Конева 12",
                "Сокольники",
                "+7 911 911 11 11",
                "3",
                "2022-11-30",
                "Комментарий",
                List.of("GRAY", "BLACK")
        );
    }
    public static Order getOrderWithoutColor() {

        return new Order(
                "Ольга",
                "Иванова",
                "Вологда, ул Конева 12",
                "Сокольники",
                "+7 911 911 11 11",
                "3",
                "2022-11-30",
                "Комментарий",
                null
        );
    }
}
