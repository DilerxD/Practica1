package com.example.prueba.practica1.APIService;

import android.os.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by w3232 on 11/05/2016.
 */
public class Pichangers {

    public static Retrofit retrofit;

    public static PichangersService service;

    public static void initialize(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://pichangers-api.mybluemix.net")
                .build();
        service = retrofit.create(PichangersService.class);
    }

    public interface PichangersService {
        @GET("/alumnos?sin_equipo={flag_equipo}")
        Call<List<Usuario>> alumnosSinEquipo(@Path("flag_equipo") boolean flag_equipo);
        @POST("/login")
        Call<Message> login(@Field("usuario") String usuario,@Field("password") String password);
        @GET("/equipos")
        Call<List<Equipo>> equipos();
        @GET("/equipos/{id}")
        Call<Equipo> equipo(@Path("id") int id);
        @POST("/equipos/{id}/{codigo_alumno}")
        Call<Message> agregarAlumno(@Path("id") int id,@Path("codigo_alumno") String codigo_alumno);
    }

    public static class Usuario{
        String nombre,codigo;
        public Usuario(String nombre, String codigo) {
            this.nombre = nombre;
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }
    }
    public static class Equipo{
        String nombre,urlImagen;
        int id,partidosGanados,partidosPerdidos;
        public Equipo(String nombre, String urlImagen, int id, int partidosGanados, int partidosPerdidos) {
            this.nombre = nombre;
            this.urlImagen = urlImagen;
            this.id = id;
            this.partidosGanados = partidosGanados;
            this.partidosPerdidos = partidosPerdidos;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getUrlImagen() {
            return urlImagen;
        }

        public void setUrlImagen(String urlImagen) {
            this.urlImagen = urlImagen;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPartidosGanados() {
            return partidosGanados;
        }

        public void setPartidosGanados(int partidosGanados) {
            this.partidosGanados = partidosGanados;
        }

        public int getPartidosPerdidos() {
            return partidosPerdidos;
        }

        public void setPartidosPerdidos(int partidosPerdidos) {
            this.partidosPerdidos = partidosPerdidos;
        }
    }

    public static class Message{
        String msg;
        public Message(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
