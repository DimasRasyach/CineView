package com.example.cineview.Activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.cineview.R;
import com.example.cineview.databinding.ActivityMainBinding;
import com.example.cineview.fragment.Favorite;
import com.example.cineview.fragment.Home;
import com.example.cineview.fragment.Profile;
import com.example.cineview.fragment.Search;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fcv_main, new Home())
                    .commit();
        }

        binding.bnvMain.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home_fragment) {
                replaceFragment(new Home());
                return true;
            } else if (itemId == R.id.search_fragment) {
                replaceFragment(new Search());
                return true;
            } else if (itemId == R.id.favorite_fragment) {
                replaceFragment(new Favorite());
                return true;
            } else if (itemId == R.id.profile_fragment) {
                replaceFragment(new Profile());
                return true;
            } else {
                return false;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcv_main, fragment)
                .commit();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}