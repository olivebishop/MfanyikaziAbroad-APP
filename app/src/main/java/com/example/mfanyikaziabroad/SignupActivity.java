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

public class SignupActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText password;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        signupBtn = findViewById(R.id.signup_btn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty() && email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                    Toast.makeText(SignupActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                signupUser(username.getText().toString(), email.getText().toString(), password.getText().toString());
            }
        });
    }

    public  void login(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void signupUser(String username, String email, String password){
        Retrofit retrofit = ApiClient.getApiClient();
        ApiService apiService = retrofit.create(ApiService.class);

        User user = new User(username, email, password);

        Call<User> call = apiService.register(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(SignupActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
//                User responseFromAPI = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignupActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error", t.toString());
            }
        });
    }
}