package com.hit.client.model;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;     // If the class will be change

    private Long id;
    private String eventName;
    private String customerName;
    private double price;

    //empty constructor
    public Ticket() {
    }


    public Ticket(Long id, String eventName, String customerName, double price) {
        this.id = id;
        this.eventName = eventName;
        this.customerName = customerName;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Methods
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", price=" + price +
                '}';
    }
    // Tell the system to compare according id instead of address
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }
    // working with hashmap and must because equals function
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}