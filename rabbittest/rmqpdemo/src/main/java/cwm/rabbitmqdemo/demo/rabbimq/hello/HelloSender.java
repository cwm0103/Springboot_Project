package cwm.rabbitmqdemo.demo.rabbimq.hello;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;
@Configuration
public class HelloSender {

    @Autowired
    private AmqpTemplate  amqpTemplate;


    public void send1(String message)
    {
        String context="sender:"+message;
        System.out.println("Sender:"+context);
        this.amqpTemplate.convertAndSend("hello",context);
    }
    public void send2(String message)
    {
        String context="beautfil:"+message;
        this.amqpTemplate.convertAndSend("hello",context);
    }
}
