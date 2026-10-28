package com.example.vis_reservation.data.connector;

import com.example.vis_reservation.data.dao.IItemDao;
import com.example.vis_reservation.data.dao.IOrderDao;
import com.example.vis_reservation.domain.service.ItemService;
import com.example.vis_reservation.domain.service.OrderService;

public class GlobalConnector {

    private IDataConnection connection;

    public GlobalConnector(IDataConnection connection) {
        this.connection = connection;
    }

    public IDataConnection getDataConnection() {
        return connection;
    }

    public void setDataConnection(IDataConnection connection) {
        this.connection = connection;
    }

    public IItemDao createItemDao() {
        return connection.createItemDao();
    }

    public IOrderDao createOrderDao() {
        return connection.createOrderDao();
    }

    public ItemService createItemService() {
        return new ItemService(connection.createItemDao());
    }

    public OrderService createOrderService() {
        return new OrderService(connection.createOrderDao());
    }
}
