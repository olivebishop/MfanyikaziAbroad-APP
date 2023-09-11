package com.example.mfanyikaziabroad;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface ApiService {
    @POST("signup")
    Call<User> register(@Body User user);

    @POST("login")
    Call<User> login(@Body User user);
}
