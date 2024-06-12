package co.edu.uco.subscriptionprocessor.courier;

import co.edu.uco.subscriptionprocessor.domain.billing.BillingProcess;
import co.edu.uco.subscriptionprocessor.domain.plan.PlanListMessage;
import co.edu.uco.subscriptionprocessor.service.billing.BillingService;
import co.edu.uco.subscriptionprocessor.service.plan.PlanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiverBroker {

    @Autowired
    private PlanService planService;

    @Autowired
    private BillingService billingService;

    @Autowired
    private MessageSenderBroker messageSenderBroker;

    @Autowired
    private ObjectMapper objectMapper;

    private Object lastReceivedMessage;

    @RabbitListener(queues = "${rabbitmq.queue.plan-processing}")
    public void processPlanMessage(String message) {
        try {
            PlanListMessage receivedMessage = objectMapper.readValue(message, PlanListMessage.class);
            messageSenderBroker.sendPlanResponseMessage(planService.getDiscountByPeriod(receivedMessage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.billing-processing}")
    public void processBillingMessage(String message) {
        try {
            BillingProcess receivedMessage = objectMapper.readValue(message, BillingProcess.class);
            String billPath = billingService.createPdfBilling(receivedMessage);
            billingService.sendEmailWithAttachment(receivedMessage.getPerson(), billPath);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Object getLastReceivedMessage() {
        return lastReceivedMessage;
    }

}
