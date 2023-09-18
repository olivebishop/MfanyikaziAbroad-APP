package com.example.mfanyikaziabroad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApplyActivity extends AppCompatActivity {
    private EditText name, email, phone ;
    private Button submitBtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty() && email.getText().toString().isEmpty() && phone.getText().toString().isEmpty()){
                    Toast.makeText(ApplyActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                apply(name.getText().toString(), email.getText().toString(), phone.getText().toString());
            }
        });
    }

    private void apply(String name, String email, String phone){
        Retrofit retrofit = ApiClient.getApiClient();
        ApiService apiService = retrofit.create(ApiService.class);

        Application application = new Application(name, email, phone);

        Call<Application> call = apiService.submitApplication(application);

        call.enqueue(new Callback<Application>() {
            @Override
            public void onResponse(Call<Application> call, Response<Application> response) {
                Toast.makeText(ApplyActivity.this, "Submitted Succesfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Application> call, Throwable t) {
                Toast.makeText(ApplyActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error", t.toString());
            }
        });
    }
}