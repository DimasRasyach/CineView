package com.example.cineview.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cineview.R;
import com.example.cineview.api.ApiClient;
import com.example.cineview.api.ApiService;
import com.example.cineview.models.UserModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUsername extends AppCompatActivity {
    private ApiService apiService;
    private String authHeader;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_username);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ChangeUsername), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Ambil token dan userId dari SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_pref", MODE_PRIVATE);
        String token = prefs.getString("auth_token", null);
        userId = prefs.getString("user_id", null);

        // Inisialisasi Retrofit client dengan interceptor auth token
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        Button updateButton = findViewById(R.id.ButtonUpdate);

        updateButton.setOnClickListener(v -> {
            String newUsername = usernameEditText.getText().toString().trim();

            if (newUsername.isEmpty()) {
                Toast.makeText(ChangeUsername.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            authHeader = "Bearer " + token;

            // Siapkan body request
            Map<String, String> body = new HashMap<>();
            body.put("username", newUsername);

            // Panggil API update username
            Call<UserModel> call = apiService.updateUsername(authHeader, userId, body);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ChangeUsername.this, "Username berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        finish(); // Kembali ke halaman sebelumnya
                    } else {
                        Toast.makeText(ChangeUsername.this, "Gagal update username: " + response.message(), Toast.LENGTH_SHORT).show();
                        Log.e("ChangeUsername", "Update failed: " + response.code() + " - " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(ChangeUsername.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}