package com.example.cineview.fragment;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cineview.R;
import com.example.cineview.adapter.MovieAdapter;
import com.example.cineview.api.ApiClient;
import com.example.cineview.api.ApiService;
import com.example.cineview.models.MovieItem;
import com.example.cineview.models.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends Fragment {

    private ProgressBar searchLoading;

    private RecyclerView recyclerView;
    private EditText searchEditText;
    private TextView textResultCount;

    private MovieAdapter movieAdapter;
    private List<MovieItem> movieList;
    private List<MovieItem> filteredList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchLoading = view.findViewById(R.id.searchLoading);

        Button btnFilter = view.findViewById(R.id.btnFilter);
        Button btnSort = view.findViewById(R.id.btnSort);

        searchEditText = view.findViewById(R.id.searchEditText);
        recyclerView = view.findViewById(R.id.searchResultRecyclerView);
        textResultCount = view.findViewById(R.id.textResultCount);

        btnFilter.setOnClickListener(v -> showFilterPopup(btnFilter));
        btnSort.setOnClickListener(v -> showSortPopup(btnSort));

        // Inisialisasi RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        movieList = new ArrayList<>();
        filteredList = new ArrayList<>();

        movieAdapter = new MovieAdapter(requireContext(), filteredList);
        recyclerView.setAdapter(movieAdapter);


        updateResultCount();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s.toString());
            }
            @Override public void afterTextChanged(Editable s) { }
        });

        recyclerView.setVisibility(View.GONE);
        textResultCount.setVisibility(View.GONE);
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
            Collections.sort(filteredList, (movie1, movie2) -> movie1.getTitle().compareToIgnoreCase(movie2.getTitle()));
            movieAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
        });

        sortZa.setOnClickListener(v -> {
            Collections.sort(filteredList, (movie1, movie2) -> movie2.getTitle().compareToIgnoreCase(movie1.getTitle()));
            movieAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
        });

        popupWindow.showAsDropDown(anchorView, 0, 8);
    }

    private void showFilterPopup(View anchorView) {
        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_filter, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setElevation(20f);

        setupExpandableSection(popupView, R.id.sectionGenreHeader, R.id.chipGroupGenre, R.id.arrowGenre);
        setupExpandableSection(popupView, R.id.sectionDateHeader, R.id.layoutDatePicker, R.id.arrowDate);
        setupExpandableSection(popupView, R.id.sectionActorHeader, R.id.layoutActorSearch, R.id.arrowActor);
        setupExpandableSection(popupView, R.id.sectionOrderHeader, R.id.chipGroupOrder, R.id.arrowOrder);

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

    private void setupExpandableSection(View root, int headerId, int contentId, int arrowId) {
        LinearLayout header = root.findViewById(headerId);
        View content = root.findViewById(contentId);
        ImageView arrow = root.findViewById(arrowId);

        header.setOnClickListener(v -> {
            if (content.getVisibility() == View.VISIBLE) {
                content.setVisibility(View.GONE);
                arrow.setRotation(0);
            } else {
                content.setVisibility(View.VISIBLE);
                arrow.setRotation(180);
            }
        });
    }

    private void filterMovies(String query) {
        filteredList.clear();

        if (query.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textResultCount.setVisibility(View.GONE);
            movieAdapter.notifyDataSetChanged();
            return;
        }

        for (MovieItem movie : movieList) {
            if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(movie);
            }
        }

        if (filteredList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textResultCount.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textResultCount.setVisibility(View.VISIBLE);
            textResultCount.setText(filteredList.size() + " results");
        }

        movieAdapter.notifyDataSetChanged();
    }

    private void updateResultCount() {
        textResultCount.setText(filteredList.size() + " results");
    }

    private void fetchAllMovies() {
        searchLoading.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        textResultCount.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        apiService.getAllMovies().enqueue(new Callback<List<MovieItem>>() {
            @Override
            public void onResponse(Call<List<MovieItem>> call, Response<List<MovieItem>> response) {
                searchLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    movieList.clear();
                    movieList.addAll(response.body());

                    filteredList.clear();
                    filteredList.addAll(movieList);

                    movieAdapter.notifyDataSetChanged();

                    recyclerView.setVisibility(View.VISIBLE);
                    textResultCount.setVisibility(View.VISIBLE);
                    updateResultCount();
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat data film", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MovieItem>> call, Throwable t) {
                searchLoading.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Gagal memuat film: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchAllMovies();
    }
}