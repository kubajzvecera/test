package com.example.vis_reservation.domain.service;

import com.example.vis_reservation.data.dao.IItemDao;
import com.example.vis_reservation.data.entity.ItemEntity;
import com.example.vis_reservation.domain.Item;
import com.example.vis_reservation.domain.mapper.ItemMapper;

import java.util.List;

public class ItemService {

    private final IItemDao itemDao;

    public ItemService(IItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> getAllItems() {
        return itemDao.findAll();
    }

    public Item getById(long id) {
        return itemDao.findById(id);
    }

    public void save(Item item) {
        itemDao.save(item);
    }

    public long nextId() {
        return itemDao.findAll().stream()
                .mapToLong(Item::getId)
                .max()
                .orElse(0L) + 1;
    }

    public List<Item> mapToDomain(List<ItemEntity> entities) {
        return ItemMapper.toDomainList(entities);
    }

    public List<ItemEntity> mapToEntity(List<Item> items) {
        return ItemMapper.toEntityList(items);
    }
}
