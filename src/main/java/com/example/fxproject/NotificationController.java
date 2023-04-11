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
    @FXML TableView<Notification> notificationTableView;
    @FXML TableColumn<Notification, String> tenantIDColumn;
    @FXML TableColumn<Notification, String> tenantNameColumn;
    @FXML TableColumn<Notification, String> occupancyIDColumn;
    @FXML TableColumn<Notification, String> occupancyAddressColumn;
    @FXML TableColumn<Notification, String> notificationMessageColumn;

    // Private attributes.
    static ArrayList<Notification> notifications = new ArrayList<>();

    public void initialize() {
        notificationTableView.getItems().clear();
        loadTableViewData(NotificationController.notifications);
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
        NotificationController.notifications.add(notification);
    }
}
