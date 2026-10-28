package com.example.vis_reservation.data.dao;

import com.example.vis_reservation.domain.Customer;

import java.util.List;

public class CustomerSqlDao implements ICustomerDao {

    public CustomerSqlDao() {
    }

    @Override
    public void save(Customer customer) {
        System.out.println("CustomerSqlDao.save: SQL spojeni zatim neni implementovano.");
    }

    @Override
    public Customer findById(long id) {
        System.out.println("CustomerSqlDao.findById: SQL spojeni zatim neni implementovano.");
        return null;
    }

    @Override
    public List<Customer> findAll() {
        System.out.println("CustomerSqlDao.findAll: SQL spojeni zatim neni implementovano.");
        return List.of();
    }

    @Override
    public void deleteAll() {
        System.out.println("CustomerSqlDao.deleteAll: SQL spojeni zatim neni implementovano.");
    }
}
