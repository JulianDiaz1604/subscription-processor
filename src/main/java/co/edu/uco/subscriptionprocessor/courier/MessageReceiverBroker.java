package co.edu.uco.subscriptionprocessor.courier;

import co.edu.uco.subscriptionprocessor.domain.plan.PlanListMessage;
import co.edu.uco.subscriptionprocessor.service.plan.PlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageReceiverBroker {

    @Autowired
    private PlanService planService;

    @Autowired
    private MessageSenderBroker messageSenderBroker;

    @Autowired
    private ObjectMapper objectMapper;

    private Object lastReceivedMessage;

    @RabbitListener(queues = "${rabbitmq.queue.plan-processing}")
    public void processPlanMessage(String message) {
        try {
            System.out.println("Received plan message: " + message);
            PlanListMessage receivedMessage = objectMapper.readValue(message, PlanListMessage.class);
            messageSenderBroker.sendPlanResponseMessage(planService.getDiscountByPeriod(receivedMessage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.billing-processing}")
    public void processBillingMessage(String message) {
        System.out.println("Received billing message: " + message);
        // TODO process message
    }

    public Object getLastReceivedMessage() {
        return lastReceivedMessage;
    }

}
