package com.example.hotelappref.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hotelappref.models.Hotel;

/**
 * Room Database class for the HotelAppRef application.
 *
 * WHY @Database? This annotation tells Room to create a database with the specified
 * entities and version. Room generates all the database creation and migration code.
 *
 * SINGLETON PATTERN: We use the singleton pattern to ensure only one database instance
 * exists throughout the app's lifecycle. This prevents memory leaks and ensures data
 * consistency.
 *
 * VERSION: Start with version 1. Increment this number whenever you change the schema
 * (add/remove tables or columns). You'll also need to provide a Migration object to
 * tell Room how to upgrade the database.
 */
@Database(entities = {Hotel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Provide access to the HotelDao.
     * Room generates the implementation automatically.
     */
    public abstract HotelDao hotelDao();

    // Singleton instance
    private static volatile AppDatabase INSTANCE;

    /**
     * Get the singleton database instance.
     *
     * WHY synchronized? To prevent multiple threads from creating multiple instances
     * simultaneously. This is thread-safe singleton initialization.
     *
     * WHY volatile? Ensures that INSTANCE is always read from main memory, not cached
     * in CPU registers. This guarantees visibility across threads.
     *
     * @param context Application context (not Activity context to prevent memory leaks)
     * @return The singleton AppDatabase instance
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Double-checked locking: check again inside synchronized block
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),  // Use app context to prevent leaks
                            AppDatabase.class,
                            "hotel_database"  // Database file name
                    )
                    // WHY allowMainThreadQueries? For simplicity in this reference app.
                    // PRODUCTION: Remove this! Always do database operations on background threads
                    // using AsyncTask, ExecutorService, or Kotlin Coroutines.
                    .allowMainThreadQueries()
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Populate the database with sample data if empty.
     * Call this when the app starts to ensure there's data to display.
     *
     * WHY? For the midterm, you want the app to show data immediately without
     * requiring the user to add hotels first.
     *
     * @param context Application context
     */
    public static void populateInitialData(Context context) {
        AppDatabase db = getInstance(context);
        HotelDao dao = db.hotelDao();

        // Only populate if database is empty
        if (dao.getHotelCount() == 0) {
            // Add sample hotels (using mipmap resource for images)
            dao.insert(createSampleHotel(
                    "Grand Plaza Hotel",
                    "+961 1 123456",
                    "www.grandplaza.com",
                    "Beirut Downtown, Lebanon",
                    "Beirut Souks, National Museum, Pigeon Rocks",
                    "Lebanese Cuisine, International Buffet, Rooftop Restaurant"
            ));

            dao.insert(createSampleHotel(
                    "Seaside Resort",
                    "+961 1 234567",
                    "www.seasideresort.com",
                    "Jounieh, Lebanon",
                    "Harissa, Jeita Grotto, Casino du Liban",
                    "Mediterranean Restaurant, Seafood Bar, Pool Cafe"
            ));

            dao.insert(createSampleHotel(
                    "Mountain View Hotel",
                    "+961 1 345678",
                    "www.mountainview.com",
                    "Faraya, Lebanon",
                    "Mzaar Ski Resort, Cedars Forest, Qadisha Valley",
                    "Alpine Restaurant, Chalet Bar, Après-ski Lounge"
            ));

            dao.insert(createSampleHotel(
                    "City Center Hotel",
                    "+961 1 456789",
                    "www.citycenter.com",
                    "Hamra, Beirut",
                    "AUB, Hamra Street, Verdun Shopping",
                    "Traditional Lebanese, Coffee Shop, Bar"
            ));

            dao.insert(createSampleHotel(
                    "Luxury Inn",
                    "+961 1 567890",
                    "www.luxuryinn.com",
                    "Raouche, Beirut",
                    "Pigeon Rocks, Corniche, Zaitunay Bay",
                    "Fine Dining, Sushi Bar, Sky Lounge"
            ));

            dao.insert(createSampleHotel(
                    "Beach Resort Hotel",
                    "+961 1 678901",
                    "www.beachresort.com",
                    "Tyre, South Lebanon",
                    "Tyre Ruins, Al-Bass Archaeological Site, Beach",
                    "Seafood Restaurant, Beach Bar, Mediterranean Grill"
            ));
        }
    }

    /**
     * Helper method to create a Hotel object with sample data.
     * WHY? Reduces code duplication when inserting multiple sample hotels.
     */
    private static Hotel createSampleHotel(String name, String phone, String website,
                                           String location, String nearby, String food) {
        Hotel hotel = new Hotel(name, phone, website, location, nearby, food,
                android.R.mipmap.sym_def_app_icon);  // Default Android icon
        return hotel;
    }

    /**
     * Close the database.
     * Only call this when your app is shutting down, not between activities!
     */
    public static void closeDatabase() {
        if (INSTANCE != null) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }
}
