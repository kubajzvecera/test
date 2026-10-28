package com.example.vis_reservation.domain.service;

import com.example.vis_reservation.data.dao.IOrderDao;
import com.example.vis_reservation.domain.Order;

import java.util.List;

public class OrderService {

    private final IOrderDao orderDao;

    public OrderService(IOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    public Order getById(long id) {
        return orderDao.findById(id);
    }

    public void save(Order order) {
        orderDao.save(order);
    }

    public long nextId() {
        return orderDao.findAll().stream()
                .mapToLong(Order::getId)
                .max()
                .orElse(0L) + 1;
    }
}
