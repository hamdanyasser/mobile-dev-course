package com.example.hotelappref.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelappref.HotelDetailsActivity;
import com.example.hotelappref.databinding.HotelItemBinding;
import com.example.hotelappref.models.Hotel;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private final Context context;
    private final List<Hotel> hotelList;

    public HotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HotelItemBinding binding = HotelItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), 
                parent, 
                false
        );
        return new HotelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.bind(hotel);
    }

    @Override
    public int getItemCount() {
        return hotelList != null ? hotelList.size() : 0;
    }

    class HotelViewHolder extends RecyclerView.ViewHolder {
        private final HotelItemBinding binding;

        public HotelViewHolder(HotelItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Hotel hotel) {
            // Set hotel data
            binding.hotelName.setText(hotel.getName());
            binding.hotelLocation.setText(hotel.getLocation());
            binding.hotelImage.setImageResource(hotel.getImageResource());

            // Set click listener for the entire card
            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(context, HotelDetailsActivity.class);
                intent.putExtra("hotel", hotel);
                context.startActivity(intent);
            });
        }
    }
}
