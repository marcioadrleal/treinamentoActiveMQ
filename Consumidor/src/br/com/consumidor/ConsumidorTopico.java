package br.com.consumidor;

import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConsumidorTopico {

	public static void main(String[] args) {
		try {
			Properties props = new Properties(); 
			props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory"); 
			props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
			props.setProperty("queue.financeiro", "fila.financeiro");
			InitialContext initial = new InitialContext(props);
			ConnectionFactory factory = (ConnectionFactory) initial.lookup("ConnectionFactory");
			Connection conn = factory.createConnection();
			conn.start();
            Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destinationFile = (Destination) initial.lookup("financeiro");
            MessageConsumer consumer = session.createConsumer(destinationFile);
	       
            consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message msg) {
                  System.out.println(msg);   
					
				}
			});
            
            
            new Scanner(System.in).nextLine(); //parar o programa para testar a conexao
	        session.close();
	        conn.close();
	        initial.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
