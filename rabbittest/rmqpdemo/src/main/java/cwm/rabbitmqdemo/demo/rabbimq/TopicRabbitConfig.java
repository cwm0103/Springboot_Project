package cwm.rabbitmqdemo.demo.rabbimq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TopicRabbitConfig {

    private final static  String msg1="topic.cwm";
    private final static  String msg2="topic.cwmq";

    @Bean
    public Queue queue_msg1()
    {
        return new Queue(TopicRabbitConfig.msg1,true);
    }

    @Bean
    public Queue queue_msg2()
    {
        return new Queue(TopicRabbitConfig.msg2,true);
    }

    @Bean
    TopicExchange exchange()
    {
        return new TopicExchange("topicExchange",true,true);
    }

    @Bean
    Binding bindingExchangmsg1(Queue queue_msg1,TopicExchange exchange)
    {
        return BindingBuilder.bind(queue_msg1).to(exchange).with("topic.cwm");
    }

    @Bean
    Binding bindingExchangmsg2(Queue queue_msg2,TopicExchange exchange)
    {
        return BindingBuilder.bind(queue_msg2).to(exchange).with("topic.#");
    }

}
