package com.example.prueba.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.prueba.practica1.APIService.Pichangers;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtUser)
    EditText txtUser;
    @BindView(R.id.txtPass)
    EditText txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if(!Pichangers.initialized) {
            Pichangers.initialize();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(txtUser.getText().toString(),txtPass.getText().toString());
            }
        });




    }

    public void login(String user,String pass){
        //20122323
        //123
        Pichangers.service.login(new Pichangers.Login(user,pass)).enqueue(new Callback<Pichangers.Message>() {
            @Override
            public void onResponse(Call<Pichangers.Message> call, Response<Pichangers.Message> response) {
                if(response.body() != null) {
                    Log.d("Login", response.body().getMsg());
                    if(response.body().getMsg().equals("OK")){
                        Intent i = new Intent(LoginActivity.this,EquiposActivity.class);
                        startActivity(i);
                    }
                }else{
                    Log.e("Login-Error","error "+response.code());
                    Log.e("Login-Error","URL: " + call.request().url().toString());
                }
            }

            @Override
            public void onFailure(Call<Pichangers.Message> call, Throwable t) {
                Log.e("Login - error",t.getMessage());
            }
        });
    }
}
