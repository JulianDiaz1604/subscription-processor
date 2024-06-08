package co.edu.uco.subscriptionprocessor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMQProperties {

    private Exchange exchange = new Exchange();
    private Queue queue = new Queue();
    private RoutingKey routingKey = new RoutingKey();

    public static class Exchange {
        private String request;
        private String response;

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

    public static class Queue {
        private String planProcessing;
        private String planResponse;
        private String billingProcessing;
        private String billingResponse;

        public String getPlanProcessing() {
            return planProcessing;
        }

        public void setPlanProcessing(String planProcessing) {
            this.planProcessing = planProcessing;
        }

        public String getPlanResponse() {
            return planResponse;
        }

        public void setPlanResponse(String planResponse) {
            this.planResponse = planResponse;
        }

        public String getBillingProcessing() {
            return billingProcessing;
        }

        public void setBillingProcessing(String billingProcessing) {
            this.billingProcessing = billingProcessing;
        }

        public String getBillingResponse() {
            return billingResponse;
        }

        public void setBillingResponse(String billingResponse) {
            this.billingResponse = billingResponse;
        }
    }

    public static class RoutingKey {
        private String planProcessing;
        private String planResponse;
        private String billingProcessing;
        private String billingResponse;

        public String getPlanProcessing() {
            return planProcessing;
        }

        public void setPlanProcessing(String planProcessing) {
            this.planProcessing = planProcessing;
        }

        public String getPlanResponse() {
            return planResponse;
        }

        public void setPlanResponse(String planResponse) {
            this.planResponse = planResponse;
        }

        public String getBillingProcessing() {
            return billingProcessing;
        }

        public void setBillingProcessing(String billingProcessing) {
            this.billingProcessing = billingProcessing;
        }

        public String getBillingResponse() {
            return billingResponse;
        }

        public void setBillingResponse(String billingResponse) {
            this.billingResponse = billingResponse;
        }
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public RoutingKey getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(RoutingKey routingKey) {
        this.routingKey = routingKey;
    }

}