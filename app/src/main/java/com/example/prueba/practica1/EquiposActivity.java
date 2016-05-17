package com.example.prueba.practica1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prueba.practica1.APIService.Pichangers;
import com.example.prueba.practica1.APIService.Pichangers.Equipo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquiposActivity extends AppCompatActivity {
    @BindView(R.id.grid)
    GridView grid;


    private List<Equipo> equipos = new ArrayList<>();
    GridAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);
        ButterKnife.bind(this);
        if(!Pichangers.initialized) {
            Pichangers.initialize();
        }
        getSupportActionBar().setTitle("Equipos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        adapter = new GridAdapter(this,equipos);
        grid.setAdapter(adapter);
        Pichangers.service.equipos().enqueue(new Callback<List<Equipo>>() {
            @Override
            public void onResponse(Call<List<Equipo>> call, Response<List<Equipo>> response) {
                Log.d("Equipos","Cargo");
                equipos.clear();
                equipos.addAll(response.body());
                Log.d("Equipos","equipos:" +equipos.size());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Equipo>> call, Throwable t) {
                Log.e("Equipos",t.getMessage());
            }
        });





    }



    class GridAdapter extends BaseAdapter {
        List<Pichangers.Equipo> equipos;
        Context context;

        public GridAdapter(Context context,List<Pichangers.Equipo> list) {
            equipos = list;
            this.context = context;
        }

        // Total number of things contained within the adapter
        public int getCount() {
            return equipos.size();
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
            View view = LayoutInflater.from(context).inflate(R.layout.equipo_item, parent, false);
            TextView equipoNombre = (TextView)view.findViewById(R.id.equipoNombre);
            ImageView img = (ImageView) view.findViewById(R.id.imagen);
            equipoNombre.setText(equipos.get(position).getNombre());
            Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(img);

            FrameLayout btnAgregar = (FrameLayout)view.findViewById(R.id.btnAgregar);
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,AgregarActivity.class);
                    i.putExtra("idEquipo",equipos.get(position).getId());
                    startActivity(i);

                }
            });

            return view;
        }
    }
}
