package com.example.cineview.Activities;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

public class ChangePassword extends AppCompatActivity {

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

            // Kalau valid, lakukan aksi update di sini
            Toast.makeText(ChangePassword.this, "Password berhasil diperbarui", Toast.LENGTH_SHORT).show();

            // Contoh: kembali ke halaman sebelumnya atau lanjut ke aksi lainnya
            finish(); // Menutup halaman ini
        });
    }
}