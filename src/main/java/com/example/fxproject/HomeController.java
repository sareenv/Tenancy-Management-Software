
package com.example.fxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;

// Controller Class that loads different components of javafx and their logic
public class HomeController {

    // loading ImageView, HBox, AnchorPane
    @FXML
    ImageView homeImageView;

    @FXML
    ImageView tenantsImageView;

    @FXML
    ImageView notificationImageView;

    @FXML
    ImageView leaseImageView;

    @FXML
    HBox homeMenuBox;

    @FXML
    HBox tenantMenuBox;

    @FXML
    HBox notificationMenuBox;

    @FXML
    HBox leaseMenuBox;

    @FXML
    AnchorPane viewerPane;

    // controller associated for setting the GUI color
    private void resetSelectedColors(HBox selectedOptions) {

        ArrayList<HBox> menuOptions = new ArrayList<>();
        menuOptions.add(homeMenuBox);
        menuOptions.add(tenantMenuBox);
        menuOptions.add(notificationMenuBox);
        menuOptions.add(leaseMenuBox);

        for(HBox box: menuOptions) {
            if (box != selectedOptions) {
                box.setStyle("-fx-background-color:  #00ADB5");
            } else {
                box.setStyle("-fx-background-color: #393E46");
            }
        }
    }

    private Object loadScene(URL url) {
        try {
            if (url != null) {
                FXMLLoader loader = new FXMLLoader(url);
                AnchorPane pane  = loader.load();
                System.out.println(pane);
                Object controller = null;
                controller = loader.getController();
                viewerPane.getChildren().setAll(pane);
                return controller;
            } else {
                System.out.println("URL is missing");
            }
        } catch (LoadException e) {
            System.out.println("Load Exception " + e.getMessage());
        }catch (Exception e) {
            System.out.println("Exception" + e);
        }
        return null;
    }

    public void loadHomeScene() {
        URL url = getClass().getResource("Property.fxml");
        PropertyController controller = (PropertyController) loadScene(url);
        if (controller != null) {
            controller.setupCountryComboBox();
        }
    }

    @FXML
    public void homeSceneSelection() {
        // Platform.runLater to be running on the UI/Application thread.
        loadHomeScene();
        resetSelectedColors(homeMenuBox);
    }

    @FXML
    public void tenantSceneSelection() {
        URL url = getClass().getResource("Tenants.fxml");
        loadScene(url);
        resetSelectedColors(tenantMenuBox);
    }

    @FXML
    public void notificationSceneSelection() {
        URL url = getClass().getResource("Notifications.fxml");
        loadScene(url);
        resetSelectedColors(notificationMenuBox);
    }

    @FXML
    public void leaseSceneSelection() {
        URL url = getClass().getResource("Lease.fxml");
        loadScene(url);
        resetSelectedColors(leaseMenuBox);
    }

    public void loadImageView() {
        try {
            // loading the resources for the application
            File file = null;
            file = new File("");
            String filePath = file.getAbsoluteFile().toString();
            String basePath = filePath + "/src/main/resources/com/example/fxproject";

            String homePath = basePath + "/home.png";
            String notificationPath = basePath + "/notification.png";
            String tenantPath = basePath + "/users.png";
            String leasePath = basePath + "/lease.png";

            FileInputStream inputStream = new FileInputStream(homePath);
            FileInputStream notificationStream = new FileInputStream(notificationPath);
            FileInputStream tenantStream = new FileInputStream(tenantPath);
            FileInputStream leaseStream = new FileInputStream(leasePath);

            Image homeImage = new Image(inputStream);
            Image notificationImage = new Image(notificationStream);
            Image tenantsImage = new Image(tenantStream);
            Image leaseImage = new Image(leaseStream);
            Thread.sleep(1000);
            // needs to be changed
            homeImageView.setImage(homeImage);
            tenantsImageView.setImage(tenantsImage);
            notificationImageView.setImage(notificationImage);
            leaseImageView.setImage(leaseImage);
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
        }
    }
}
