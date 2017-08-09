package com.imooc.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppProducer {

    // 单点
    // private static final String url = "tcp://localhost:61616";
    //ActiveMQ集群
    private static final String url = "failover:(tcp://192.168.1.129:61617,tcp://192.168.1.129:61618)?randomize=true";

    private static final String queueName = "queue-test";

    public static void main(String[] args) throws JMSException {
        //1、创建connectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2、创建connection
        Connection connection = connectionFactory.createConnection();
        //3、启动connection
        connection.start();
        //4、创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 5、创建一个目标
        Destination destination = session.createQueue(queueName);
        //6、创建一个生产者
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 100; i++) {
            //7、创建消息
            TextMessage textMessage = session.createTextMessage("test" + i);
            //8、发送消息
            producer.send(textMessage);

            System.out.println("发送消息" + textMessage.getText());

        }

        //9、关闭连接
        connection.close();


    }
}
