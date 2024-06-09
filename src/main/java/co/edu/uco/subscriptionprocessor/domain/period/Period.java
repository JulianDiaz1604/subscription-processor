package co.edu.uco.subscriptionprocessor.domain.period;

import java.util.UUID;

public class Period {

    private UUID id;
    private String name;
    private double discount;
    private int months;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    @Override
    public String toString() {
        return "Period{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discount=" + discount +
                ", months=" + months +
                '}';
    }

}
