package com.example.assignment2;


// Class to represent holiday data
public class HolidayData {
    private String description;
    private String name;
    private String location;
    private String type;

    // Constructor to initialize holiday data
    public HolidayData(String name, String description, String location, String type) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
    }

    // Getter method for holiday name
    public String getName() {
        return name;
    }

    // Getter method for holiday description
    public String getDescription() {
        return description;
    }

    // Getter method for holiday location
    public String getLocation() {
        return location;
    }

    // Setter method for holiday location
    public void setLocation(String location) {
        this.location = location;
    }

    // Getter method for holiday type
    public String getType() {
        return type;
    }

    // Setter method for holiday type
    public void setType(String type) {
        this.type = type;
    }
}
