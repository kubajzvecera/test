module com.example.vis_reservation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;


    opens com.example.vis_reservation.presentation to javafx.fxml;
    exports com.example.vis_reservation.presentation;
    exports com.example.vis_reservation.data.connector;
    exports com.example.vis_reservation.data.dao;
    exports com.example.vis_reservation.data.db;
    exports com.example.vis_reservation.domain;
    exports com.example.vis_reservation.domain.service;
    exports com.example.vis_reservation.domain.mapper;
    exports com.example.vis_reservation.data.entity;
}