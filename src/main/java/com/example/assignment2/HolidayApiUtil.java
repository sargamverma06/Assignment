package com.example.assignment2;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HolidayApiUtil {

    // Method to fetch data from the API
    public static String fetchDataFromAPI(String apiUrl) throws IOException {
        // Create a URL object and open a connection
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request method and timeout
        connection.setRequestMethod("GET");
        connection.setReadTimeout(5000);

        // Get the response code
        int responseCode = connection.getResponseCode();

        // Check if the response code is OK (200)
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response from the API
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                // Read each line of the response
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                return response.toString();
            }
        } else {
            // Throw an exception if the response code is not OK
            throw new IOException("Error in API request. Response code: " + responseCode);
        }
    }

    // Method to parse JSON and get holiday data for a specific date
    public static HolidayData parseJson(String json, ZonedDateTime selectedDate) {
        JSONObject jsonObject = new JSONObject(json);

        // Get the array of holidays from the JSON response
        JSONArray holidaysArray = jsonObject.getJSONObject("response").getJSONArray("holidays");

        for (int i = 0; i < holidaysArray.length(); i++) {
            JSONObject holiday = holidaysArray.getJSONObject(i);

            // Get the ISO date of the holiday
            String holidayIsoDate = holiday.getJSONObject("date").optString("iso");

            // Parse the ISO date to LocalDate
            LocalDate holidayDate = parseDate(holidayIsoDate);

            // Create ZonedDateTime for the holiday date
            ZonedDateTime holidayDateTime = holidayDate.atStartOfDay(selectedDate.getZone());

            // Check if the holiday date matches the selected date
            if (holidayDateTime.equals(selectedDate)) {
                // Get holiday details
                String name = holiday.optString("name", "No Name");
                String description = holiday.optString("description", "");
                String location = holiday.optString("location", "Unknown");
                String type = holiday.optString("type", "Unknown");

                // Return HolidayData object
                return new HolidayData(name, description, location, type);
            }
        }

        // No matching holiday found
        return new HolidayData("No holiday found", "", "", "");
    }

    // Method to parse JSON and get a list of holidays for a specific month
    public static List<HolidayData> parseMonthHolidays(String json, LocalDate selectedDate) {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray holidaysArray = jsonObject.getJSONObject("response").getJSONArray("holidays");

        List<HolidayData> monthHolidays = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;

        for (int i = 0; i < holidaysArray.length(); i++) {
            JSONObject holiday = holidaysArray.getJSONObject(i);

            // Get the ISO date of the holiday
            String holidayIsoDate = holiday.getJSONObject("date").optString("iso");

            try {
                // Parse the ISO date to LocalDate
                LocalDate holidayDate = LocalDate.parse(holidayIsoDate, dateFormatter);

                // Check if the holiday is in the selected month
                if (holidayDate.getMonth() == selectedDate.getMonth()) {
                    // Get holiday details
                    String location = holiday.optString("location", "Unknown");
                    String type = holiday.optString("type", "Unknown");
                    String name = holiday.optString("name", "No Name");
                    String description = holiday.optString("description", "");

                    // Add HolidayData object to the list
                    monthHolidays.add(new HolidayData(name, description, location, type));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return monthHolidays;
    }

    // Private method to parse date using multiple formatters
    private static LocalDate parseDate(String date) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ofPattern("MM-dd-yyyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                // Try parsing the date with each formatter
                return LocalDate.parse(date, formatter);
            } catch (Exception ignored) {
                // Ignore exceptions and try the next formatter
            }
        }

        // Return a default value if parsing fails
        return LocalDate.MIN;
    }
}
