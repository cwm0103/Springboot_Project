package cwm.rabbitmqdemo.demo.rabbimq.topic;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void Sender()
    {
        amqpTemplate.convertAndSend("topicExchange","topic.a","测试内容1");
    }

    public void Sender1()
    {
        amqpTemplate.convertAndSend("topicExchange","topic.cwm","测试内容2" );
    }

    public void Sender2()
    {
        amqpTemplate.convertAndSend("topicExchange","topic.cwmq","测试内容3" );
    }
}
