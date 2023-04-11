package com.example.fxproject;

import Model.Notification;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class NotificationController {
   @FXML
    TableView<Notification> notificationTableView;
    TableColumn<Notification, String> tenantIDColumn;
    TableColumn<Notification, String> tenantNameColumn;
    TableColumn<Notification, String> occupancyIDColumn;
    TableColumn<Notification, String> occupancyAddressColumn;
    TableColumn<Notification, String> notificationMessageColumn;

    // Private attributes.
    private static ArrayList<Notification> notifications = new ArrayList<>();

    public void initialize() {
        notificationTableView.getItems().clear();
        loadTableViewData(notifications);
    }

    public void loadTableViewData(List<Notification> list) {
        ObservableList<Notification> data = FXCollections.observableArrayList(list);
        try {
            notificationTableView.setItems(data);
            tenantIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenantID()));
            tenantNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenantName()));
            occupancyIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOccupancyId()));
            occupancyAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
            notificationMessageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updatedNotification(Notification notification) {
        notifications.add(notification);
    }
}
