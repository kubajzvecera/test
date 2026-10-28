package com.example.vis_reservation.data.dao;

import com.example.vis_reservation.data.db.Database;
import com.example.vis_reservation.data.entity.AddressEntity;
import com.example.vis_reservation.data.entity.CustomerEntity;
import com.example.vis_reservation.data.entity.ItemEntity;
import com.example.vis_reservation.data.entity.OrderEntity;
import com.example.vis_reservation.domain.Order;
import com.example.vis_reservation.domain.mapper.OrderMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderSqlDao implements IOrderDao {

    private final Database database;

    public OrderSqlDao() {
        this(Database.getInstance());
    }

    public OrderSqlDao(Database database) {
        this.database = database;
    }

    @Override
    public void save(Order order) {
        OrderEntity entity = OrderMapper.toEntity(order);
        try {
            database.update("""
                    INSERT INTO ORDERS (id, customer_id)
                    VALUES (?, ?)
                    """,
                    entity.getId(),
                    entity.getCustomer() != null ? entity.getCustomer().getId() : null
            );
            for (ItemEntity item : entity.getItems()) {
                database.update("""
                        INSERT INTO SHOPPING_CART (order_id, item_id)
                        VALUES (?, ?)
                        """,
                        entity.getId(), item.getId()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("OrderSqlDao.save failed for " + order, e);
        }
    }

    @Override
    public Order findById(long id) {
        try {
            List<OrderEntity> orders = database.query("""
                    SELECT o.id AS order_id,
                           c.id AS customer_id, c.fname, c.lname, c.phone, c.email,
                           a.id AS address_id, a.street, a.city, a.house_number, a.zip_code
                    FROM ORDERS o
                    LEFT JOIN CUSTOMER c ON c.id = o.customer_id
                    LEFT JOIN ADDRESS a ON a.id = c.address_id
                    WHERE o.id = ?
                    """,
                    this::mapOrderRow, id
            );
            if (orders.isEmpty()) {
                return null;
            }
            OrderEntity entity = orders.get(0);
            entity.setItems(loadItems(entity.getId()));
            return OrderMapper.toDomain(entity);
        } catch (SQLException e) {
            throw new RuntimeException("OrderSqlDao.findById failed for id=" + id, e);
        }
    }

    @Override
    public List<Order> findAll() {
        try {
            List<OrderEntity> orders = database.query("""
                    SELECT o.id AS order_id,
                           c.id AS customer_id, c.fname, c.lname, c.phone, c.email,
                           a.id AS address_id, a.street, a.city, a.house_number, a.zip_code
                    FROM ORDERS o
                    LEFT JOIN CUSTOMER c ON c.id = o.customer_id
                    LEFT JOIN ADDRESS a ON a.id = c.address_id
                    ORDER BY o.id
                    """,
                    this::mapOrderRow
            );
            for (OrderEntity order : orders) {
                order.setItems(loadItems(order.getId()));//loadItems returns all items info of this order
            }
            return OrderMapper.toDomainList(orders);
        } catch (SQLException e) {
            throw new RuntimeException("OrderSqlDao.findAll failed", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            database.update("DELETE FROM SHOPPING_CART");
            database.update("DELETE FROM ORDERS");
        } catch (SQLException e) {
            throw new RuntimeException("OrderSqlDao.deleteAll failed", e);
        }
    }

    private OrderEntity mapOrderRow(ResultSet rs) throws SQLException {
        OrderEntity order = new OrderEntity(rs.getLong("order_id"));

        Long customerId = nullableLong(rs, "customer_id");
        if (customerId != null) {
            Long addressId = nullableLong(rs, "address_id");
            AddressEntity address = addressId == null ? null : new AddressEntity(
                    addressId,
                    rs.getString("street"),
                    rs.getString("city"),
                    rs.getString("house_number"),
                    rs.getString("zip_code")
            );
            order.setCustomer(new CustomerEntity(
                    customerId,
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    address
            ));
        }
        return order;
    }

    private Long nullableLong(ResultSet rs, String column) throws SQLException {
        long value = rs.getLong(column);
        return rs.wasNull() ? null : value;
    }

    private List<ItemEntity> loadItems(long orderId) throws SQLException {
        return database.query("""
                SELECT i.id, i.name, i.description, i.price
                FROM ITEM i
                JOIN SHOPPING_CART sc ON i.id = sc.item_id
                WHERE sc.order_id = ?
                ORDER BY i.id
                """,
                rs -> new ItemEntity(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("price")
                ),
                orderId
        );
    }
}
