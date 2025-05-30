package com.example.cineview.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cineview.R;

public class Account extends Fragment {

    private TextView tvUsername, tvEmail, tvPassword;
    private TextView tvChangeUsername, tvChangePassword, tvChangeEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Inisialisasi view
        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPassword = view.findViewById(R.id.tvPassword);

        tvChangeUsername = view.findViewById(R.id.tvChangeUsername);
        tvChangePassword = view.findViewById(R.id.tvChangePassword);
        tvChangeEmail = view.findViewById(R.id.tvChangeEmail);

        // Set teks dari user login (contoh data dummy)
        tvUsername.setText("John Doe");
        tvEmail.setText("john@example.com");
        tvPassword.setText("BLONDE");

        // Tambahkan listener untuk ubah username, password, email
        tvChangeUsername.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Change Username clicked", Toast.LENGTH_SHORT).show();
            // Tambahkan aksi ganti username di sini (bisa tampilkan dialog / pindah fragment)
        });

        tvChangePassword.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Change Password clicked", Toast.LENGTH_SHORT).show();
            // Tambahkan aksi ganti password
        });

        tvChangeEmail.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Change Email clicked", Toast.LENGTH_SHORT).show();
            // Tambahkan aksi ganti email
        });

        return view;
    }
}
