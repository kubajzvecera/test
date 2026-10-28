package com.example.vis_reservation.domain.mapper;

import com.example.vis_reservation.data.entity.ItemEntity;
import com.example.vis_reservation.domain.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {

    public static Item toDomain(ItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Item(
                entity.getId(),
                entity.getName(),
                entity.getDesc(),
                entity.getPrice()
        );
    }

    public static ItemEntity toEntity(Item domain) {
        if (domain == null) {
            return null;
        }
        return new ItemEntity(
                domain.getId(),
                domain.getName(),
                domain.getDesc(),
                domain.getPrice()
        );
    }

    public static List<Item> toDomainList(List<ItemEntity> entities) {
        List<Item> items = new ArrayList<>();
        if (entities == null) {
            return items;
        }
        for (ItemEntity entity : entities) {
            items.add(toDomain(entity));
        }
        return items;
    }

    public static List<ItemEntity> toEntityList(List<Item> items) {
        List<ItemEntity> entities = new ArrayList<>();
        if (items == null) {
            return entities;
        }
        for (Item domain : items) {
            entities.add(toEntity(domain));
        }
        return entities;
    }
}
