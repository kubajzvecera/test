package com.example.vis_reservation.data.dao;

import com.example.vis_reservation.domain.Customer;

import java.util.List;

public class CustomerTextDao implements ICustomerDao {

    public CustomerTextDao() {
    }

    @Override
    public void save(Customer customer) {
        System.out.println("CustomerTextDao.save: textove spojeni zatim neni implementovano.");
    }

    @Override
    public Customer findById(long id) {
        System.out.println("CustomerTextDao.findById: textove spojeni zatim neni implementovano.");
        return null;
    }

    @Override
    public List<Customer> findAll() {
        System.out.println("CustomerTextDao.findAll: textove spojeni zatim neni implementovano.");
        return List.of();
    }

    @Override
    public void deleteAll() {
        System.out.println("CustomerTextDao.deleteAll: textove spojeni zatim neni implementovano.");
    }
}
