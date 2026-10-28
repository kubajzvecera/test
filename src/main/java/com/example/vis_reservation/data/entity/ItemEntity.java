package com.example.vis_reservation.data.entity;

import java.math.BigDecimal;

public class ItemEntity {
    private long id;
    private String name;
    private String desc;
    private BigDecimal price;

    public ItemEntity() {
    }

    public ItemEntity(long id, String name, String desc, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemEntity{id=" + id + ", name='" + name + "', desc='" + desc + "', price=" + price + "}";
    }
}
