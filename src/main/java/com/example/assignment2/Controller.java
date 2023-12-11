package com.example.assignment2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class Controller {

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label holidayNameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView iconImageView;

    @FXML
    private void initialize() {
        // Load the icon image from the resources folder
        Image iconImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/assignment2/OIP.jpeg")));
        iconImageView.setImage(iconImage);
    }

    @FXML
    private void getMonthHolidays() {
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate != null) {
            try {
                // API Key for accessing the holiday data
                String apiKey = "qEWNVTkwglBqCVze37AHzeP69sXflSYc";
                // API URL for fetching holidays for the selected month
                String apiUrl = "https://calendarific.com/api/v2/holidays?&api_key=" + apiKey + "&country=CA&year=2024";

                System.out.println("API URL: " + apiUrl);

                // Fetch holiday data from the API
                String jsonResponse = HolidayApiUtil.fetchDataFromAPI(apiUrl);
                System.out.println("API Response: " + jsonResponse);

                // Pass the selected date to the parsing method
                List<HolidayData> monthHolidays = HolidayApiUtil.parseMonthHolidays(jsonResponse, selectedDate);

                // Open the new screen with the list of holidays
                openHolidayListScreen(monthHolidays);
            } catch (Exception e) {
                e.printStackTrace();
                holidayNameLabel.setText("Error fetching holiday data: " + e.getMessage());
                descriptionLabel.setText("");
            }
        }
    }

    private void openHolidayListScreen(List<HolidayData> monthHolidays) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("holiday-list-view.fxml"));
            Parent parent = fxmlLoader.load();

            // Get the controller for the new screen
            HolidayListController holidayListController = fxmlLoader.getController();

            // Set the list of holidays in the controller
            holidayListController.setHolidayList(monthHolidays);

            // Create a new stage for the new screen
            Stage stage = new Stage();
            stage.setTitle("Holidays in the Month");
            stage.setScene(new Scene(parent, 625, 625));
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/assignment2/OIP.jpeg"))));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getHolidayInfo() {
        ZonedDateTime selectedDateTime = datePicker.getValue().atStartOfDay(ZoneId.systemDefault());

        if (selectedDateTime != null) {
            try {
                // API Key for accessing the holiday data
                String apiKey = "qEWNVTkwglBqCVze37AHzeP69sXflSYc";
                // API URL for fetching holiday information for the selected date
                String apiUrl = "https://calendarific.com/api/v2/holidays?&api_key=" + apiKey + "&country=CA&year=2024";

                System.out.println("API URL: " + apiUrl);

                // Fetch holiday data from the API
                String jsonResponse = HolidayApiUtil.fetchDataFromAPI(apiUrl);
                System.out.println("API Response: " + jsonResponse);

                // Pass the selected date to the parsing method
                HolidayData holidayData = HolidayApiUtil.parseJson(jsonResponse, selectedDateTime);

                // Update the UI with holiday information
                holidayNameLabel.setText(holidayData.getName());
                descriptionLabel.setText(holidayData.getDescription());
            } catch (Exception e) {
                e.printStackTrace();
                holidayNameLabel.setText("Error fetching holiday data: " + e.getMessage());
                descriptionLabel.setText("");
            }
        }
    }
}
