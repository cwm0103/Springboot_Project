package cwm.rabbitmqdemo.demo.rabbimq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
public class FanoutrabbitConfig {
    @Bean
    public Queue queue_a()
    {
        return new Queue("fanout.a");
    }
    @Bean
    public Queue queue_b()
    {
        return new Queue("fanout.b");
    }
    @Bean
    public Queue queue_c()
    {
        return new Queue("fanout.c");
    }

    @Bean
    FanoutExchange fanoutExchange()
    {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingFanoutExchange_a(Queue queue_a,FanoutExchange exchange)
    {
        return  BindingBuilder.bind(queue_a).to(exchange);
    }
    @Bean
    Binding bindingFanoutExchange_b(Queue queue_b,FanoutExchange exchange)
    {
        return  BindingBuilder.bind(queue_b).to(exchange);
    }
    @Bean
    Binding bindingFanoutExchange_c(Queue queue_c,FanoutExchange exchange)
    {
        return  BindingBuilder.bind(queue_c).to(exchange);
    }
}
