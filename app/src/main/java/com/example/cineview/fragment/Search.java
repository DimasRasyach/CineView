package com.example.cineview.fragment;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.cineview.R;


public class Search extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Button btnFilter = view.findViewById(R.id.btnFilter);
        Button btnSort = view.findViewById(R.id.btnSort);

        btnFilter.setOnClickListener(v -> showFilterPopup(btnFilter));
        btnSort.setOnClickListener(v -> showSortPopup(btnSort));

        return view;
    }

    private void showSortPopup(View anchorView) {
        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_sort, null);

        int width = anchorView.getWidth();
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);

        TextView sortAz = popupView.findViewById(R.id.sortAz);
        TextView sortZa = popupView.findViewById(R.id.sortZa);

        sortAz.setOnClickListener(v -> {
            popupWindow.dismiss();
        });

        sortZa.setOnClickListener(v -> {
            popupWindow.dismiss();
        });

        popupWindow.showAsDropDown(anchorView, 0, 8);
    }

    private void showFilterPopup(View anchorView) {
        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_filter, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setElevation(20f);


        LinearLayout sectionGenreHeader = popupView.findViewById(R.id.sectionGenreHeader);
        View chipGroupGenre = popupView.findViewById(R.id.chipGroupGenre);
        ImageView arrowGenre = popupView.findViewById(R.id.arrowGenre);

        LinearLayout sectionDateHeader = popupView.findViewById(R.id.sectionDateHeader);
        View layoutDatePicker = popupView.findViewById(R.id.layoutDatePicker);
        ImageView arrowDate = popupView.findViewById(R.id.arrowDate);

        LinearLayout sectionActorHeader = popupView.findViewById(R.id.sectionActorHeader);
        View layoutActorSearch = popupView.findViewById(R.id.layoutActorSearch);
        ImageView arrowActor = popupView.findViewById(R.id.arrowActor);

        LinearLayout sectionOrderHeader = popupView.findViewById(R.id.sectionOrderHeader);
        View chipGroupOrder = popupView.findViewById(R.id.chipGroupOrder);
        ImageView arrowOrder = popupView.findViewById(R.id.arrowOrder);

        sectionGenreHeader.setOnClickListener(v -> toggleSection(chipGroupGenre, arrowGenre));
        sectionDateHeader.setOnClickListener(v -> toggleSection(layoutDatePicker, arrowDate));
        sectionActorHeader.setOnClickListener(v -> toggleSection(layoutActorSearch, arrowActor));
        sectionOrderHeader.setOnClickListener(v -> toggleSection(chipGroupOrder, arrowOrder));

        EditText editReleaseDate = popupView.findViewById(R.id.editReleaseDate);
        editReleaseDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (view, year, month, dayOfMonth) -> {
                        String selectedDate = String.format("%02d/%02d/%02d", dayOfMonth, month + 1, year % 100);
                        editReleaseDate.setText(selectedDate);
                    },
                    2024, 0, 1
            );
            datePickerDialog.show();
        });


        popupWindow.showAsDropDown(anchorView, 0, 16);
    }

    private void toggleSection(View sectionContent, ImageView arrowIcon) {
        if (sectionContent.getVisibility() == View.VISIBLE) {
            sectionContent.setVisibility(View.GONE);
            arrowIcon.setRotation(0);
        } else {
            sectionContent.setVisibility(View.VISIBLE);
            arrowIcon.setRotation(180);
        }
    }

}