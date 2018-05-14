package cwm.rabbitmqdemo.demo.rabbimq.more;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MoreSender1 {

    @Autowired
    private AmqpTemplate amqpTemplate;
    public void Send1(String mes)
    {
        for(int i=0;i<20;i++)
        {
            String context="sender1:"+i+mes;
            amqpTemplate.convertAndSend("more",context);
        }
    }
}
