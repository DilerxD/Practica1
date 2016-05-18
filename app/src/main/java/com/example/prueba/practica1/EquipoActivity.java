package com.example.prueba.practica1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prueba.practica1.APIService.Pichangers;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipoActivity extends AppCompatActivity {

    @BindView(R.id.txtEquipoNombre)
    TextView txtEquipoNombre;
    @BindView(R.id.txtGanados)
    TextView txtGanados;
    @BindView(R.id.txtPerdidos)
    TextView txtPerdidos;
    @BindView(R.id.imagen)
    ImageView equipoFoto;

    int idEquipo = -1;

    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Informacion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(!Pichangers.initialized) {
            Pichangers.initialize();
        }
        Bundle extras = getIntent().getExtras();
        idEquipo = extras.getInt("idEquipo");
        if(idEquipo == -1){
            finish();
        }

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Cargando");
        pDialog.setCancelable(false);

        pDialog.show();
        Pichangers.service.equipo(idEquipo).enqueue(new Callback<Pichangers.Equipo>() {
            @Override
            public void onResponse(Call<Pichangers.Equipo> call, Response<Pichangers.Equipo> response) {
                pDialog.hide();
                txtGanados.setText("Partidos Ganados: "+response.body().getPartidosGanados());
                txtPerdidos.setText("Partidos Ganados: "+response.body().getPartidosPerdidos());
                txtEquipoNombre.setText("Partidos Ganados: "+response.body().getNombre());
                Picasso.with(EquipoActivity.this).load(response.body().getUrlImagen()).into(equipoFoto);
            }

            @Override
            public void onFailure(Call<Pichangers.Equipo> call, Throwable t) {
                Log.e("Equipo",t.getMessage());
                pDialog.hide();
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                super. onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
