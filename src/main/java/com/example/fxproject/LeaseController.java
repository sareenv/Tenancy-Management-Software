package com.example.fxproject;

import Interface.Constants;
import Model.Administrator;
import Model.Lease;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class LeaseController {


    @FXML TextField tenantIDTextField;
    @FXML DatePicker leaseStartDatePicker;
    @FXML DatePicker leaseEndDatePicker;
    @FXML ComboBox<String> occupanciesComboBox;
    @FXML TabPane leaseTabPane;

    // Private Attributes.
    private Administrator administrator;
    private Lease lease;

    // Initialize method for the lease controller.
    public void initialize() {
        administrator = Administrator.makeSharedInstance();
        leaseTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            // do something when a new tab is selected
            if (newTab.getText().equals("Create a Lease")) {
                System.out.println("Create Tenant is selected from the tab pane");
            } else if(newTab.getText().equals("All Lease")) {
                ArrayList<Lease> allLease = getAllLease();
                System.out.println("The size of all lease is " + allLease.size());
            } else {
                System.out.println("Expired leases are selected");
            }
        });
    }


    // Method to the data for all the lease.
    public ArrayList<Lease> getAllLease() {
        return Lease.getAllLease(Constants.LeasePath);
    }

    // Method to get the expired lease.
    public ArrayList<Lease> getExpiredLease() {
        return Lease.getExpiredLease(getAllLease());
    }


    // Method to find all the occupancies.
    public void findAllOccupancies() {

    }

    // Utility method to generate the alert message.
    private void showAlert(String errorTitle, String errorDescription) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.setContentText(errorDescription);
        alert.showAndWait();
    }

    // Method to handle the button click event.
    public void handleNewLeaseSave() {
        System.out.println("Lease saved is clicked");
    }

}
