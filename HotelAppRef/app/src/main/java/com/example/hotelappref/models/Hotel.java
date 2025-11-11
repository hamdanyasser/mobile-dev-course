package com.example.hotelappref.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Hotel model class representing a hotel entity with all its properties.
 * Implements Serializable to allow passing between activities via Intent.
 *
 * @Entity annotation marks this as a Room database table.
 * Each field represents a column in the hotels table.
 *
 * WHY Room? Room provides compile-time verification of SQL queries and reduces boilerplate
 * for database operations. It's the recommended persistence library for Android.
 */
@Entity(tableName = "hotels")
public class Hotel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;  // Primary key for database

    private String name;
    private String phone;
    private String website;
    private String location;
    private String nearby;
    private String food;
    private int imageResource;

    /**
     * Constructor to create a Hotel object
     * @param name Hotel name
     * @param phone Phone number
     * @param website Website URL
     * @param location Physical location address
     * @param nearby Nearby attractions
     * @param food Food/dining options
     * @param imageResource Resource ID for hotel image
     */
    public Hotel(String name, String phone, String website, String location,
                 String nearby, String food, int imageResource) {
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.location = location;
        this.nearby = nearby;
        this.food = food;
        this.imageResource = imageResource;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getLocation() {
        return location;
    }

    public String getNearby() {
        return nearby;
    }

    public String getFood() {
        return food;
    }

    public int getImageResource() {
        return imageResource;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNearby(String nearby) {
        this.nearby = nearby;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
