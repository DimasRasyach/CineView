package com.example.cineview.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cineview.Activities.ChangePassword;
import com.example.cineview.Activities.ChangeUsername;
import com.example.cineview.R;
import com.example.cineview.api.ApiClient;
import com.example.cineview.api.ApiService;
import com.example.cineview.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account extends Fragment {

    private TextView tvUsername, tvPassword;
    private TextView tvChangeUsername, tvChangePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvPassword = view.findViewById(R.id.tvPassword);

        tvChangeUsername = view.findViewById(R.id.tvChangeUsername);
        tvChangePassword = view.findViewById(R.id.tvChangePassword);

        fetchUserData();

        tvChangeUsername.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangeUsername.class);
            startActivity(intent);
        });

        tvChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent);
        });

        return view;
    }

    private void fetchUserData() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String authToken = sharedPref.getString("auth_token", null);
        String userId = sharedPref.getString("user_id", null);

        if (authToken == null || userId == null) {
            Toast.makeText(getContext(), "User belum login", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<UserModel> call = apiService.getUserById("Bearer " + authToken, userId);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    UserModel user = response.body();
                    tvUsername.setText(user.getUsername());
                    tvPassword.setText("********"); // untuk alasan keamanan, atau bisa ditampilkan sesuai kebutuhan
                } else {
                    Toast.makeText(getContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
