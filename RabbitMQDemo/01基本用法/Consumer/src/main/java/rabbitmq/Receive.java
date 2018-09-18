package rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Receive {
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

            System.out.println("准备接收消息（如果要退出，则请按回车键）：");

            channel.basicQos(1);

            final Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.printf("接收到的消息：%s\n", message);

                    try {
                        doWork(message);
                    } finally {
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        System.out.printf("接收的消息【%s】处理完成，现在时间为：%s\n", message, sdf.format(date));
                        //channel.basicAck(envelope.getDeliveryTag(), false); // 手动ACK：用来确认消息已经被消费完成了
                    }
                }
            };
            //channel.basicConsume(TASK_QUEUE_NAME, false, consumer); // 开启消费者与通道、队列关联；手动ACK
            channel.basicConsume(TASK_QUEUE_NAME, true, consumer); // 开启消费者与通道、队列关联；自动ACK

            new Scanner(System.in).nextLine();
        }
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

