package com.example.vis_reservation.data.dao;

import com.example.vis_reservation.domain.Item;

import java.util.List;

public interface IItemDao {

    void save(Item item);

    Item findById(long id);

    List<Item> findAll();

    void deleteAll();
}
