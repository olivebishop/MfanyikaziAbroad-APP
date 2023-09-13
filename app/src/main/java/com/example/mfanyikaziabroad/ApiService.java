package com.example.mfanyikaziabroad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface ApiService {
    @POST("signup")
    Call<User> register(@Body User user);

    @POST("login")
    Call<User> login(@Body User user);

//    Get all jobs
    @GET("jobs")
    Call<List<Job>> getAllJobs();

}
