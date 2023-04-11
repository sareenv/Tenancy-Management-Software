package com.example.fxproject;

import Builder.OccupancyCreator;
import Interface.Constants;
import Model.Address;
import Model.Administrator;
import Model.Occupancy;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class PropertyController {

    @FXML TextField streetNumberTextField;
    @FXML TextField streetNameTextField;
    @FXML TextField cityTextField;
    @FXML TextField postalCodeField;
    @FXML TextField bedRoomCountTextField;
    @FXML TextField bathRoomCountTextField;
    @FXML TextField squareFootTextField;
    @FXML TextField apartmentNumberTextField;
    @FXML TextField unitNumberTextField;
    @FXML ComboBox<String> countryComboBox;
    @FXML RadioButton houseTypeRadioButton;
    @FXML RadioButton apartmentTypeRadioButton;
    @FXML RadioButton condoTypeRadioButton;
    @FXML ComboBox<String> filterComboBox;
    @FXML Button saveButton;

    // Private Properties.
    private ToggleGroup toggleGroup;
    private String propertyType = "";
    private BooleanProperty isBedRoomTextFieldVisible = new SimpleBooleanProperty(false);
    private BooleanProperty isBathRoomTextFieldVisible = new SimpleBooleanProperty(false);
    private BooleanProperty isSquareFootTextFieldVisible = new SimpleBooleanProperty(false);
    private BooleanProperty isUnitNoTextFieldVisible = new SimpleBooleanProperty(false);
    private BooleanProperty isApartmentTextFieldVisible = new SimpleBooleanProperty(false);

    // UIControls to be presented for the table view.
    @FXML TableView<Occupancy> occupancyTableView;
    @FXML TableColumn<Occupancy, String> occupancyTypeColumn;
    @FXML TableColumn<Occupancy, String> occupancyIDColumn;
    @FXML TableColumn<Occupancy, String> addressCityColumn;
    @FXML TableColumn<Occupancy, String> addressCountryColumn;
    @FXML TableColumn<Occupancy, String> addressStreetNameColumn;
    @FXML TableColumn<Occupancy, String> addressStreetNumberColumn;
    @FXML TableColumn<Occupancy, String> isOccupiedStatusColumn;

    // Controller Methods to link to the Models.
    private Administrator administrator = Administrator.makeSharedInstance();

    public void initialize() throws InterruptedException {

        ThreadRun thread = new ThreadRun("display");
        thread.start();
        thread.join();

        ArrayList<Occupancy> occupancyList = thread.occupancyList;
        loadTableViewData(occupancyList);
        occupancySelectionType();
        // default selection.
        toggleGroup.selectToggle(houseTypeRadioButton);
        bindTextFieldVisibility();
        prefetchFilterOptions();
        inputValueChanged();
    }

    private void occupancySelectionType() {
        toggleGroup = new ToggleGroup();
        houseTypeRadioButton.setToggleGroup(toggleGroup);
        houseTypeRadioButton.setUserData("house");
        apartmentTypeRadioButton.setToggleGroup(toggleGroup);
        apartmentTypeRadioButton.setUserData("apartment");
        condoTypeRadioButton.setToggleGroup(toggleGroup);
        condoTypeRadioButton.setUserData("condo");
        addPropertyChangeListener();
    }

    // Method to detect the input value changed for the filter combobox
    public void inputValueChanged() {
        filterComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "All Properties":
                        occupancyTableView.getItems().clear();
                        loadTableViewData(displayAllProperties());
                        break;

                    case "Rented Properties":
                        occupancyTableView.getItems().clear();
                        loadTableViewData(displayRentedProperties());
                        break;

                    case "Vacant Properties":
                        occupancyTableView.getItems().clear();
                        loadTableViewData(displayVacantProperties());
                        break;
                }
            }
        });
    }

    // Method to add the options for the filter options for the property.
    public void prefetchFilterOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add("All Properties");
        options.add("Rented Properties");
        options.add("Vacant Properties");
        filterComboBox.getItems().addAll(options);
        filterComboBox.setValue(options.get(0));
    }

    private Occupancy createOccupancy() throws InterruptedException{
        OccupancyCreator creator  = null;
        ThreadRun threadRun = null;
        Address address = new Address(streetNumberTextField.getText(),
                streetNameTextField.getText(),
                postalCodeField.getText(),
                cityTextField.getText(),
                countryComboBox.getValue());

        if (propertyType.equals("house")) {
            threadRun = new ThreadRun("createHouse");
            threadRun.homeOccupancy(address);
        } else if(propertyType.equals("apartment")) {
            try {
                int bedroom = Integer.parseInt(bedRoomCountTextField.getText());
                int bathroom = Integer.parseInt(bathRoomCountTextField.getText());
                double squareFoot = Double.parseDouble(squareFootTextField.getText());
                int apartmentNumber = Integer.parseInt(apartmentNumberTextField.getText());

                threadRun = new ThreadRun("createApartment");
                threadRun.apartmentOccupancy(address,bedroom,bathroom,squareFoot,apartmentNumber);

            } catch (Exception e) {
                showAlert("Invalid Type Error", e.getMessage());
                return null;
            }
        } else {
            // Creating a condo type.
            try {
                int bedroom = Integer.parseInt(bedRoomCountTextField.getText());
                int bathroom = Integer.parseInt(bathRoomCountTextField.getText());
                double squareFoot = Double.parseDouble(squareFootTextField.getText());
                int unitNumber = Integer.parseInt(unitNumberTextField.getText());

                threadRun = new ThreadRun("createCondo");
                threadRun.condoOccupancy(address,bedroom,bathroom,squareFoot,unitNumber);


            }catch (Exception e) {
                showAlert("Invalid Type Error", e.getMessage());
                return null;
            }
        }
        threadRun.start();
        threadRun.join();
        return threadRun.occupancy;
    }

    // Utility method to generate the alert message.
    private void showAlert(String errorTitle, String errorDescription) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.setContentText(errorDescription);
        alert.showAndWait();
    }

    public void handleSaveButtonClicked(ActionEvent event) throws InterruptedException {
        // TODO: - Handle Other labels also.
        if (propertyType.equals("")) {
            String messageContent = "Invalid property type selection";
            String messageTitle = "Selection Type Error";
            showAlert(messageTitle, messageContent);
        }
        Occupancy occupancy = createOccupancy();
        if (addProperty(occupancy)) {
            String messageContent = "Success";
            String messageTitle = "New property has been registered to the system";
            showAlert(messageContent, messageTitle);
        } else {
            String messageContent = "Error creating user";
            String messageTitle = "There has been a error registering the user.";
            showAlert(messageTitle, messageContent);
        }
    }

    // method to bind the text fields based on the address type selection.
    private void bindTextFieldVisibility() {
        bedRoomCountTextField.visibleProperty().bind(isBedRoomTextFieldVisible);
        bathRoomCountTextField.visibleProperty().bind(isBathRoomTextFieldVisible);
        squareFootTextField.visibleProperty().bind(isSquareFootTextFieldVisible);
        unitNumberTextField.visibleProperty().bind(isUnitNoTextFieldVisible);
        apartmentNumberTextField.visibleProperty().bind(isApartmentTextFieldVisible);
    }

    // Method to add the event listener to the property change of selected type.
    private void addPropertyChangeListener() {
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            String propertySelection = (String) newToggle.getUserData();
            propertyType = propertySelection;
            switch (propertySelection) {
                case "house":
                    isBedRoomTextFieldVisible.set(false);
                    isBathRoomTextFieldVisible.set(false);
                    isSquareFootTextFieldVisible.set(false);
                    isUnitNoTextFieldVisible.set(false);
                    isApartmentTextFieldVisible.set(false);
                    break;
                case "condo":
                    isUnitNoTextFieldVisible.set(true);
                    isBedRoomTextFieldVisible.set(true);
                    isBathRoomTextFieldVisible.set(true);
                    isSquareFootTextFieldVisible.set(true);
                    isApartmentTextFieldVisible.set(false);
                    break;
                case "apartment":
                    isApartmentTextFieldVisible.set(true);
                    isBedRoomTextFieldVisible.set(true);
                    isBathRoomTextFieldVisible.set(true);
                    isSquareFootTextFieldVisible.set(true);
                    isUnitNoTextFieldVisible.set(false);
                    break;
            }
        });
    }
    // Utility method to load the data based on the provided list.
    public void loadTableViewData(List<Occupancy> occupancyList) {
        ObservableList<Occupancy> data = FXCollections.observableArrayList(occupancyList);
        try {
            occupancyTableView.setItems(data);
            occupancyIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
            addressCityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.
                    getValue().getOccupancyAddress().city));
            addressCountryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOccupancyAddress().country));
            addressStreetNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOccupancyAddress().streetNumber));
            addressStreetNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOccupancyAddress().streetName));
            occupancyTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPropertyType()));
            isOccupiedStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isOccupancyAvailable() ? "Available" : "NOT Available"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setupCountryComboBox() {
        countryComboBox.getItems().addAll("United States",
                "Canada",
                "Mexico",
                "Brazil",
                "Argentina",
                "United Kingdom",
                "France",
                "Germany",
                "Spain",
                "Italy",
                "China",
                "Japan",
                "South Korea",
                "India",
                "Australia"
        );
        countryComboBox.setValue("Canada");
    }



    public Boolean addProperty(Occupancy occupancy) {
        try {
            administrator.addUnit(occupancy);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Occupancy> displayRentedProperties() {
        return Occupancy.findOccupanciesWithStatus(Constants.OccupancyPath, false);
    }

    // Utility method to display all the properties.
    public ArrayList<Occupancy> displayAllProperties() {
        return Occupancy.findOccupanciesWithStatus(Constants.OccupancyPath, null);
    }

    public ArrayList<Occupancy> displayVacantProperties() {
        ArrayList<Occupancy> rented = this.displayRentedProperties();
        ArrayList<Occupancy> allOccupancies = this.displayAllProperties();
        ArrayList<Occupancy> result = new ArrayList<>();
        for (Occupancy o: allOccupancies) {
            if (!rented.contains(o)) {
                result.add(o);
            }
        }
        return result;
    }
}