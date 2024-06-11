package co.edu.uco.subscriptionprocessor.domain.billing;

import co.edu.uco.subscriptionprocessor.domain.period.Period;
import co.edu.uco.subscriptionprocessor.domain.person.Person;
import co.edu.uco.subscriptionprocessor.domain.plan.Plan;
import co.edu.uco.subscriptionprocessor.domain.subscription.Subscription;

public class BillingProcess {

    private Subscription subscription;
    private Person person;
    private Period period;
    private Plan plan;
    private Double amount;

    public BillingProcess() {

    }

    public BillingProcess(Subscription subscription, Person person, Period period, Plan plan, Double amount) {
        this.subscription = subscription;
        this.person = person;
        this.period = period;
        this.plan = plan;
        this.amount = amount;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

}
