package com.example.vis_reservation.data.dao;

import com.example.vis_reservation.domain.Order;

import java.util.List;

public class OrderTextDao implements IOrderDao {

    public OrderTextDao() {
    }

    @Override
    public void save(Order order) {
        System.out.println("OrderTextDao.save: textove spojeni zatim neni implementovano.");
    }

    @Override
    public Order findById(long id) {
        System.out.println("OrderTextDao.findById: textove spojeni zatim neni implementovano.");
        return null;
    }

    @Override
    public List<Order> findAll() {
        System.out.println("OrderTextDao.findAll: textove spojeni zatim neni implementovano.");
        return List.of();
    }

    @Override
    public void deleteAll() {
        System.out.println("OrderTextDao.deleteAll: textove spojeni zatim neni implementovano.");
    }
}
