package co.edu.uco.subscriptionprocessor.domain.billing;

import java.time.LocalDateTime;
import java.util.UUID;

public class Billing {
    private UUID id;
    private double amount;
    private LocalDateTime emissionDate;
    private LocalDateTime dueDate;
    private UUID subscriptionId;

    public Billing(double amount, LocalDateTime emissionDate, LocalDateTime dueDate, UUID subscriptionId) {
        this.amount = amount;
        this.emissionDate = emissionDate;
        this.dueDate = dueDate;
        this.subscriptionId = subscriptionId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(LocalDateTime emissionDate) {
        this.emissionDate = emissionDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public UUID getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(UUID subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @Override
    public String toString() {
        return "Billing{" +
                "id=" + id +
                ", amount=" + amount +
                ", emissionDate=" + emissionDate +
                ", dueDate=" + dueDate +
                ", subscriptionId=" + subscriptionId +
                '}';
    }

}
