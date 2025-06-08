package com.example.cineview.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cineview.R;
import com.example.cineview.adapter.ImageSliderAdapter;
import com.example.cineview.adapter.MovieCardAdapter;
import com.example.cineview.adapter.TopRatingAdapter;
import com.example.cineview.api.ApiClient;
import com.example.cineview.api.ApiService;
import com.example.cineview.design.GridSpacingItemDecoration;
import com.example.cineview.models.MovieItem;
import com.example.cineview.models.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ConstraintLayout homeLayout;
    private ApiService apiService;

    private int loadingCount = 0; // Counter untuk track loading API

    private void showLoading() {
        loadingCount++;
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        loadingCount--;
        if (loadingCount <= 0) {
            loadingCount = 0;
            progressBar.setVisibility(View.GONE);

            // Tampilkan layout utama setelah semua loading selesai
            if (homeLayout.getVisibility() != View.VISIBLE) {
                homeLayout.setAlpha(0f);
                homeLayout.setVisibility(View.VISIBLE);
                homeLayout.animate().alpha(1f).setDuration(300).start(); // animasi fade-in
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        homeLayout = view.findViewById(R.id.home);
        progressBar.setVisibility(View.VISIBLE);
        homeLayout.setVisibility(View.GONE); // sembunyikan tampilan utama saat awal

        TextView usernameTextView = view.findViewById(R.id.username);
        fetchUserData(usernameTextView);

        // Setup Image Slider
        ViewPager2 viewPager2 = view.findViewById(R.id.imageSlider);
        List<MovieItem> imageSliderMovies = new ArrayList<>();
        ImageSliderAdapter sliderAdapter = new ImageSliderAdapter(getContext(), imageSliderMovies);
        viewPager2.setAdapter(sliderAdapter);

        apiService = ApiClient.getRetrofit().create(ApiService.class);

        // Load slider movies
        showLoading();
        Call<List<MovieItem>> sliderCall = apiService.getAllMovies();
        sliderCall.enqueue(new Callback<List<MovieItem>>() {
            @Override
            public void onResponse(Call<List<MovieItem>> call, Response<List<MovieItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieItem> moviesFromApi = response.body();
                    List<MovieItem> top3 = moviesFromApi.subList(0, Math.min(3, moviesFromApi.size()));
                    imageSliderMovies.clear();
                    imageSliderMovies.addAll(top3);
                    sliderAdapter.notifyDataSetChanged();

                    if (imageSliderMovies.size() > 1) {
                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                int totalItem = sliderAdapter.getItemCount();
                                if (totalItem == 0) return;

                                int currentItem = viewPager2.getCurrentItem();
                                int nextItem = (currentItem + 1) % totalItem;
                                viewPager2.setCurrentItem(nextItem, true);
                                handler.postDelayed(this, 5000);
                            }
                        };
                        handler.postDelayed(runnable, 5000);
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load slider movies", Toast.LENGTH_SHORT).show();
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<List<MovieItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load slider movies", Toast.LENGTH_SHORT).show();
                hideLoading();
            }
        });

        // Setup top rated recycler
        recyclerView = view.findViewById(R.id.topRatingRecycler);
        List<MovieItem> topRatedList = new ArrayList<>();
        TopRatingAdapter topRatingAdapter = new TopRatingAdapter(requireContext(), topRatedList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(topRatingAdapter);

        // Load top rated movies
        showLoading();
        apiService.getTopRatedMovies().enqueue(new Callback<List<MovieItem>>() {
            @Override
            public void onResponse(Call<List<MovieItem>> call, Response<List<MovieItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    topRatedList.clear();
                    topRatedList.addAll(response.body());
                    topRatingAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load top rated movies", Toast.LENGTH_SHORT).show();
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<List<MovieItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load top rated movies", Toast.LENGTH_SHORT).show();
                Log.e("TopRatedError", "onFailure: Gagal memuat daftar top-rated film.", t);
                hideLoading();
            }
        });

        // Setup recommended recycler
        RecyclerView recommendedRecycler = view.findViewById(R.id.recommendedRecycler);
        List<MovieItem> recommendedList = new ArrayList<>();
        MovieCardAdapter recommendedAdapter = new MovieCardAdapter(getContext(), recommendedList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recommendedRecycler.setLayoutManager(gridLayoutManager);
        recommendedRecycler.addItemDecoration(new GridSpacingItemDecoration(2, 24, true));
        recommendedRecycler.setAdapter(recommendedAdapter);

        // Load recommended movies
        showLoading();
        Call<List<MovieItem>> recommendedCall = apiService.getAllMovies();
        recommendedCall.enqueue(new Callback<List<MovieItem>>() {
            @Override
            public void onResponse(Call<List<MovieItem>> call, Response<List<MovieItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recommendedList.clear();
                    recommendedList.addAll(response.body());
                    recommendedAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load movies", Toast.LENGTH_SHORT).show();
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<List<MovieItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load movies", Toast.LENGTH_SHORT).show();
                Log.e("MovieApiError", "onFailure: Gagal memuat daftar film.", t);
                hideLoading();
            }
        });
    }

    private void fetchUserData(TextView usernameTextView) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String authToken = sharedPref.getString("auth_token", null);

        if (authToken == null) {
            Log.e("Auth", "Token tidak ditemukan");
            usernameTextView.setText("Tamu");
            return;
        }

        apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<UserModel> call = apiService.getUserProfile("Bearer " + authToken);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    UserModel user = response.body();
                    usernameTextView.setText(user.getUsername());
                } else {
                    Log.e("Auth", "Gagal" + response.code());
                    usernameTextView.setText("Gagal memuat");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("Auth", "Network Error: " + t);
                usernameTextView.setText("Kesalahan jaringan");
            }
        });
    }
}
