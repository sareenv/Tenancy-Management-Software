package com.example.fxproject;

import Model.Administrator;
import Model.Tenant;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class TenantController {
    @FXML TextField nameTextField;
    @FXML TextField monthlySalaryTextField;
    @FXML ComboBox<String> occupationsComboBox;
    @FXML Button saveButton;

    // TODO: - Need to complete this section.
    @FXML TableView<Tenant> tenantTableView;
    @FXML TableColumn<Tenant, String> tenantIdColumn;
    @FXML TableColumn<Tenant, String> tenantNameColumn;
    @FXML TableColumn<Tenant, String> tenantOccupationColumn;
    @FXML TableColumn<Tenant, String> tenantMonthlySalaryColumn;


    public void loadTableViewData(List<Tenant> tenantsList) {
        ObservableList<Tenant> data = FXCollections.observableArrayList(tenantsList);
        try {
            tenantTableView.setItems(data);
            tenantIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.
                    getValue().id.toString()));
            tenantNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.
                    getValue().name));
            tenantOccupationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().occupation));
            tenantMonthlySalaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().monthlyIncome.toString()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private Administrator administrator;

    @FXML
    public void initialize() {
        administrator = Administrator.makeSharedInstance();
        addComboOptionsMenu();
        ArrayList<Tenant> allTenants = displayAllTenants();
        loadTableViewData(allTenants);
    }

    public ArrayList<Tenant> displayAllTenants() {
        return Tenant.getAllTenants();
    }

    public void addComboOptionsMenu() {
        String[] topOccupations = {"Software Developer", "Nurse", "Physician", "Dentist", "Accountant",
                "Lawyer", "Teacher", "Engineer", "Police Officer", "Construction Worker", "Chef",
                "Salesperson", "Marketing Manager", "Human Resources Manager", "Financial Advisor",
                "Writer", "Artist", "Musician", "Athlete", "Actor", "Other"};
        occupationsComboBox.getItems().addAll(topOccupations);
        occupationsComboBox.setValue("Other");
    }

    // Utility method to generate the alert message.
    private void showAlert(String errorTitle, String errorDescription) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.setContentText(errorDescription);
        alert.showAndWait();
    }

    public void saveTenantButtonClicked(ActionEvent event) {
        try {
            String name = nameTextField.getText();
            String occupation = occupationsComboBox.getValue();
            Double income = Double.parseDouble(monthlySalaryTextField.getText());
            boolean result = addTenant(name, occupation, income);
            if (result) {
                // TODO: - Show the alert message with the success.
                showAlert("Success", "Tenant has been registered to our system");
            } else {
                // TODO:  - Show the alert message with failure.
                showAlert("Failure", "Tenant has not been registered to our system");
            }
            nameTextField.clear();
            occupationsComboBox.setValue("Other");
            monthlySalaryTextField.clear();
        } catch (Exception exception) {
            showAlert("Error saving tenant", "Invalid values has been supplied.");
        }
    }

    public Boolean addTenant(String name, String occupation, Double monthlyIncome) {
        return administrator.addTenant(name, occupation, monthlyIncome);
    }
}
