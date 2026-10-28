package com.example.vis_reservation.data.db;

import com.example.vis_reservation.domain.Address;
import com.example.vis_reservation.domain.Customer;
import com.example.vis_reservation.domain.Item;
import com.example.vis_reservation.domain.Order;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class Storage {
    private final Database database;

    public Storage(Database database) {
        this.database = database;
    }

    public void dropTables() throws SQLException {
        database.update("DROP TABLE IF EXISTS SHOPPING_CART");
        database.update("DROP TABLE IF EXISTS ORDERS");
        database.update("DROP TABLE IF EXISTS CUSTOMER");
        database.update("DROP TABLE IF EXISTS ITEM");
        database.update("DROP TABLE IF EXISTS ADDRESS");
    }

    public void createTables() throws SQLException {
        database.update("""
                CREATE TABLE IF NOT EXISTS ITEM (
                    id BIGINT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    description VARCHAR(500),
                    price DECIMAL(10, 2) NOT NULL
                )
                """
        );
        database.update("""
                CREATE TABLE IF NOT EXISTS ADDRESS (
                    id BIGINT PRIMARY KEY,
                    street VARCHAR(100),
                    city VARCHAR(100),
                    house_number VARCHAR(20),
                    zip_code VARCHAR(10)
                )
                """
        );
        database.update("""
                CREATE TABLE IF NOT EXISTS CUSTOMER (
                    id BIGINT PRIMARY KEY,
                    fname VARCHAR(100),
                    lname VARCHAR(100),
                    phone VARCHAR(30),
                    email VARCHAR(150),
                    address_id BIGINT,
                    FOREIGN KEY (address_id) REFERENCES ADDRESS(id)
                )
                """
        );
        database.update("""
                CREATE TABLE IF NOT EXISTS ORDERS (
                    id BIGINT PRIMARY KEY,
                    customer_id BIGINT,
                    CONSTRAINT FK_ORDERS_CUSTOMER FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id)
                )
                """
        );
        database.update("""
                CREATE TABLE IF NOT EXISTS SHOPPING_CART (
                    order_id BIGINT,
                    item_id BIGINT,
                    PRIMARY KEY (order_id, item_id),
                    FOREIGN KEY (order_id) REFERENCES ORDERS(id),
                    FOREIGN KEY (item_id) REFERENCES ITEM(id)
                )
                """
        );

        // migrace uz existujici databaze
        database.update("ALTER TABLE ORDERS ADD COLUMN IF NOT EXISTS customer_id BIGINT");
        database.update("""
                ALTER TABLE ORDERS ADD CONSTRAINT IF NOT EXISTS FK_ORDERS_CUSTOMER
                FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id)
                """
        );
    }

    public void seedData() throws SQLException {
        seedAddresses();
        seedCustomers();
        seedItems();
        seedOrders();
    }

    private void seedAddresses() throws SQLException {
        long count = count("ADDRESS");
        long nextId = nextId("ADDRESS");
        if (count < 1) {
            saveAddress(new Address(nextId++, "Hlavni", "Praha", "123", "11000"));
        }
        if (count < 2) {
            saveAddress(new Address(nextId++, "Skolska", "Ostrava", "45", "70200"));
        }
    }

    private void seedCustomers() throws SQLException {
        long count = count("CUSTOMER");
        long nextId = nextId("CUSTOMER");
        List<Long> addressIds = queryIds("SELECT id FROM ADDRESS ORDER BY id");
        if (count < 1) {
            saveCustomer(new Customer(nextId++, "Jan", "Novak", "+420 777 123 456",
                    "jan.novak@email.cz", findAddress(addressIds, 0)));
        }
        if (count < 2) {
            saveCustomer(new Customer(nextId++, "Petra", "Svobodova", "+420 605 987 654",
                    "petra.svobodova@email.cz", findAddress(addressIds, 1)));
        }
    }

    private void seedItems() throws SQLException {
        if (count("ITEM") > 0) {
            return;
        }
        saveItem(new Item(1, "Stul", "Dreveny jidelni stul", new BigDecimal("2499.90")));
        saveItem(new Item(2, "Zidle", "Polstrovana kancelarska zidle", new BigDecimal("1299.50")));
        saveItem(new Item(3, "Lampa", "Stojaci lampa", new BigDecimal("899.00")));
    }

    public void saveAddress(Address address) throws SQLException {
        database.update("""
                INSERT INTO ADDRESS (id, street, city, house_number, zip_code)
                VALUES (?, ?, ?, ?, ?)
                """,
                address.getId(), address.getStreet(), address.getCity(),
                address.getHouseNumber(), address.getZipCode()
        );
    }

    public void saveCustomer(Customer customer) throws SQLException {
        database.update("""
                INSERT INTO CUSTOMER (id, fname, lname, phone, email, address_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                customer.getId(), customer.getFname(), customer.getLname(),
                customer.getPhone(), customer.getEmail(),
                customer.getAddress() != null ? customer.getAddress().getId() : null
        );
    }

    public void saveItem(Item item) throws SQLException {
        database.update("""
                INSERT INTO ITEM (id, name, description, price)
                VALUES (?, ?, ?, ?)
                """,
                item.getId(), item.getName(), item.getDesc(), item.getPrice()
        );
    }

    public void saveOrder(Order order) throws SQLException {
        database.update("""
                INSERT INTO ORDERS (id, customer_id)
                VALUES (?, ?)
                """,
                order.getId(),
                order.getCustomer() != null ? order.getCustomer().getId() : null
        );
        for (Item item : order.getItems()) {
            database.update("""
                    INSERT INTO SHOPPING_CART (order_id, item_id)
                    VALUES (?, ?)
                    """,
                    order.getId(), item.getId()
            );
        }
    }

    private void seedOrders() throws SQLException {
        List<Long> customerIds = queryIds("SELECT id FROM CUSTOMER ORDER BY id");
        List<Long> itemIds = queryIds("SELECT id FROM ITEM ORDER BY id");
        if (customerIds.isEmpty() || itemIds.isEmpty()) {
            return;
        }

        List<Long> orderIds = queryIds("SELECT id FROM ORDERS ORDER BY id");
        if (orderIds.isEmpty()) {
            createOrders(customerIds, itemIds);
        } else {
            assignMissingCustomers(customerIds);
        }
    }

    private void createOrders(List<Long> customerIds, List<Long> itemIds) throws SQLException {
        long orderId = nextId("ORDERS");
        int itemIndex = 0;
        for (Long customerId : customerIds) {
            if (itemIndex >= itemIds.size()) {
                break;
            }
            database.update("INSERT INTO ORDERS (id, customer_id) VALUES (?, ?)", orderId, customerId);
            int itemsInOrder = Math.min(2, itemIds.size() - itemIndex);
            for (int i = 0; i < itemsInOrder; i++) {
                database.update("""
                        INSERT INTO SHOPPING_CART (order_id, item_id)
                        VALUES (?, ?)
                        """,
                        orderId, itemIds.get(itemIndex++)
                );
            }
            orderId++;
        }
    }

    private void assignMissingCustomers(List<Long> customerIds) throws SQLException {
        List<Long> ordersWithoutCustomer = queryIds(
                "SELECT id FROM ORDERS WHERE customer_id IS NULL ORDER BY id");
        int index = 0;
        for (Long orderId : ordersWithoutCustomer) {
            database.update("UPDATE ORDERS SET customer_id = ? WHERE id = ?",
                    customerIds.get(index % customerIds.size()), orderId);
            index++;
        }
    }

    private long count(String table) throws SQLException {
        List<Long> result = database.query("SELECT COUNT(*) FROM " + table, rs -> rs.getLong(1));
        return result.isEmpty() ? 0 : result.get(0);
    }

    private long nextId(String table) throws SQLException {
        List<Long> result = database.query(
                "SELECT COALESCE(MAX(id), 0) + 1 FROM " + table, rs -> rs.getLong(1));
        return result.isEmpty() ? 1 : result.get(0);
    }

    private List<Long> queryIds(String sql) throws SQLException {
        return database.query(sql, rs -> rs.getLong(1));
    }

    private Address findAddress(List<Long> addressIds, int index) throws SQLException {
        if (addressIds.isEmpty()) {
            return null;
        }
        long id = addressIds.get(Math.min(index, addressIds.size() - 1));
        List<Address> addresses = database.query(
                "SELECT id, street, city, house_number, zip_code FROM ADDRESS WHERE id = ?",
                rs -> new Address(
                        rs.getLong("id"),
                        rs.getString("street"),
                        rs.getString("city"),
                        rs.getString("house_number"),
                        rs.getString("zip_code")
                ),
                id
        );
        return addresses.isEmpty() ? null : addresses.get(0);
    }
}
