package com.example.fxproject;

import Interface.Constants;
import Model.Administrator;
import Model.Lease;
import Model.Occupancy;
import Model.Tenant;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class LeaseController {

    @FXML DatePicker leaseStartDatePicker;
    @FXML DatePicker leaseEndDatePicker;
    @FXML ComboBox<String> occupanciesComboBox;
    @FXML ComboBox<String> tenantComboBox;
    @FXML TabPane leaseTabPane;

    // All lease table view.
    @FXML TableView<Lease> allLeaseTableView;
    @FXML TableColumn<Lease, String> tenantIdColumn;
    @FXML TableColumn<Lease, String> tenantNameColumn;
    @FXML TableColumn<Lease, String> leaseStartingTableColumn;
    @FXML TableColumn<Lease, String> leaseEndTableColumn;
    @FXML TableColumn<Lease, String> occupancyIdColumn;
    @FXML TableColumn<Lease,String> occupancyAddressColumn;
    @FXML TableColumn<Lease,Void> deleteColumn;

    // Private Attributes.
    private Administrator administrator;
    private Lease lease;

    // Initialize method for the lease controller.
    public void initialize() {
        administrator = Administrator.makeSharedInstance();
        handleTabPaneSwitch();
        prefetchOccupancies();
        prefetchAllTenants();
    }

    public void loadAllLeaseTableViewData() {
        getAllLease();
        ObservableList<Lease> data = FXCollections.observableArrayList(getAllLease());

        try {
            allLeaseTableView.setItems(data);
            occupancyIdColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData
                            .getValue()
                            .getOccupancy()
                            .id.toString()));

            occupancyAddressColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getOccupancy()
                            .getOccupancyAddress().toString())
                    );

            leaseStartingTableColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData
                            .getValue()
                            .getEndDate()
                            .toString())
                    );

            leaseEndTableColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData
                            .getValue()
                            .getStartDate()
                            .toString())
                    );

            tenantNameColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData
                            .getValue()
                            .getTenant()
                            .name)
                    );

            tenantIdColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData
                            .getValue()
                            .getTenant()
                            .id
                            .toString()
                    )
            );

            deleteColumn.setCellFactory(param -> new TableCell<Lease, Void>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        Lease lease = getTableRow().getItem();
                        // TODO: Delete the person from your data model.
                        lease.getOccupancy().setIsOccupancyAvailable(true);
                        boolean result = lease.deleteLease();
                        System.out.println(result);
                        allLeaseTableView.getItems().clear();
                        administrator.notifyAllObserver();
                        loadAllLeaseTableViewData();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            });

        } catch (Exception e) {
            showAlert("Error", e.getMessage());
            System.out.println(e);
        }
    }

    // Method to handle the tab pane switch.
    private void handleTabPaneSwitch() {
        leaseTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            // do something when a new tab is selected
            if (newTab.getText().equals("Create a Lease")) {
                System.out.println("Create Tenant is selected from the tab pane");
            } else if(newTab.getText().equals("All Lease")) {
                loadAllLeaseTableViewData();
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

    // Method to find all the occupancies and fill the combobox.
    public void prefetchOccupancies() {
        ArrayList<Occupancy> occupancies =  Occupancy.findOccupanciesWithStatus(Constants.OccupancyPath, null);
        ArrayList<String> occupancyIDsList = new ArrayList<>();
        for (Occupancy occupancy: occupancies) {
            String occupancyString = occupancy.getId().toString() + "~ \n"
             + occupancy.getOccupancyAddress().toString();
            occupancyIDsList.add(occupancyString);
        }
        occupanciesComboBox.getItems().addAll(occupancyIDsList);
    }

    public void prefetchAllTenants() {
        ArrayList<Tenant> allTenants =  Tenant.getAllTenants();
        ArrayList<String> tenantDescriptionList = new ArrayList<>();
        for (Tenant tenant: allTenants) {
            String tenantString = tenant.id + " - " + tenant.name;
            tenantDescriptionList.add(tenantString);
        }
        tenantComboBox.getItems().addAll(tenantDescriptionList);
    }

    // Utility method to generate the alert message.
    private void showAlert(String errorTitle, String errorDescription) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.setContentText(errorDescription);
        alert.showAndWait();
    }

    private void registerLease() {
        String tenantID = tenantComboBox.getValue();
        String occupancyId = occupanciesComboBox.getValue();
        if (tenantID == null || occupancyId == null) {
            showAlert("Unselected", "Please check occupancy " +
                    "or tenant is unselected ");
            return;
        }

        if (!tenantID.contains("-") || !occupancyId.contains("~")) {
            showAlert("Unselected", "Please check occupancy " +
                    "or tenant is unselected ");
            return;
        }
        tenantID = tenantID.replaceAll(" - .*", "");
        occupancyId = occupancyId.substring(0, occupancyId.indexOf("~"));
        Tenant tenant = Tenant.findTenantByID(tenantID);
        Occupancy occupancy = Occupancy.findOccupancyByID(occupancyId, Constants.OccupancyPath);
        LocalDate startLocalDate = leaseStartDatePicker.getValue();
        Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate endLocalDate = leaseEndDatePicker.getValue();
        Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (tenant == null || occupancy == null) {
            // Unable to find the tenant with specified id.
            showAlert("Error", "Tenant or Occupancy could not be located with " +
                    "the selected id");
        } else {
            Lease lease  = new Lease(occupancy, tenant, startDate, endDate);
            if (lease.checkLeaseValidity()) {
                try {
                    lease.registerLease();
                    showAlert("Success", "A lease for the tenant " +
                            "has been registered");
                }catch (Exception e) {
                    showAlert("Error", e.getMessage());
                }
            } else {
                showAlert("Invalid Dates Error", "Invalid dates are used to register the lease");
            }
        }
    }

    // Method to handle the button click event.
    public void handleNewLeaseSave() {
        registerLease();
    }

}
