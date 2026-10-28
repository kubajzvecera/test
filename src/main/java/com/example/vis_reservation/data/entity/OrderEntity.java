package com.example.vis_reservation.data.entity;

import java.util.ArrayList;
import java.util.List;

public class OrderEntity {
    private long id;
    private List<ItemEntity> items;
    private CustomerEntity customer;

    public OrderEntity() {
        this.items = new ArrayList<>();
    }

    public OrderEntity(long id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public void addItem(ItemEntity item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "OrderEntity{id=" + id + ", customer=" + customer + ", items=" + items + "}";
    }
}
