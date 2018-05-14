package cwm.rabbitmqdemo.demo.rabbimq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.a")
public class FanoutReceiver1 {

    @RabbitHandler
        public void prosser(String msg)
        {
            System.out.println("Receiver1:"+msg);
        }
}
