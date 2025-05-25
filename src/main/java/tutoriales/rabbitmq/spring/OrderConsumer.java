package tutoriales.rabbitmq.spring;

import com.rabbitmq.client.*;

public class OrderConsumer {
    private final static String QUEUE_NAME = "orders";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Esperando pedidos...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String order = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Procesando: " + order);
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        }
    }
}
