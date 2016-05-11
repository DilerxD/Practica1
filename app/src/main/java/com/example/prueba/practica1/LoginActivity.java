package com.example.prueba.practica1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.prueba.practica1.APIService.Pichangers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Pichangers.initialize();




    }

    public void login(){
        Pichangers.service.login("20122323","123").enqueue(new Callback<Pichangers.Message>() {
            @Override
            public void onResponse(Call<Pichangers.Message> call, Response<Pichangers.Message> response) {

            }

            @Override
            public void onFailure(Call<Pichangers.Message> call, Throwable t) {

            }
        });
    }
}
