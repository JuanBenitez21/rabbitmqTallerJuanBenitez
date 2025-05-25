package tutoriales.rabbitmq.spring;


import com.rabbitmq.client.*;

public class NotificationConsumer {
    private final static String QUEUE_NAME = "live_notifications";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Esperando notificaciones...");

            DeliverCallback callback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Notificación recibida: " + message);
            };

            channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});
        }
    }
}
