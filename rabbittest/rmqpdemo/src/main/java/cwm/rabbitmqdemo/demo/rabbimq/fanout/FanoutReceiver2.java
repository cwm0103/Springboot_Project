package cwm.rabbitmqdemo.demo.rabbimq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.b")
public class FanoutReceiver2 {

    @RabbitHandler
    public void process(String msg)
    {
        System.out.println("Receiver2:"+msg);
    }
}
