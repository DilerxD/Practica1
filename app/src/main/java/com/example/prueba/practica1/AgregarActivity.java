package com.example.prueba.practica1;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prueba.practica1.APIService.Pichangers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarActivity extends AppCompatActivity {

    @BindView(R.id.listaAlumnos)
    ListView listaAlumnos;

    AlumnosAdapter adapter;
    List<Pichangers.Usuario> alumnos = new ArrayList<>();
    int idEquipo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Escoja Integrante");
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

        adapter = new AlumnosAdapter(this,alumnos);
        listaAlumnos.setAdapter(adapter);

        Pichangers.service.alumnosSinEquipo(true).enqueue(new Callback<List<Pichangers.Usuario>>() {
            @Override
            public void onResponse(Call<List<Pichangers.Usuario>> call, Response<List<Pichangers.Usuario>> response) {
                alumnos.clear();
                alumnos.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Pichangers.Usuario>> call, Throwable t) {
                Log.e("Equipos",t.getMessage());
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


    class AlumnosAdapter extends BaseAdapter {
        List<Pichangers.Usuario> alumnos;
        Context context;

        SweetAlertDialog pDialog;
        public AlumnosAdapter(Context context,List<Pichangers.Usuario> list) {
            alumnos = list;
            this.context = context;

            pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Cargando");
            pDialog.setCancelable(false);
        }

        // Total number of things contained within the adapter
        public int getCount() {
            return alumnos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.agregar_item, parent, false);
            TextView txtNombre = (TextView)view.findViewById(R.id.txtNombre);
            TextView txtCodigo = (TextView)view.findViewById(R.id.txtCodigo);

            txtNombre.setText(alumnos.get(position).getNombre());
            txtCodigo.setText(alumnos.get(position).getCodigo());

            ImageView btnAdd = (ImageView)view.findViewById(R.id.btnAdd);


            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pDialog.show();
                    Pichangers.service.agregarAlumno(idEquipo,alumnos.get(position).getCodigo()).enqueue(new Callback<Pichangers.Message>() {
                        @Override
                        public void onResponse(Call<Pichangers.Message> call, Response<Pichangers.Message> response) {

                            pDialog.hide();
                            new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Listo!")
                                    .setContentText(alumnos.get(position).getNombre()+" fue añadido al equipo!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void onFailure(Call<Pichangers.Message> call, Throwable t) {
                            Log.e("AgregandoAlumno",t.getMessage());
                        }
                    });

                }
            });

            return view;
        }
    }
}
