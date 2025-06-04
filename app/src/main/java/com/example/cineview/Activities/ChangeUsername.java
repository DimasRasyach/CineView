package com.example.cineview.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cineview.R;

public class ChangeUsername extends AppCompatActivity {

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

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        Button updateButton = findViewById(R.id.ButtonUpdate);

        updateButton.setOnClickListener(v -> {
            String newUsername = usernameEditText.getText().toString().trim();

            if (newUsername.isEmpty()) {
                Toast.makeText(ChangeUsername.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                // Lakukan aksi simpan atau update username di sini
                Toast.makeText(ChangeUsername.this, "Username berhasil diperbarui", Toast.LENGTH_SHORT).show();

                // Misal kembali ke halaman sebelumnya:
                finish();
            }
        });

    }
}