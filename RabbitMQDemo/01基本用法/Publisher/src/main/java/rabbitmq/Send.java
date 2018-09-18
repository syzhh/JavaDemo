package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Send {
    private static final String APP_ID = "150117";
    private static final String TASK_QUEUE_NAME = String.format("MQ%s.BaseStudy", APP_ID);

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("flight");
        factory.setPassword("yyabc123");
        factory.setVirtualHost("Flight");
        factory.setHost("139.198.13.12");
        factory.setPort(4128);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

            while(true)
            {
                System.out.print("请输入要发送的消息：");
                BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
                String message = buffer.readLine();
                byte[] body = message.getBytes("UTF-8");

                channel.basicPublish("", TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        body);

                System.out.printf(" 已发送的消息：%s\n", message);
            }
        }
    }
}
