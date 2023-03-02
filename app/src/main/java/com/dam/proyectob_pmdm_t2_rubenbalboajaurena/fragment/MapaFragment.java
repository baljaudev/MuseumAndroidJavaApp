package com.dam.proyectob_pmdm_t2_rubenbalboajaurena.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.MainActivity;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.R;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data.MuseumRes;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.util.APIRestServicesMuseum;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.util.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Retrofit;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;
    Retrofit r = RetrofitClient.getClient(APIRestServicesMuseum.BASE_URL);
    APIRestServicesMuseum ars = r.create(APIRestServicesMuseum.class);
    String distrito = "";

    public static Fragment newInstance(String distrito) {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.ARG_DISTRITO, distrito);
        fragment.setArguments(args);
        return fragment;
    }

    public MapaFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);

        mMapView = rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (getArguments() != null) {
            distrito = getArguments().getString(MainActivity.ARG_DISTRITO);
        }

        if (networkAvailable()) {
            if (distrito.equals("Todos") || distrito.equals("")) {
                consultarMuseos();
            } else {
                consultarMuseosDistrito();
            }
        } else {
            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_LONG).show();
        }

    }

    private void consultarMuseosDistrito() {
        Call<MuseumRes> call = ars.obtenerMuseosDistrito(APIRestServicesMuseum.CLAVE_KEY, distrito);
        call.enqueue(new retrofit2.Callback<MuseumRes>() {
            @Override
            public void onResponse(Call<MuseumRes> call, retrofit2.Response<MuseumRes> response) {
                if (response.isSuccessful()) {
                    MuseumRes museos = response.body();
                    if (museos != null) {
                        cargarEnMapa(museos);
                    } else {
                        Toast.makeText(getActivity(), R.string.no_museos, Toast.LENGTH_LONG).show();
                        LatLng madrid = new LatLng(40.416775, -3.703790);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(madrid, 10));
                    }
                }
            }

            @Override
            public void onFailure(Call<MuseumRes> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void consultarMuseos() {
        Call<MuseumRes> call = ars.obtenerMuseos(APIRestServicesMuseum.CLAVE_KEY);
        call.enqueue(new retrofit2.Callback<MuseumRes>() {
            @Override
            public void onResponse(Call<MuseumRes> call, retrofit2.Response<MuseumRes> response) {
                if (response.isSuccessful()) {
                    MuseumRes museos = response.body();
                    if (museos != null) {
                        cargarEnMapa(museos);
                    }
                }
            }

            @Override
            public void onFailure(Call<MuseumRes> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cargarEnMapa(MuseumRes museos) {
        BitmapDescriptor icono;
        LatLng latLng;
        for (int i = 0; i < museos.getMuseo().size(); i++) {
            latLng = new LatLng(museos.getMuseo().get(i).getLocation().getLatitude(),
                    museos.getMuseo().get(i).getLocation().getLongitude());

            icono = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(museos.getMuseo().get(i).getTitle())
                    .icon(icono));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }
    }


    private boolean networkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }
}