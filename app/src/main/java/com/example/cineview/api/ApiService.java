package com.example.cineview.api;

import com.example.cineview.models.ApiResponse;
import com.example.cineview.models.CommentRequest;
import com.example.cineview.models.CommentResponse;
import com.example.cineview.models.FavoriteMoviesResponse;
import com.example.cineview.models.LoginRequest;
import com.example.cineview.models.MovieIdRequest;
import com.example.cineview.models.MovieItem;
import com.example.cineview.models.RatingData;
import com.example.cineview.models.RatingRequest;
import com.example.cineview.models.RatingResponse;
import com.example.cineview.models.RegisterRequest;
import com.example.cineview.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<ApiResponse> loginUser(@Body LoginRequest request);

    @GET("api/users/profile")
    Call<UserModel> getUserProfile(@Header("Authorization") String authHeader);

    // Get Movies by list id
    @GET("api/movies") // karena base_url + movies sesuai router backend
    Call<List<MovieItem>> getAllMovies();

    @GET("api/movies/{id}")
    Call<MovieItem> getMovieById(
            @Header("Authorization") String authHeader,
            @Path("id") String id
    );

    @POST("api/movies/{id}/ratings")
    Call<Void> postRating(@Header("Authorization") String authHeader, @Path("id") String movieId, @Body RatingRequest ratingRequest);

    // Ambil semua komentar berdasarkan movieId
    @GET("api/movies/{id}/comments")
    Call<CommentResponse> getCommentsByMovieId(@Path("id") String movieId);

    // Kirim komentar (butuh token)
    @POST("api/movies/{id}/comments")
    Call<Void> postComment(
            @Header("Authorization") String authHeader, // "Bearer <your_token>"
            @Path("id") String movieId,
            @Body CommentRequest commentRequest

    );

    // Edit komentar (opsional)
    @PUT("api/movies/{id}/comments/{commentId}")
    Call<Void> updateComment(
            @Path("id") String movieId,
            @Path("commentId") String commentId,
            @Body CommentRequest comment,
            @Header("Authorization") String token
    );

    // Hapus komentar (opsional)
    @DELETE("api/movies/{id}/comments/{commentId}")
    Call<Void> deleteComment(
            @Path("id") String movieId,
            @Path("commentId") String commentId,
            @Header("Authorization") String token
    );

    @GET("api/users/{id}/favorites")
    Call<FavoriteMoviesResponse> getFavorites(
            @Header("Authorization") String authHeader,
            @Path("id") String userId
    );

    @POST("api/users/{id}/favorites")
    Call<ApiResponse> addFavoriteMovie(
            @Header("Authorization") String authHeader,
            @Path("id") String userId,
            @Body MovieIdRequest movieIdRequest
    );

    @DELETE("api/users/{id}/favorites/{movieId}")
    Call<ApiResponse> deleteFavoriteMovie(
            @Header("Authorization") String authHeader,
            @Path("id") String userId,
            @Path("movieId") String movieId
    );

    @GET("api/movies/{movieId}/ratings")
    Call<RatingResponse> getMovieRatings(@Path("movieId") String movieId);
}
