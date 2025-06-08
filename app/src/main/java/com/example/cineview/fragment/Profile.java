package com.example.cineview.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.cineview.Activities.SignIn;
import com.example.cineview.R;
import com.example.cineview.api.ApiClient;
import com.example.cineview.api.ApiService;
import com.example.cineview.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usernameTextView = view.findViewById(R.id.username);
        ProgressBar progressBar = view.findViewById(R.id.loadingProgressBar);
        View profileImage = view.findViewById(R.id.profileImage);
        View optionsContainer = view.findViewById(R.id.optionsContainer);
        View gradientView = view.findViewById(R.id.gradientView);

        // Sembunyikan konten dulu, tampilkan loading
        progressBar.setVisibility(View.VISIBLE);
        profileImage.setVisibility(View.GONE);
        usernameTextView.setVisibility(View.GONE);
        optionsContainer.setVisibility(View.GONE);
        gradientView.setVisibility(View.GONE);

        fetchUserData(usernameTextView, progressBar, profileImage, optionsContainer, gradientView);

        optionsContainer.setTranslationY(300f);
        optionsContainer.setAlpha(0f);
        gradientView.setTranslationY(300f);
        gradientView.setAlpha(0f);

        optionsContainer.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        gradientView.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator())
                .start();


        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.optionsContainer, new ProfileOptionsFragment())
                .commit();
    }

    private void fetchUserData(TextView usernameTextView, ProgressBar progressBar,
                               View profileImage, View optionsContainer, View gradientView) {

        SharedPreferences sharedPref = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String authToken = sharedPref.getString("auth_token", null);

        if (authToken == null) {
            Log.e("Auth", "Token tidak ditemukan");
            usernameTextView.setText("Tamu");
            showContent(progressBar, usernameTextView, profileImage, optionsContainer, gradientView);
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
                    Log.e("Auth", "Gagal: " + response.code());
                    usernameTextView.setText("Gagal memuat");
                }
                showContent(progressBar, usernameTextView, profileImage, optionsContainer, gradientView);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("Auth", "Network Error: " + t);
                usernameTextView.setText("Kesalahan jaringan");
                showContent(progressBar, usernameTextView, profileImage, optionsContainer, gradientView);
            }
        });
    }

    private void showContent(ProgressBar progressBar, TextView usernameTextView,
                             View profileImage, View optionsContainer, View gradientView) {

        progressBar.setVisibility(View.GONE);
        profileImage.setVisibility(View.VISIBLE);
        usernameTextView.setVisibility(View.VISIBLE);
        optionsContainer.setVisibility(View.VISIBLE);
        gradientView.setVisibility(View.VISIBLE);

        optionsContainer.setTranslationY(300f);
        optionsContainer.setAlpha(0f);
        gradientView.setTranslationY(300f);
        gradientView.setAlpha(0f);

        optionsContainer.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        gradientView.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.optionsContainer, new ProfileOptionsFragment())
                .commit();
    }
}