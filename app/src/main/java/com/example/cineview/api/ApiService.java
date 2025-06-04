package com.example.cineview.api;

import com.example.cineview.models.ApiResponse;
import com.example.cineview.models.LoginRequest;
import com.example.cineview.models.RegisterRequest;
import com.example.cineview.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<ApiResponse> loginUser(@Body LoginRequest request);

    @GET("api/users/{id}")
    Call<UserModel> getUserById(@Path("id") String userId);

    @GET("api/users/profile")
    Call<UserModel> getUserProfile(@Header("Authorization") String authToken);
}
