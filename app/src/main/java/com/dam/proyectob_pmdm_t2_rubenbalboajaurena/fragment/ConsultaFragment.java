package com.dam.proyectob_pmdm_t2_rubenbalboajaurena.fragment;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.DetalleActivity;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.MainActivity;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.R;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data.Museo;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data.MuseoParce;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data.MuseumRes;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.rvutil.MuseoAdapter;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.util.APIRestServicesMuseum;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.util.RetrofitClient;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ConsultaFragment extends Fragment implements View.OnClickListener{

    String distrito = "";
    public static final String CLAVE_URL = "URL";

    Retrofit r = RetrofitClient.getClient(APIRestServicesMuseum.BASE_URL);
    APIRestServicesMuseum ars = r.create(APIRestServicesMuseum.class);

    MuseoAdapter adapter;
    RecyclerView rvMuseos;

    RecyclerView.LayoutManager lm;

    List<Museo> museos = new ArrayList<>();

    public ConsultaFragment() {}

    public static Fragment newInstance(String distrito) {
        ConsultaFragment fragment = new ConsultaFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.ARG_DISTRITO, distrito);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            distrito = getArguments().getString(MainActivity.ARG_DISTRITO);
        }

        if (isNetworkAvailable()) {
            if (distrito.equals("") || distrito.equals(MainActivity.TODOS_DIST)) {
                consultarAPITodosMuseos();
            } else {
                consultarAPIDistrito();
            }
        } else {
            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_LONG).show();
        }

    }

    private void consultarAPIDistrito() {
        Call<MuseumRes> call = ars.obtenerMuseosDistrito(APIRestServicesMuseum.CLAVE_KEY, distrito);
        call.enqueue(new Callback<MuseumRes>() {
            @Override
            public void onResponse(Call<MuseumRes> call, retrofit2.Response<MuseumRes> response) {
                if (response.isSuccessful()) {
                    MuseumRes museumRes = response.body();
                    cargarDatos(museumRes);
                } else {
                    Log.e("ERROR", "Error en la respuesta.");
                }
            }

            @Override
            public void onFailure(Call<MuseumRes> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    private void consultarAPITodosMuseos() {
        Call<MuseumRes> call = ars.obtenerMuseos(APIRestServicesMuseum.CLAVE_KEY);
        call.enqueue(new Callback<MuseumRes>() {
            @Override
            public void onResponse(Call<MuseumRes> call, retrofit2.Response<MuseumRes> response) {
                if (response.isSuccessful()) {
                    MuseumRes museumRes = response.body();
                    cargarDatos(museumRes);
                } else {
                    Log.e("ERROR", "Error en la respuesta.");
                }
            }

            @Override
            public void onFailure(Call<MuseumRes> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cargarDatos(MuseumRes museumRes) {
        lm = new LinearLayoutManager(getActivity());
        museos = museumRes.getMuseo();

        adapter = new MuseoAdapter((ArrayList<Museo>) museos);
        adapter.setOnClckListener(this);

        rvMuseos.setHasFixedSize(true);
        rvMuseos.setLayoutManager(lm);
        rvMuseos.setAdapter(adapter);

        if (museos.size() == 0) {
            Toast.makeText(getActivity(), R.string.no_museos, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulta, container, false);
        rvMuseos = view.findViewById(R.id.rvMuseos);
        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onClick(View v) {
        int pos = rvMuseos.getChildAdapterPosition(v);
        Museo museo = museos.get(pos);
        String url = museo.getIdURL();
        String id = url.substring(url.lastIndexOf("/") + 1);

        MuseoParce museoParce = new MuseoParce(id);
        Intent intent = new Intent(getActivity(), DetalleActivity.class);
        intent.putExtra(CLAVE_URL, museoParce);
        startActivity(intent);
    }
}