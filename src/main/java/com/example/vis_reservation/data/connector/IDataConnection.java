package com.example.vis_reservation.data.connector;

import com.example.vis_reservation.data.dao.ICustomerDao;
import com.example.vis_reservation.data.dao.IItemDao;
import com.example.vis_reservation.data.dao.IOrderDao;

public interface IDataConnection {

    IItemDao createItemDao();

    IOrderDao createOrderDao();

    ICustomerDao createCustomerDao();
}
