/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Send;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Queue;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

/**
 *
 * @author casva
 */
public class FileSend {
    public static void Send(String content) throws JMSException{
    ConnectionFactory connFactory = new ConnectionFactory();
        connFactory.setProperty(ConnectionConfiguration.imqAddressList, "mq://145.93.41.187:7676");

        Queue myQueue = new Queue("FileSysteem");

        try (Connection connection = connFactory.createConnection(); 
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
                MessageProducer producer = session.createProducer(myQueue)) {

            Message message = session.createTextMessage("new message");
            message.setStringProperty("content", content);
            producer.send(message);
        }
    }
}
