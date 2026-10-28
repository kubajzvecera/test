package com.example.vis_reservation.presentation;

import com.example.vis_reservation.data.connector.GlobalConnector;
import com.example.vis_reservation.data.connector.SQLConnector;
import com.example.vis_reservation.data.db.Database;
import com.example.vis_reservation.data.db.Storage;
import com.example.vis_reservation.domain.service.ItemService;
import com.example.vis_reservation.domain.service.OrderService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Database database = Database.getInstance();
            database.init();
            Storage storage = new Storage(database);
            storage.createTables();
            storage.seedData();
            database.startWebServer();
        } catch (SQLException e) {
            throw new IOException("Nepodarilo se inicializovat databazi", e);
        }

        GlobalConnector connector = new GlobalConnector(new SQLConnector());
        ItemService itemService = connector.createItemService();
        OrderService orderService = connector.createOrderService();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 680);
        ItemController controller = fxmlLoader.getController();
        controller.setItemService(itemService);

        OrderController orderController =
                (OrderController) fxmlLoader.getNamespace().get("orderViewController");
        orderController.setServices(orderService);

        stage.setTitle("VIS - Item / Objednavka");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        Database.getInstance().stopWebServer();
        Database.getInstance().close();
    }
}
