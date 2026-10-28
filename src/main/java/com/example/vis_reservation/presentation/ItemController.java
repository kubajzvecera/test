package com.example.vis_reservation.presentation;

import com.example.vis_reservation.domain.Item;
import com.example.vis_reservation.domain.service.ItemService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.List;

public class ItemController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descField;

    @FXML
    private TextField priceField;

    @FXML
    private Label statusLabel;

    @FXML
    private ListView<String> itemsList;

    private ItemService itemService;

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
        refreshNextId();
        refreshItems();
    }

    @FXML
    protected void onSaveClick() {
        if (itemService == null) {
            statusLabel.setText("Chyba: service neni nastaven.");
            return;
        }
        try {
            long id = Long.parseLong(idField.getText().trim());
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            BigDecimal price = new BigDecimal(priceField.getText().trim());
            if (name.isEmpty()) {
                statusLabel.setText("Chyba: name je povinne.");
                return;
            }
            itemService.save(new Item(id, name, desc, price));
            statusLabel.setText("Ulozeno: id=" + id);
            nameField.clear();
            descField.clear();
            priceField.clear();
            refreshNextId();
            refreshItems();
        } catch (NumberFormatException e) {
            statusLabel.setText("Chyba: id musi byt cele cislo a price desetinne cislo.");
        } catch (RuntimeException e) {
            statusLabel.setText("Chyba ukladani: " + rootMessage(e));
        }
    }

    private void refreshNextId() {
        if (itemService == null) {
            return;
        }
        try {
            idField.setText(String.valueOf(itemService.nextId()));
        } catch (RuntimeException e) {
            statusLabel.setText("Chyba cteni DB: " + rootMessage(e));
        }
    }

    private void refreshItems() {
        if (itemService == null) {
            return;
        }
        try {
            List<Item> items = itemService.getAllItems();
            itemsList.getItems().setAll(items.stream().map(Item::toString).toList());
        } catch (RuntimeException e) {
            statusLabel.setText("Chyba cteni DB: " + rootMessage(e));
        }
    }

    private static String rootMessage(RuntimeException e) {
        Throwable t = e;
        while (t.getCause() != null) {
            t = t.getCause();
        }
        String msg = t.getMessage();
        return msg == null ? t.toString() : msg;
    }
}
