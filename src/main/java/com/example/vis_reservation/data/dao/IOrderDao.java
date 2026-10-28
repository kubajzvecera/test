package com.example.vis_reservation.data.dao;

import com.example.vis_reservation.domain.Order;

import java.util.List;

public interface IOrderDao {

    void save(Order order);

    Order findById(long id);

    List<Order> findAll();

    void deleteAll();
}
