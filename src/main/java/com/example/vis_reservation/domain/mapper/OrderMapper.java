package com.example.vis_reservation.domain.mapper;

import com.example.vis_reservation.data.entity.ItemEntity;
import com.example.vis_reservation.data.entity.OrderEntity;
import com.example.vis_reservation.domain.Item;
import com.example.vis_reservation.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static Order toDomain(OrderEntity entity) {
        if (entity == null) {
            return null;
        }
        Order order = new Order(entity.getId());
        order.setCustomer(CustomerMapper.toDomain(entity.getCustomer()));
        if (entity.getItems() != null) {
            for (ItemEntity item : entity.getItems()) {
                order.addItem(ItemMapper.toDomain(item));
            }
        }
        return order;
    }

    public static OrderEntity toEntity(Order domain) {
        if (domain == null) {
            return null;
        }
        OrderEntity entity = new OrderEntity(domain.getId());
        entity.setCustomer(CustomerMapper.toEntity(domain.getCustomer()));
        if (domain.getItems() != null) {
            for (Item item : domain.getItems()) {
                entity.addItem(ItemMapper.toEntity(item));
            }
        }
        return entity;
    }

    public static List<Order> toDomainList(List<OrderEntity> entities) {
        List<Order> orders = new ArrayList<>();
        if (entities == null) {
            return orders;
        }
        for (OrderEntity entity : entities) {
            orders.add(toDomain(entity));
        }
        return orders;
    }

    public static List<OrderEntity> toEntityList(List<Order> orders) {
        List<OrderEntity> entities = new ArrayList<>();
        if (orders == null) {
            return entities;
        }
        for (Order order : orders) {
            entities.add(toEntity(order));
        }
        return entities;
    }
}
