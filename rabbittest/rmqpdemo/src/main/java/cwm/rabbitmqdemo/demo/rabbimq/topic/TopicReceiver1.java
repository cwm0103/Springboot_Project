package cwm.rabbitmqdemo.demo.rabbimq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.cwmq")
public class TopicReceiver1 {

    @RabbitHandler
    public void proess(String msg)
    {
        System.out.println("接受 topic.cwmq: "+msg);
    }
}
