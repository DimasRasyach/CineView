package com.example.cineview.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ChangePassword extends AppCompatActivity {
    private ApiService apiService;
    private String authHeader;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ChangePassword), (v, insets) -> {
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

        EditText passwordEditText = findViewById(R.id.passwordEditText);
        ImageView togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility);

        togglePasswordVisibility.setOnClickListener(v -> {
            if (passwordEditText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                togglePasswordVisibility.setImageResource(R.drawable.eyeopen);
            } else {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                togglePasswordVisibility.setImageResource(R.drawable.eyeclose);
            }
            passwordEditText.setSelection(passwordEditText.length());
        });

        EditText confirmPasswordEditText = findViewById(R.id.confirmpasswordEditText);
        ImageView toggleConfirmPasswordVisibility = findViewById(R.id.toggleConfirmPasswordVisibility);

        toggleConfirmPasswordVisibility.setOnClickListener(v -> {
            if (confirmPasswordEditText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                confirmPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                toggleConfirmPasswordVisibility.setImageResource(R.drawable.eyeopen);
            } else {
                confirmPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                toggleConfirmPasswordVisibility.setImageResource(R.drawable.eyeclose);
            }
            confirmPasswordEditText.setSelection(confirmPasswordEditText.length());
        });

        Button buttonUpdate = findViewById(R.id.ButtonUpdate);

        buttonUpdate.setOnClickListener(v -> {
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (password.isEmpty()) {
                passwordEditText.setError("Password tidak boleh kosong");
                passwordEditText.requestFocus();
                return;
            }

            if (confirmPassword.isEmpty()) {
                confirmPasswordEditText.setError("Konfirmasi password tidak boleh kosong");
                confirmPasswordEditText.requestFocus();
                return;
            }

            if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Password tidak sama");
                confirmPasswordEditText.requestFocus();
                return;
            }

            authHeader = "Bearer " + token;

            // Body request
            Map<String, String> body = new HashMap<>();
            body.put("password", password);

            // Panggil API
            Call<UserModel> call = apiService.updatePassword(authHeader, userId, body);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ChangePassword.this, "Password berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        finish();
                } else {
                        Toast.makeText(ChangePassword.this, "Gagal update password: " + response.message(), Toast.LENGTH_SHORT).show();
                        Log.e("ChangePassword", "Update failed: " + response.code() + " - " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(ChangePassword.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ChangePassword", "onFailure: " + t.getMessage(), t);
                }
            });
        });
    }
}