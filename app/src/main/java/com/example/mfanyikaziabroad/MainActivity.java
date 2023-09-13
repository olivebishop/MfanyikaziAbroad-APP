package com.example.mfanyikaziabroad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Username and password required", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginUser(email.getText().toString(), password.getText().toString());
            }
        });
    }
    public  void signup(View view){
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }

    public  void loginUser(String email, String password){
        Retrofit retrofit = ApiClient.getApiClient();
        ApiService apiService = retrofit.create(ApiService.class);

        User user = new User(email, password);
        Call<User> call = apiService.login(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                Log.d("onResponse", response.toString());
//------------------ Connect to dashboard --------------------------------------------------
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("onFailure", t.toString());
            }
        });
    }
}