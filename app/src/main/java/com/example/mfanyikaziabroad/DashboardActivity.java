package com.example.mfanyikaziabroad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Retrofit retrofit = ApiClient.getApiClient();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Job>> call = apiService.getAllJobs();
        call.enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
               List<Job> jobs = response.body();
                jobs.forEach(job -> Log.d("Jobs","Title: " + job.getTitle() + "Description" + job.getDescription()));
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Log.d("Jobs", String.valueOf(t));
            }
        });

    }
}