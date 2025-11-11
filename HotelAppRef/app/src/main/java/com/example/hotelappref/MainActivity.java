package com.example.hotelappref;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelappref.adapters.HotelAdapter;
import com.example.hotelappref.database.AppDatabase;
import com.example.hotelappref.database.HotelDao;
import com.example.hotelappref.databinding.ActivityMainBinding;
import com.example.hotelappref.models.Hotel;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity - The main screen displaying the list of hotels.
 *
 * KEY CONCEPTS:
 * - ViewBinding: Type-safe way to access views (no findViewById!)
 * - RecyclerView: Efficient list display with ViewHolder pattern
 * - Room Database: Persistent storage for hotel data
 * - ActivityResultLauncher: Modern way to handle activity results (replaces startActivityForResult)
 *
 * WHY these patterns? They're best practices for modern Android development and what
 * you'll see in real-world apps and the Android midterm exam.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HotelAdapter adapter;
    private List<Hotel> hotelList;

    // Room Database components
    private AppDatabase database;
    private HotelDao hotelDao;

    // Activity Result Launcher for Add Hotel
    private ActivityResultLauncher<Intent> addHotelLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // WHY ViewBinding? Type-safe access to views, eliminates findViewById and NullPointerException
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Hotels in Lebanon");
        }

        // Initialize Room Database
        // WHY? We need persistent storage so data survives app restarts
        database = AppDatabase.getInstance(this);
        hotelDao = database.hotelDao();

        // Populate initial data if database is empty
        AppDatabase.populateInitialData(this);

        // Initialize Activity Result Launcher
        initializeAddHotelLauncher();

        // Setup RecyclerView
        // WHY LinearLayoutManager? Shows items in a vertical list (like ListView but better)
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);  // Performance optimization

        // Load hotels from database
        loadHotelsFromDatabase();

        // Setup adapter
        // WHY adapter? Bridges data (hotelList) with UI (RecyclerView)
        adapter = new HotelAdapter(this, hotelList);
        binding.recyclerView.setAdapter(adapter);

        // Setup FAB (Floating Action Button) click listener
        // WHY FAB? Material Design pattern for primary action (adding hotels)
        binding.fabAddHotel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddHotelActivity.class);
            addHotelLauncher.launch(intent);
        });
    }

    /**
     * Initialize the Activity Result Launcher for handling results from AddHotelActivity.
     *
     * WHY ActivityResultLauncher? Modern replacement for startActivityForResult (deprecated).
     * Provides type-safe contracts and clearer code structure.
     */
    private void initializeAddHotelLauncher() {
        addHotelLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Hotel was added to database by AddHotelActivity
                        // Reload the list from database to show the new hotel
                        loadHotelsFromDatabase();
                        adapter.notifyDataSetChanged();

                        // Scroll to the last (newest) item
                        if (hotelList.size() > 0) {
                            binding.recyclerView.smoothScrollToPosition(hotelList.size() - 1);
                        }
                    }
                }
        );
    }

    /**
     * Load all hotels from the Room database into the hotelList.
     *
     * WHY? Separates data loading from UI setup. Makes code more maintainable.
     * In production, you'd do this on a background thread using ExecutorService or Coroutines.
     */
    private void loadHotelsFromDatabase() {
        // Query database for all hotels
        hotelList = new ArrayList<>(hotelDao.getAllHotels());
    }

    /**
     * Refresh the hotel list from database and update the UI.
     * Call this whenever hotels are added, updated, or deleted.
     *
     * WHY separate method? Enables easy data refresh from anywhere in the activity.
     */
    private void refreshHotelList() {
        loadHotelsFromDatabase();
        adapter.notifyDataSetChanged();
    }
}
