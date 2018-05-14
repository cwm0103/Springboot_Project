package cwm.rabbitmqdemo.demo.rabbimq;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue_hello(){
       return new Queue("hello");
    }

    @Bean
    public Queue queue_more()
    {
        return new Queue("more");
    }
    @Bean
    public Queue queue_object()
    {
        return new Queue("obj");
    }

}
