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


// ApplicationLauncher is driver class our execution and extends the Application class from JAVA-FX
public class ApplicationLauncher extends Application {

    // overriding the start method for starting the java-fx application
    @Override
    public void start(Stage stage) throws Exception {

        // Using the FXML to laod the components in our application
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent homeWindow = loader.load();

        // Calling associated controller with call imageView and HomeScene
        HomeController controller = loader.getController();
        controller.loadImageView();
        controller.loadHomeScene();

        // creating scene and setting its title and showing that scene
        Scene homeScene = new Scene(homeWindow);
        stage.setScene(homeScene);
        stage.setTitle("Property Management Software");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

        // Creating GUI thread that runs differently from our main thread
        Thread runnerThread = new Thread(new Runnable() {

            // overriding the run method
            @Override
            public void run() {
                // launching the application
                Application.launch(ApplicationLauncher.class);
            }
        });
//        runnerThread.setDaemon(true);
        // setting GUI thread to MAX-Priority and starting that thread
        runnerThread.setPriority(Thread.MAX_PRIORITY);
        runnerThread.start();
    }
}