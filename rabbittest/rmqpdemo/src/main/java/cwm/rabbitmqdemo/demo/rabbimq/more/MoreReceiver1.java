package cwm.rabbitmqdemo.demo.rabbimq.more;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "more")
public class MoreReceiver1 {

    @RabbitHandler
    public void process(String mes)
    {
        System.out.println("Receiver1:"+mes);
    }

}
