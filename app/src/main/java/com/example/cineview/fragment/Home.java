package com.example.cineview.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
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
import com.example.cineview.models.TopRatingModel;
import com.example.cineview.models.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usernameTextView = view.findViewById(R.id.username);
        fetchUserData(usernameTextView);

        ViewPager2 viewPager2 = view.findViewById(R.id.imageSlider);

        List<Integer> images = Arrays.asList(
                R.drawable.gambar1,
                R.drawable.gambar2,
                R.drawable.gambar3
        );

        ImageSliderAdapter adapter = new ImageSliderAdapter(images);

        viewPager2.setAdapter(adapter);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager2.getCurrentItem();
                int totalItem = adapter.getItemCount();
                int nextItem = (currentItem + 1) % totalItem;
                viewPager2.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);

        recyclerView = view.findViewById(R.id.topRatingRecycler);

        List<TopRatingModel> list = new ArrayList<>();
        list.add(new TopRatingModel(R.drawable.foto, "FILM 1"));
        list.add(new TopRatingModel(R.drawable.foto, "FILM 2"));
        list.add(new TopRatingModel(R.drawable.foto, "FILM 3"));
        list.add(new TopRatingModel(R.drawable.foto, "FILM 4"));
        list.add(new TopRatingModel(R.drawable.foto, "FILM 5"));
        list.add(new TopRatingModel(R.drawable.foto, "FILM 6"));
        list.add(new TopRatingModel(R.drawable.foto, "FILM 7"));


        TopRatingAdapter adapter2 = new TopRatingAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter2);

        RecyclerView trendRecycler = view.findViewById(R.id.recommendedRecycler);
        List<MovieItem> trendMovies = new ArrayList<>();
//        trendMovies.add(new MovieItem(R.drawable.gambar1, "Judul 1", "4.9", "Deskripsi 1"));
//        trendMovies.add(new MovieItem(R.drawable.gambar2, "Judul 2", "4.7", "Deskripsi 2"));
//        trendMovies.add(new MovieItem(R.drawable.gambar1, "Judul 3", "4.6", "Deskripsi 3"));
//        trendMovies.add(new MovieItem(R.drawable.gambar2, "Judul 4", "4.8", "Deskripsi 4"));

        MovieCardAdapter trendAdapter = new MovieCardAdapter(getContext(), trendMovies);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        trendRecycler.setLayoutManager(gridLayoutManager);
        trendRecycler.addItemDecoration(new GridSpacingItemDecoration(2, 24, true));
        trendRecycler.setAdapter(trendAdapter);

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<List<MovieItem>> call = apiService.getAllMovies();
        call.enqueue(new Callback<List<MovieItem>>() {
            @Override
            public void onResponse(Call<List<MovieItem>> call, Response<List<MovieItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    trendMovies.clear();
                    trendMovies.addAll(response.body());
                    trendAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MovieItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load movies", Toast.LENGTH_SHORT).show();
                Log.e("MovieApiError", "onFailure: Gagal memuat daftar film.", t);
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

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
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