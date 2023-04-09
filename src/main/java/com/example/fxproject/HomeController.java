
package com.example.fxproject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;

public class HomeController {
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

    public void initialize() {
        signalNotify();
    }

    private void signalNotify() {
        synchronized (this) {
            this.notifyAll();
        }
    }

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
                Object controller = null;
                controller = loader.getController();
                System.out.println("Controller is " + controller);
                viewerPane.getChildren().setAll(pane);
                System.out.println("setup the children");
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
        Platform.runLater(() -> {
            loadHomeScene();
            resetSelectedColors(homeMenuBox);
        });
    }

    @FXML
    public void tenantSceneSelection() {
        // Platform.runLater to be running on the UI/Application thread.
        Platform.runLater(() -> {
            URL url = getClass().getResource("Tenants.fxml");
            loadScene(url);
            resetSelectedColors(tenantMenuBox);
        });
    }

    @FXML
    public void notificationSceneSelection() {
        // Platform.runLater to be running on the UI/Application thread.
        Platform.runLater(() -> {
            URL url = getClass().getResource("Notifications.fxml");
            loadScene(url);
            resetSelectedColors(notificationMenuBox);
        });
    }

    @FXML
    public void leaseSceneSelection() {
        Platform.runLater(() -> {
            URL url = getClass().getResource("Lease.fxml");
            loadScene(url);
            resetSelectedColors(leaseMenuBox);
        });
    }

    public void loadImageView() {
        try {
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

            // needs to be changed
            Platform.runLater(() -> {
                homeImageView.setImage(homeImage);
                tenantsImageView.setImage(tenantsImage);
                notificationImageView.setImage(notificationImage);
                leaseImageView.setImage(leaseImage);
            });
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
        }
    }
}
