package cwm.rabbitmqdemo.demo.rabbimq.fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void Send(String msg)
    {
        System.out.println("发送消息!");
        amqpTemplate.convertAndSend("fanoutExchange","","send:"+msg);
    }
}
