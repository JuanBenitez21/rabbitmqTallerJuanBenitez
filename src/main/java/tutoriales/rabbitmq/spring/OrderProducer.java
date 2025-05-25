package tutoriales.rabbitmq.spring;



import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class OrderProducer {
    private final static String QUEUE_NAME = "orders";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i = 1; i <= 5; i++) {
                String order = "Pedido #" + i;
                channel.basicPublish("", QUEUE_NAME, null, order.getBytes());
                System.out.println(" [x] Enviado: " + order);
                Thread.sleep(500);
            }
        }
    }
}

