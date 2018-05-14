package cwm.rabbitmqdemo.demo.rabbimq.object;

import cwm.rabbitmqdemo.demo.entity.Dog;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void SendObj(Dog dog)
    {
        amqpTemplate.convertAndSend("obj",dog.toString());
    }
}
