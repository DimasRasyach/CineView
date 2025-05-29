package com.example.cineview.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.example.cineview.R;


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

        View optionsContainer = view.findViewById(R.id.optionsContainer);
        View gradientView = view.findViewById(R.id.gradientView);

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
    }
}