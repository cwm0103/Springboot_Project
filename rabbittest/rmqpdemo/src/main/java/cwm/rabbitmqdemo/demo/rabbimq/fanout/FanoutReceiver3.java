package cwm.rabbitmqdemo.demo.rabbimq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.c")
public class FanoutReceiver3 {

    @RabbitHandler
    public void process(String msg)
    {
        System.out.println("Receiver3:"+msg);
    }
}
