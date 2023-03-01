package com.dam.proyectob_pmdm_t2_rubenbalboajaurena.util;

import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data.MuseumRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRestServicesMuseum {

    public static final String CLAVE_KEY = "201132-0-museos.json";

    public static final String BASE_URL = "https://datos.madrid.es/egob/catalogo/";

    @GET("{key}")
    Call<MuseumRes> obtenerMuseos(
            @Path("key") String key);

    @GET("{key}")
    Call<MuseumRes> obtenerMuseosDistrito(
            @Path("key") String key,
            @Query("distrito_nombre") String distrito);


    @GET("tipo/entidadesyorganismos/{museo}")
    Call<MuseumRes> obtenerMuseosTipo(
            @Path("museo") String museo);

}
