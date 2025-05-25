package tutoriales.rabbitmq.spring;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class NotificationProducer {
    private final static String QUEUE_NAME = "live_notifications";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String[] messages = {
                    "Nuevo mensaje del sistema",
                    "Evento en vivo comenzando ahora",
                    "Actualización importante disponible"
            };

            for (String msg : messages) {
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                System.out.println(" [x] Notificación enviada: " + msg);
                Thread.sleep(1000); // Simula tiempo entre notificaciones
            }
        }
    }
}

