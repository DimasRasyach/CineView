package com.example.cineview.fragment;

import android.content.Intent;
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

        tvUsername.setText("John Doe");
        tvPassword.setText("BLONDE");

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
}
