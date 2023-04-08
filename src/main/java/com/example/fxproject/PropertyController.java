package com.example.fxproject;

import Builder.OccupancyCreator;
import Interface.Constants;
import Model.Address;
import Model.Administrator;
import Model.House;
import Model.Occupancy;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    // Private Properties.
    private ToggleGroup toggleGroup;
    private BooleanProperty isTextFieldVisible = new SimpleBooleanProperty(false);

    // UIControls to be presented for the table view.
    @FXML TableView<Occupancy> occupancyTableView;
    @FXML TableColumn<Occupancy, String> occupancyIDColumn;
    @FXML TableColumn<Occupancy, String> addressCityColumn;
    @FXML TableColumn<Occupancy, String> addressCountryColumn;
    @FXML TableColumn<Occupancy, String> addressStreetNameColumn;
    @FXML TableColumn<Occupancy, String> addressStreetNumberColumn;

    public void initialize() {
        ArrayList<Occupancy> occupancyList = Occupancy.findOccupanciesWithStatus(Constants.OccupancyPath, null);
        System.out.println("Fetched list is " + occupancyList);
        loadTableViewData();
        occupancySelectionType();
        bedRoomCountTextField.visibleProperty().bind(isTextFieldVisible);
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

    // Method to add the event listener to the property change of selected type.
    private void addPropertyChangeListener() {
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            String propertySelection = (String) newToggle.getUserData();
            System.out.println("Property selection is " + propertySelection);
            if (propertySelection.equals("house")) {
                isTextFieldVisible.set(false);
            } else {
                isTextFieldVisible.set(true);
            }
        });
    }

    public void loadTableViewData() {
        // dummy occupancy.
        Address houseAddress = new Address("2175", "De Maisonnuve Ouest",
                "H3H 1L5", "Montreal", "Canada");
        OccupancyCreator creator = new OccupancyCreator.Builder(houseAddress).build();
        Occupancy occupancy = creator.createRentalUnit();

        ObservableList<Occupancy> data = FXCollections.observableArrayList(
                occupancy,
                occupancy,
                occupancy,
                occupancy
        );
        occupancyTableView.setItems(data);
        occupancyIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        addressCityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.
                getValue().getOccupancyAddress().city));
        addressCountryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOccupancyAddress().country));
        addressStreetNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOccupancyAddress().streetNumber));
        addressStreetNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOccupancyAddress().streetName));
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
}