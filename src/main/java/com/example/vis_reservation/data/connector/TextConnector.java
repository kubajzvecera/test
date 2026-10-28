package com.example.vis_reservation.data.connector;

import com.example.vis_reservation.data.dao.CustomerTextDao;
import com.example.vis_reservation.data.dao.ICustomerDao;
import com.example.vis_reservation.data.dao.IItemDao;
import com.example.vis_reservation.data.dao.IOrderDao;
import com.example.vis_reservation.data.dao.ItemTextDao;
import com.example.vis_reservation.data.dao.OrderTextDao;

public class TextConnector implements IDataConnection {

    public TextConnector() {
    }

    @Override
    public IItemDao createItemDao() {
        return new ItemTextDao();
    }

    @Override
    public IOrderDao createOrderDao() {
        return new OrderTextDao();
    }

    @Override
    public ICustomerDao createCustomerDao() {
        return new CustomerTextDao();
    }
}
