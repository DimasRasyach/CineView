package com.example.cineview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cineview.Activities.SignIn;
import com.example.cineview.R;

public class ProfileOptionsFragment extends Fragment {

    public ProfileOptionsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_profile_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        View menuAccount = view.findViewById(R.id.menuAccount);
        View menuSupport = view.findViewById(R.id.menuSupport);
        View menuLogout = view.findViewById(R.id.menuLogout);

        menuAccount.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.optionsContainer, new Account())
                    .addToBackStack(null)
                    .commit();
        });

        menuLogout.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_logout, null);

            final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(getContext())
                    .setView(dialogView)
                    .setCancelable(false)
                    .create();

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            View btnLogoutConfirm = dialogView.findViewById(R.id.btnLogoutConfirm);
            View btnLogoutCancel = dialogView.findViewById(R.id.btnLogoutCancel);

            btnLogoutConfirm.setOnClickListener(view1 -> {
                Toast.makeText(getContext(), "Logging out", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), SignIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            });

            btnLogoutCancel.setOnClickListener(view1 -> dialog.dismiss());

            dialog.show();
        });
    }
}
