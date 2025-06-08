package com.example.cineview.api;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    private String authHeader;

    public AuthInterceptor(String token) {
        this.authHeader = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder();
        if (authHeader != null) {
            builder.header("Authorization", authHeader);
        }

        Request request = builder.build();
        return chain.proceed(request);
    }
}
