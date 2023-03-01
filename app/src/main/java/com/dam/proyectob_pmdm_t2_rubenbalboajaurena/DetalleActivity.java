package com.dam.proyectob_pmdm_t2_rubenbalboajaurena;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data.MuseoParce;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data.MuseumRes;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.fragment.ConsultaFragment;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.util.APIRestServicesMuseum;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.util.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetalleActivity extends AppCompatActivity {

    TextView tvNombreMuseo;
    TextView tvDistrito;
    TextView tvArea;
    TextView tvDireccion;
    TextView tvDescripcionMuseo;
    TextView tvHorarioMuseo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        tvNombreMuseo = findViewById(R.id.tvNombreMuseo);
        tvDistrito = findViewById(R.id.tvDistrito);
        tvArea = findViewById(R.id.tvArea);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvDescripcionMuseo = findViewById(R.id.tvDescripcionMuseo);
        tvHorarioMuseo = findViewById(R.id.tvHorarioMuseo);

        MuseoParce museo = getIntent().getParcelableExtra(ConsultaFragment.CLAVE_URL);

        consumirAPI(museo.getUrl());
    }

    private void consumirAPI(String url) {
        Retrofit r = RetrofitClient.getClient(APIRestServicesMuseum.BASE_URL);
        APIRestServicesMuseum ars = r.create(APIRestServicesMuseum.class);
        Call<MuseumRes> call = ars.obtenerMuseosTipo(url);
        call.enqueue(new Callback<MuseumRes>() {
            @Override
            public void onResponse(Call<MuseumRes> call, Response<MuseumRes> response) {
                if (response.isSuccessful()) {
                    MuseumRes museo = response.body();
                    cargarDatos(museo);
                }
            }

            @Override
            public void onFailure(Call<MuseumRes> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private void cargarDatos(MuseumRes museo) {
        tvNombreMuseo.setText(museo.getMuseo().get(0).getTitle());
        String distritoURL = museo.getMuseo().get(0).getAddress().getDistrict().getId();
        String distrito = distritoURL.substring(distritoURL.lastIndexOf("/") + 1);
        tvDistrito.setText(distrito);
        String areaURL = museo.getMuseo().get(0).getAddress().getArea().getId();
        String area = areaURL.substring(areaURL.lastIndexOf("/") + 1);
        tvArea.setText(area);
        String direccion = museo.getMuseo().get(0).getAddress().getStreetAddress();
        String postalCode = museo.getMuseo().get(0).getAddress().getPostalCode();
        String ciudad = museo.getMuseo().get(0).getAddress().getLocality();
        tvDireccion.setText(direccion + ", " + postalCode + "\n" + ciudad);
        tvDescripcionMuseo.setText(museo.getMuseo().get(0).getOrganization().getOrganizationDesc());
        tvHorarioMuseo.setText(museo.getMuseo().get(0).getOrganization().getSchedule());
    }

}