package com.example.vis_reservation.data.connector;

import com.example.vis_reservation.data.dao.CustomerSqlDao;
import com.example.vis_reservation.data.dao.ICustomerDao;
import com.example.vis_reservation.data.dao.IItemDao;
import com.example.vis_reservation.data.dao.IOrderDao;
import com.example.vis_reservation.data.dao.ItemSqlDao;
import com.example.vis_reservation.data.dao.OrderSqlDao;

public class SQLConnector implements IDataConnection {

    public SQLConnector() {
    }

    @Override
    public IItemDao createItemDao() {
        return new ItemSqlDao();
    }

    @Override
    public IOrderDao createOrderDao() {
        return new OrderSqlDao();
    }

    @Override
    public ICustomerDao createCustomerDao() {
        return new CustomerSqlDao();
    }
}
