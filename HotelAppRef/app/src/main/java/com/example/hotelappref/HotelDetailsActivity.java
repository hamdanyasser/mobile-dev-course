package com.example.hotelappref;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hotelappref.databinding.ActivityHotelDetailsBinding;
import com.example.hotelappref.models.Hotel;

public class HotelDetailsActivity extends AppCompatActivity {

    private ActivityHotelDetailsBinding binding;
    private Hotel hotel;
    private static final int CALL_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Hotel Details");
        }

        // Get hotel data from intent
        hotel = (Hotel) getIntent().getSerializableExtra("hotel");

        if (hotel != null) {
            displayHotelDetails();
            setupButtons();
        }
    }

    private void displayHotelDetails() {
        binding.hotelDetailName.setText(hotel.getName());
        binding.hotelDetailImage.setImageResource(hotel.getImageResource());
        binding.phoneText.setText(hotel.getPhone());
        binding.websiteText.setText(hotel.getWebsite());
        binding.locationText.setText(hotel.getLocation());
        binding.nearbyText.setText(hotel.getNearby());
        binding.foodText.setText(hotel.getFood());
    }

    private void setupButtons() {
        // Phone button - Opens dialer with permission check
        binding.phoneButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Request permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        CALL_PERMISSION_REQUEST_CODE);
            } else {
                // Permission already granted, make the call
                makePhoneCall();
            }
        });

        // Website button - Opens browser
        binding.websiteButton.setOnClickListener(v -> {
            String url = hotel.getWebsite();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No browser app found", Toast.LENGTH_SHORT).show();
            }
        });

        // Location button - Opens maps
        binding.locationButton.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(hotel.getLocation()));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Open in browser if Maps not available
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://maps.google.com/?q=" + Uri.encode(hotel.getLocation())));
                
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No maps app found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + hotel.getPhone()));
        
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No phone app found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, make the call
                makePhoneCall();
            } else {
                // Permission denied
                Toast.makeText(this, 
                    "Phone permission is required to make calls", 
                    Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
