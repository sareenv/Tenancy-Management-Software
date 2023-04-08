package com.example.fxproject;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PropertyController {
    @FXML
    TextField streetNumberTextField;

    @FXML
    TextField streetNameTextField;

    @FXML
    TextField cityTextField;

    @FXML
    TextField postalCodeField;

    @FXML
    ComboBox<String> countryComboBox;

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