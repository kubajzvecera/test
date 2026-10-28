package com.example.vis_reservation.data.dao;

import com.example.vis_reservation.data.db.Database;
import com.example.vis_reservation.data.entity.ItemEntity;
import com.example.vis_reservation.domain.Item;
import com.example.vis_reservation.domain.mapper.ItemMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemSqlDao implements IItemDao {

    private final Database database;

    public ItemSqlDao() {
        this(Database.getInstance());
    }

    public ItemSqlDao(Database database) {
        this.database = database;
    }

    @Override
    public void save(Item item) {
        ItemEntity entity = ItemMapper.toEntity(item);
        try {
            database.update("""
                    INSERT INTO ITEM (id, name, description, price)
                    VALUES (?, ?, ?, ?)
                    """,
                    entity.getId(), entity.getName(), entity.getDesc(), entity.getPrice()
            );
        } catch (SQLException e) {
            throw new RuntimeException("ItemSqlDao.save failed for " + item, e);
        }
    }

    @Override
    public Item findById(long id) {
        try {
            List<Item> items = database.query(
                    "SELECT id, name, description, price FROM ITEM WHERE id = ?",
                    this::mapRow, id
            );
            return items.isEmpty() ? null : items.get(0);
        } catch (SQLException e) {
            throw new RuntimeException("ItemSqlDao.findById failed for id=" + id, e);
        }
    }

    @Override
    public List<Item> findAll() {
        try {
            return database.query(
                    "SELECT id, name, description, price FROM ITEM ORDER BY id",
                    this::mapRow
            );
        } catch (SQLException e) {
            throw new RuntimeException("ItemSqlDao.findAll failed", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            database.update("DELETE FROM ITEM");
        } catch (SQLException e) {
            throw new RuntimeException("ItemSqlDao.deleteAll failed", e);
        }
    }

    private Item mapRow(ResultSet rs) throws SQLException {
        ItemEntity entity = new ItemEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price")
        );
        return ItemMapper.toDomain(entity);
    }
}
