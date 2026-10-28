package com.example.vis_reservation.data.dao;

import com.example.vis_reservation.domain.Customer;

import java.util.List;

public interface ICustomerDao {

    void save(Customer customer);

    Customer findById(long id);

    List<Customer> findAll();

    void deleteAll();
}
