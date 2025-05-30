package com.example.cineview.api;

import com.example.cineview.models.ApiResponse;
import com.example.cineview.models.LoginRequest;
import com.example.cineview.models.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<ApiResponse> loginUser(@Body LoginRequest request);
}
