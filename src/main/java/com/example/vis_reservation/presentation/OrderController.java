package com.example.vis_reservation.presentation;

import com.example.vis_reservation.domain.Customer;
import com.example.vis_reservation.domain.Item;
import com.example.vis_reservation.domain.Order;
import com.example.vis_reservation.domain.service.OrderService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.util.List;

public class OrderController {

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Long> orderIdColumn;

    @FXML
    private TableColumn<Order, String> customerColumn;

    @FXML
    private TableView<Item> orderItemsTable;

    @FXML
    private TableColumn<Item, Long> itemIdColumn;

    @FXML
    private TableColumn<Item, String> itemNameColumn;

    @FXML
    private TableColumn<Item, String> itemDescColumn;

    @FXML
    private TableColumn<Item, BigDecimal> itemPriceColumn;

    @FXML
    private Label statusLabel;

    private OrderService orderService;

    @FXML
    private void initialize() {
        ordersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrder(newValue)
        );
    }

    public void setServices(OrderService orderService) {
        this.orderService = orderService;
        configureColumns();
        refreshOrders();
    }

    private void configureColumns() {
        orderIdColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
        customerColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(customerName(data.getValue())));

        itemIdColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
        itemNameColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
        itemDescColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDesc()));
        itemPriceColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getPrice()));
    }

    private String customerName(Order order) {
        Customer customer = order.getCustomer();
        if (customer == null) {
            return "-";
        }
        return customer.getFname() + " " + customer.getLname();
    }

    private void showOrder(Order order) {
        if (order == null) {
            orderItemsTable.getItems().clear();
            return;
        }
        orderItemsTable.getItems().setAll(order.getItems());
    }

    private void refreshOrders() {
        if (orderService == null) {
            return;
        }
        try {
            List<Order> orders = orderService.getAllOrders();
            ordersTable.getItems().setAll(orders);
            showOrder(null);
            statusLabel.setText("Nacteno objednavek: " + orders.size());
        } catch (RuntimeException e) {
            statusLabel.setText("Chyba cteni DB: " + rootMessage(e));
        }
    }

    private static String rootMessage(RuntimeException e) {
        Throwable throwable = e;
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        String message = throwable.getMessage();
        return message == null ? throwable.toString() : message;
    }
}
