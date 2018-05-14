package cwm.rabbitmqdemo.demo.rabbimq.more;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "more")
public class MoreReceiver2 {

    @RabbitHandler
    public void process(String mes)
    {
        System.out.println("Receiver2:"+mes);
    }

}
