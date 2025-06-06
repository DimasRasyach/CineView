package com.example.cineview.api;

import com.example.cineview.models.ApiResponse;
import com.example.cineview.models.LoginRequest;
import com.example.cineview.models.MovieItem;
import com.example.cineview.models.RatingData;
import com.example.cineview.models.RegisterRequest;
import com.example.cineview.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("api/auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<ApiResponse> loginUser(@Body LoginRequest request);

    @GET("api/users/profile")
    Call<UserModel> getUserProfile(@Header("Authorization") String authToken);

    @GET("api/movies/favorites")
    Call<List<UserModel>> getFavoriteMovies();

    // Get Movies by list id
    @GET("api/movies") // karena base_url + movies sesuai router backend
    Call<List<MovieItem>> getAllMovies();

    @GET("api/movies/{id}")
    Call<MovieItem> getMovieById(@Path("id") String id);

    @POST("api/movies/{id}/ratings")
    Call<Void> postRating(@Header("Authorization") String authHeader, @Path("id") String movieId, @Body RatingData ratingData);
}
