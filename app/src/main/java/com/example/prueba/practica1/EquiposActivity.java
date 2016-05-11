package com.example.prueba.practica1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.prueba.practica1.APIService.Pichangers;
import com.example.prueba.practica1.APIService.Pichangers.Equipo;

import java.util.ArrayList;
import java.util.List;

public class EquiposActivity extends AppCompatActivity {
    GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);
        grid = (GridView)findViewById(R.id.grid);

        List<Equipo> equipos = new ArrayList<>();
        equipos.add(new Equipo("niupi","lala",1,1,1));
        equipos.add(new Equipo("Chelsie","lala",1,1,1));
        equipos.add(new Equipo("Barsa","lala",1,1,1));
        equipos.add(new Equipo("United","lala",1,1,1));
        equipos.add(new Equipo("Lazeter","lala",1,1,1));

        GridAdapter adapter = new GridAdapter(this,equipos);



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

        public View getView(int position,View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.equipo_item, parent, false);
            TextView equipoNombre = (TextView)view.findViewById(R.id.equipoNombre);
            equipoNombre.setText(equipos.get(position).getNombre());

            return view;
        }
    }
}
