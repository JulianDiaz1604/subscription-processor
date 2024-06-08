package co.edu.uco.subscriptionprocessor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Autowired
    private RabbitMQProperties rabbitMQProperties;

    @Bean
    public DirectExchange requestExchange() {
        return new DirectExchange(rabbitMQProperties.getExchange().getRequest());
    }

    @Bean
    public DirectExchange responseExchange() {
        return new DirectExchange(rabbitMQProperties.getExchange().getResponse());
    }

    @Bean
    public Queue planProcessingQueue() {
        return new Queue(rabbitMQProperties.getQueue().getPlanProcessing(), true);
    }

    @Bean
    public Queue planResponseQueue() {
        return new Queue(rabbitMQProperties.getQueue().getPlanResponse(), true);
    }

    @Bean
    public Queue billingProcessingQueue() {
        return new Queue(rabbitMQProperties.getQueue().getBillingProcessing(), true);
    }

    @Bean
    public Queue billingResponseQueue() {
        return new Queue(rabbitMQProperties.getQueue().getBillingResponse(), true);
    }

    @Bean
    public Binding planProcessingBinding(Queue planProcessingQueue, DirectExchange requestExchange) {
        return BindingBuilder.bind(planProcessingQueue).to(requestExchange).with(rabbitMQProperties.getRoutingKey().getPlanProcessing());
    }

    @Bean
    public Binding planResponseBinding(Queue planResponseQueue, DirectExchange responseExchange) {
        return BindingBuilder.bind(planResponseQueue).to(responseExchange).with(rabbitMQProperties.getRoutingKey().getPlanResponse());
    }

    @Bean
    public Binding billingProcessingBinding(Queue billingProcessingQueue, DirectExchange requestExchange) {
        return BindingBuilder.bind(billingProcessingQueue).to(requestExchange).with(rabbitMQProperties.getRoutingKey().getBillingProcessing());
    }

    @Bean
    public Binding billingResponseBinding(Queue billingResponseQueue, DirectExchange responseExchange) {
        return BindingBuilder.bind(billingResponseQueue).to(responseExchange).with(rabbitMQProperties.getRoutingKey().getBillingResponse());
    }

}
