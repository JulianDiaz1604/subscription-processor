package co.edu.uco.subscriptionprocessor.courier;

import co.edu.uco.subscriptionprocessor.config.RabbitMQProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSenderBroker {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQProperties rabbitMQProperties;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendPlanResponseMessage(Object message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(rabbitMQProperties.getExchange().getResponse(), rabbitMQProperties.getRoutingKey().getPlanResponse(), jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendBillingResponseMessage(Object message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(rabbitMQProperties.getExchange().getResponse(), rabbitMQProperties.getRoutingKey().getBillingResponse(), jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
