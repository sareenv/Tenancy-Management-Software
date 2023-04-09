package com.example.fxproject;

import Builder.OccupancyCreator;
import Interface.Constants;
import Model.Address;
import Model.Administrator;
import Model.Occupancy;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
    @FXML TableColumn<Occupancy, String> occupancyIDColumn;
    @FXML TableColumn<Occupancy, String> addressCityColumn;
    @FXML TableColumn<Occupancy, String> addressCountryColumn;
    @FXML TableColumn<Occupancy, String> addressStreetNameColumn;
    @FXML TableColumn<Occupancy, String> addressStreetNumberColumn;

    public void initialize() {
        // TODO: - Fix this and load data from the file system.
        ArrayList<Occupancy> occupancyList = Occupancy.findOccupanciesWithStatus(Constants.OccupancyPath, null);
        loadTableViewData(occupancyList);
        occupancySelectionType();
        bindTextFieldVisibility();
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

    private Occupancy createOccupancy() {
        OccupancyCreator creator  = null;
        Address address = new Address(streetNumberTextField.getText(),
                streetNameTextField.getText(),
                postalCodeField.getText(),
                cityTextField.getText(),
                countryComboBox.getValue());

        if (propertyType.equals("house")) {
            creator = new OccupancyCreator.Builder(address).build();
            return creator.createRentalUnit();
        } else if(propertyType.equals("apartment")) {
            // Creating a condo type.
            try {
                int bedroom = Integer.parseInt(bedRoomCountTextField.getText());
                int bathroom = Integer.parseInt(bathRoomCountTextField.getText());
                double squareFoot = Double.parseDouble(squareFootTextField.getText());
                int apartmentNumber = Integer.parseInt(apartmentNumberTextField.getText());
                creator = new OccupancyCreator.Builder(bedroom,
                        bathroom ,
                        squareFoot,
                        address, apartmentNumber).build();
                return creator.createRentalUnit();
            } catch (Exception e) {
                // TODO: - Throw the error message.
                showAlert("Invalid Type Error", e.getMessage());
                return null;
            }
        } else {
            // Creating an apartment type.
            try {
                int bedroom = Integer.parseInt(bedRoomCountTextField.getText());
                int bathroom = Integer.parseInt(bathRoomCountTextField.getText());
                double squareFoot = Double.parseDouble(squareFootTextField.getText());
                int unitNumber = Integer.parseInt(unitNumberTextField.getText());
                System.out.println(unitNumberTextField.getText());
                creator = new OccupancyCreator.Builder(unitNumber,
                        bathroom,
                        bedroom,
                        squareFoot,
                        address).build();
                return creator.createRentalUnit();
            }catch (Exception e) {
                showAlert("Invalid Type Error", e.getMessage());
                return null;
            }
        }

    }

    // Utility method to generate the alert message.
    private void showAlert(String errorTitle, String errorDescription) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.setContentText(errorDescription);
        alert.showAndWait();
    }

    public void handleSaveButtonClicked(ActionEvent event) {
        // TODO: - Handle Other labels also.
        if (propertyType.equals("")) {
            String messageContent = "Invalid property type selection";
            String messageTitle = "Selection Type Error";
            showAlert(messageTitle, messageContent);
        }
        Occupancy occupancy = createOccupancy();
        if (addProperty(occupancy)) {
            String messageContent = "Create User";
            String messageTitle = "User has been create with us";
            showAlert(messageTitle, messageContent);
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

    public void loadTableViewData(List<Occupancy> occupancyList) {
        // dummy occupancy.
        Address houseAddress = new Address("2175", "De Maisonnuve Ouest",
                "H3H 1L5", "Montreal", "Canada");
        OccupancyCreator creator = new OccupancyCreator.Builder(houseAddress).build();
        Occupancy occupancy = creator.createRentalUnit();

        ObservableList<Occupancy> data = FXCollections.observableArrayList(occupancyList);
        try {
            occupancyTableView.setItems(data);
            occupancyIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
            addressCityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.
                    getValue().getOccupancyAddress().city));
            addressCountryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOccupancyAddress().country));
            addressStreetNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOccupancyAddress().streetNumber));
            addressStreetNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOccupancyAddress().streetName));
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

    // Controller Methods to link to the Models.
    private Administrator administrator = Administrator.makeSharedInstance();

    public Boolean addProperty(Occupancy occupancy) {
        try {
            administrator.addUnit(occupancy);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // TODO: - Need to integrate this.
    public ArrayList<Occupancy> displayRentedProperties() {
        return Occupancy.findOccupanciesWithStatus(Constants.OccupancyPath, false);
    }

    // Utility method to display all the properties.
    public ArrayList<Occupancy> displayAllProperties() {
        return Occupancy.findOccupanciesWithStatus(Constants.OccupancyPath, null);
    }

    // TODO: - Need to integrate this.
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