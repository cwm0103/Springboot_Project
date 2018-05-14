package cwm.rabbitmqdemo.demo.rabbimq.more;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MoreSender2 {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void Sender2(String mes)
    {
        for(int i=0;i<20;i++)
        {
            String context="Sender2:"+i+mes;
            amqpTemplate.convertAndSend("more",context);
        }
    }
}
