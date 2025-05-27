package com.example.cineview.Activities;

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
import com.example.cineview.models.ApiResponse;
import com.example.cineview.models.RegisterRequest;

import java.io.IOException;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        Button createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUp.this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(SignUp.this, "Password tidak cocok!", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterRequest request = new RegisterRequest(username, email, password);
            ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
            Call<ApiResponse> call = apiService.registerUser(request);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(SignUp.this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                        finish(); // kembali ke login
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API", "Error body: " + errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(SignUp.this, "Registrasi gagal: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(SignUp.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                    Log.e("API", "Network error", t);
                }
            });
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}