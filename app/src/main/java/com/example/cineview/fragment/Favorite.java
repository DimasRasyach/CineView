package com.example.cineview.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineview.R;
import com.example.cineview.adapter.MovieAdapter;
import com.example.cineview.api.ApiClient;
import com.example.cineview.api.ApiService;
import com.example.cineview.models.ApiResponse;
import com.example.cineview.models.FavoriteMoviesResponse;
import com.example.cineview.models.MovieIdRequest;
import com.example.cineview.models.MovieItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorite extends Fragment implements MovieAdapter.OnFavoriteClickListener {

    private RecyclerView recyclerView;
    private EditText searchEditText;
    private MovieAdapter movieAdapter;
    private List<MovieItem> movieList;
    private List<MovieItem> filteredList;
    private ApiService apiService;
    private String authToken;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        Button btnFilter = view.findViewById(R.id.btnFilter);
        Button btnSort = view.findViewById(R.id.btnSort);
        searchEditText = view.findViewById(R.id.searchEditText);
        recyclerView = view.findViewById(R.id.favoResultRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        movieList = new ArrayList<>();
        filteredList = new ArrayList<>();

        movieAdapter = new MovieAdapter(requireContext(), filteredList);
        movieAdapter.setOnFavoriteClickListener(this); // pasang listener di adapter
        recyclerView.setAdapter(movieAdapter);

        apiService = ApiClient.getRetrofit().create(ApiService.class);
        SharedPreferences prefs = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        authToken = "Bearer " + prefs.getString("auth_token", null);
        userId = prefs.getString("user_id", null);

        if (authToken != null && userId != null) {
            loadFavoriteMovies();
        }

        btnFilter.setOnClickListener(v -> showFilterPopup(btnFilter));
        btnSort.setOnClickListener(v -> showSortPopup(btnSort));

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void showSortPopup(View anchorView) {
        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_sort, null);

        PopupWindow popupWindow = new PopupWindow(popupView, anchorView.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);

        TextView sortAz = popupView.findViewById(R.id.sortAz);
        TextView sortZa = popupView.findViewById(R.id.sortZa);

        sortAz.setOnClickListener(v -> {
            Collections.sort(filteredList, (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
            movieAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
        });

        sortZa.setOnClickListener(v -> {
            Collections.sort(filteredList, (a, b) -> b.getTitle().compareToIgnoreCase(a.getTitle()));
            movieAdapter.notifyDataSetChanged();
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
            boolean isVisible = content.getVisibility() == View.VISIBLE;
            content.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            arrow.setRotation(isVisible ? 0 : 180);
        });
    }

    private void filterMovies(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(movieList);
        } else {
            for (MovieItem movie : movieList) {
                if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(movie);
                }
            }
        }
        movieAdapter.notifyDataSetChanged();
    }

    private void loadFavoriteMovies() {
        apiService.getFavorites(authToken, userId).enqueue(new Callback<FavoriteMoviesResponse>() {
            @Override
            public void onResponse(Call<FavoriteMoviesResponse> call, Response<FavoriteMoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieList.clear();
                    movieList.addAll(response.body().getData()); // Ambil dari field "data"
                    filterMovies(searchEditText.getText().toString());
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat favorit", Toast.LENGTH_SHORT).show();
                    Log.e("TAG_ERROR", "Gagal Memuat Favorit: kode = " + response.code() + ", pesan = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<FavoriteMoviesResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG_ERROR", "Terjadi kesalahan", t);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (authToken != null && userId != null) {
            loadFavoriteMovies();
        }
    }


    // Implementasi interface dari adapter
    @Override
    public void onAddFavoriteClicked(String movieId) {
        if (authToken == null || userId == null) {
            Toast.makeText(requireContext(), "User tidak terautentikasi", Toast.LENGTH_SHORT).show();
            return;
        }
        MovieIdRequest request = new MovieIdRequest(movieId);
        apiService.addFavoriteMovie(authToken, userId, request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Ditambahkan ke favorit!", Toast.LENGTH_SHORT).show();
                    loadFavoriteMovies(); // reload favorite agar update UI
                } else {
                    Toast.makeText(requireContext(), "Gagal menambahkan favorit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
