package com.imooc.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppConsumer {


    private static final String url = "tcp://localhost:61616";

    private static final String topicName = "topic-test";

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
        Destination destination = session.createTopic(topicName);
        //6、创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);

        //7、创建一个消息监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.println("接收消息" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //8、关闭连接
        //connection.close();
    }
}