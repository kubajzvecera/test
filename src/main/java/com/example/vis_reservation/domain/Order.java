package com.example.vis_reservation.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private long id;
    private List<Item> items;
    private Customer customer;

    public Order() {
        this.items = new ArrayList<>();
    }

    public Order(long id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", customer=" + customer + ", items=" + items + "}";
    }
}
