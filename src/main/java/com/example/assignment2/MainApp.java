package com.example.assignment2;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    // The entry point of the JavaFX application
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file for the holiday view
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("holiday-view.fxml"));

        // Create a scene using the loaded FXML file
        Scene scene = new Scene(fxmlLoader.load(), 625, 625);

        // Set the application icon
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/assignment2/OIP.jpeg"))));

        // Set the title, scene, and display the stage
        stage.setTitle("Holiday Calendar");
        stage.setScene(scene);
        stage.show();
    }

    // The main method that launches the JavaFX application
    public static void main(String[] args) {
        launch();
    }
}
