package cwm.rabbitmqdemo.demo.rabbimq.object;

import cwm.rabbitmqdemo.demo.entity.Dog;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "obj")
public class ObjectReceiver {

    @RabbitHandler
    public void poress(String  dog)
    {
        System.out.println("Dog:"+dog.toString());
    }
}
