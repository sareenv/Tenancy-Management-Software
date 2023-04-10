module com.example.fxproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;

    opens com.example.fxproject to javafx.fxml;
    opens Tests to junit;
    opens Model to javafx.controls;
    exports com.example.fxproject;
    opens Tests.Objects to junit;

}