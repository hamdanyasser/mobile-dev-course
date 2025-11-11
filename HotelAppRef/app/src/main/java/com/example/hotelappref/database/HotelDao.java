package com.example.hotelappref.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hotelappref.models.Hotel;

import java.util.List;

/**
 * Data Access Object (DAO) for Hotel entities.
 *
 * WHY DAO? The DAO pattern abstracts database operations and provides a clean API
 * for accessing data. Room generates the implementation at compile time.
 *
 * @Dao annotation tells Room to generate database access code for these methods.
 * Methods can be synchronous (return values) or asynchronous (LiveData/Flow).
 */
@Dao
public interface HotelDao {

    /**
     * Insert a new hotel into the database.
     * @param hotel The hotel to insert
     * @return The row ID of the inserted hotel
     *
     * WHY @Insert? Room generates efficient INSERT statements automatically.
     * No SQL required!
     */
    @Insert
    long insert(Hotel hotel);

    /**
     * Update an existing hotel in the database.
     * @param hotel The hotel to update (must have valid ID)
     *
     * WHY @Update? Room matches by primary key and updates all fields.
     */
    @Update
    void update(Hotel hotel);

    /**
     * Delete a hotel from the database.
     * @param hotel The hotel to delete (must have valid ID)
     *
     * WHY @Delete? Room handles the DELETE operation based on primary key.
     */
    @Delete
    void delete(Hotel hotel);

    /**
     * Get all hotels from the database.
     * @return List of all hotels
     *
     * WHY @Query? For SELECT operations, we write SQL but Room verifies it at compile time.
     * This prevents runtime SQL errors!
     */
    @Query("SELECT * FROM hotels ORDER BY name ASC")
    List<Hotel> getAllHotels();

    /**
     * Get a specific hotel by ID.
     * @param hotelId The hotel's unique identifier
     * @return The hotel with the given ID, or null if not found
     */
    @Query("SELECT * FROM hotels WHERE id = :hotelId")
    Hotel getHotelById(long hotelId);

    /**
     * Search hotels by name (case-insensitive).
     * @param searchQuery The search term
     * @return List of hotels matching the search
     *
     * WHY LIKE? Enables partial text matching for search functionality.
     * The % wildcards match any characters before/after the search term.
     */
    @Query("SELECT * FROM hotels WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    List<Hotel> searchHotelsByName(String searchQuery);

    /**
     * Delete all hotels from the database.
     *
     * WHY? Useful for testing or resetting the app's data.
     */
    @Query("DELETE FROM hotels")
    void deleteAllHotels();

    /**
     * Get the total count of hotels.
     * @return Number of hotels in database
     */
    @Query("SELECT COUNT(*) FROM hotels")
    int getHotelCount();
}
