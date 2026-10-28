package com.example.vis_reservation.data.dao;

import com.example.vis_reservation.domain.Item;

import java.util.List;

public class ItemTextDao implements IItemDao {

    public ItemTextDao() {
    }

    @Override
    public void save(Item item) {
        System.out.println("ItemTextDao.save: textove spojeni zatim neni implementovano.");
    }

    @Override
    public Item findById(long id) {
        System.out.println("ItemTextDao.findById: textove spojeni zatim neni implementovano.");
        return null;
    }

    @Override
    public List<Item> findAll() {
        System.out.println("ItemTextDao.findAll: textove spojeni zatim neni implementovano.");
        return List.of();
    }

    @Override
    public void deleteAll() {
        System.out.println("ItemTextDao.deleteAll: textove spojeni zatim neni implementovano.");
    }
}
