package com.example.assignment2;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HolidayListController {
    @FXML
    private ImageView iconImageView;
    @FXML
    private ListView<String> holidayListView;

    // Initialize method, automatically called by JavaFX after loading the FXML
    @FXML
    private void initialize() {
        // Load the icon image from the resources folder
        Image iconImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/assignment2/OIP.jpeg")));
        iconImageView.setImage(iconImage);
    }

    // Method to set the holiday list for display
    public void setHolidayList(List<HolidayData> monthHolidays) {
        // Display the list of holidays in the new screen
        ObservableList<String> holidaysInfo = FXCollections.observableArrayList();

        // Populate the observable list with holiday information
        for (HolidayData holiday : monthHolidays) {
            String bulletPoint = "\u2022"; // Unicode character for bullet point
            String holidayInfo = String.format(
                    "%s Name: %s %n Description: %s%n Location: %s%n Type: %s%n",
                    bulletPoint, holiday.getName(), holiday.getDescription(), holiday.getLocation(), holiday.getType()
            );
            holidaysInfo.add(holidayInfo);
        }

        // Set the observable list as the data source for the ListView
        holidayListView.setItems(holidaysInfo);
    }
}
