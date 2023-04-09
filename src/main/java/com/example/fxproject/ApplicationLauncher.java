package com.example.fxproject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
*
* Stage = UIWindow
* Scene = UIView.
* RootNode = Element in the UIView.
* LeafNode = Button, Label, ......
* */

public class ApplicationLauncher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent homeWindow = loader.load();
        Thread controllerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HomeController controller = loader.getController();
                synchronized (ApplicationLauncher.this) {
                    // notify when the controller is ready
                    ApplicationLauncher.this.notify();
                }

                controller.loadImageView();
                controller.loadHomeScene();
            }
        });

        controllerThread.setDaemon(true);

        synchronized (this) {
            // Wait for the controller to be ready before starting it
            this.wait();
        }
        controllerThread.start();

        Scene homeScene = new Scene(homeWindow);
        stage.setScene(homeScene);
        stage.setTitle("Property Management Software");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        Thread runnerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Application.launch(ApplicationLauncher.class);
            }
        });
        runnerThread.setDaemon(true);
        runnerThread.start();
    }
}