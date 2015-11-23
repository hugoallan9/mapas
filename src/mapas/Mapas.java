/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapas;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import reportestrimestrales.Mapa;
/**
 *
 * @author ine031
 */
public class Mapas {
    
    public static String TASK_QUEUE_NAME = "mapas";
    
    private static void doWork(String task) {  
        System.out.println("XXXXXXXXXXX");
 
        String rutaOrigen = "/var/www/archivos/CSV_mapas/";
        String rutaDestino = "/home/ineservidor/Mapas/";
        /*String comando = "rm -R " + rutaDestino + "*";
        Process p;
        try {
            p = Runtime.getRuntime().exec(comando);
            try {
                p.waitFor();
                BufferedReader reader = 
                new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line = "";			
                while ((line = reader.readLine())!= null) {
                           System.out.println(line);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Mapas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Mapas.class.getName()).log(Level.SEVERE, null, ex);
        }*/
       
        
        Mapa mapa = new Mapa(rutaOrigen, rutaDestino);     
        //mapa.descargaDepartamental();
        mapa.mapasDepAutomaticos();          
}
    
    
   
    
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPassword("test");
        factory.setUsername("test");
        final Connection connection = factory.newConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
              String message = new String(body, "UTF-8");

              System.out.println(" [x] Received '" + message + "'");
              try {
                doWork(message);
              } catch(Exception e){
                  System.out.println(e.getMessage());
              }
                System.out.println(" [x] Done");
                channel.basicAck(envelope.getDeliveryTag(), false);

            }
          };
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);       
    }
    
}
