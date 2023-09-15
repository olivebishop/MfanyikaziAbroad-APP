package com.example.mfanyikaziabroad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ArrayList<Job> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getJobList();
//Retrofit start
//        Retrofit retrofit = ApiClient.getApiClient();
//        ApiService apiService = retrofit.create(ApiService.class);
//
//        Call<List<Job>> call = apiService.getAllJobs();
//        call.enqueue(new Callback<List<Job>>() {
//            @Override
//            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
//               List<Job> jobs = response.body();
//                jobs.forEach(job -> Log.d("Jobs","Title: " + job.getTitle() + "Description" + job.getDescription()));
//            }
//
//            @Override
//            public void onFailure(Call<List<Job>> call, Throwable t) {
//                Log.d("Jobs", String.valueOf(t));
//            }
//        });
//        Retrofit end

    }

    public void getJobList(){
        progressDialog = createProgressDialog(DashboardActivity.this);
        Retrofit retrofit = ApiClient.getApiClient();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Job>> call = apiService.getAllJobs();
        call.enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                progressDialog.dismiss();
                jobList = new ArrayList<>(response.body());
                customAdapter = new CustomAdapter(getApplicationContext(), jobList, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(Job job, int position) {

                        Toast.makeText(getApplicationContext(),""+job.getTitle(), Toast.LENGTH_SHORT).show();

                    }
                });
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                progressDialog.dismiss();
//                DialogHelper.getAlertWithMessage("Error",t.getMessage(), DashboardActivity.this);
                Log.d("onFailure: ", t.toString());
            }
        });
    }

    public ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_layout);
        return dialog;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}