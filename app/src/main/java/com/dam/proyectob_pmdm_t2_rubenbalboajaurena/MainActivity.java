package com.dam.proyectob_pmdm_t2_rubenbalboajaurena;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.dialog.FiltroDialog;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.dialog.OnFiltroListener;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.fragment.ConsultaFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnFiltroListener {

    public static final String ARG_DISTRITO = "DISTRITO";
    Button btnFiltro;
    TextView tvDistrito;
    Button btnConsultar;
    FragmentTransaction ft;
    String distrito = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFiltro = findViewById(R.id.btnFiltrar);
        tvDistrito = findViewById(R.id.tvDistrito);
        btnConsultar = findViewById(R.id.btnConsultar);

        btnFiltro.setOnClickListener(this);
        btnConsultar.setOnClickListener(this);

        tvDistrito.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        ft = getSupportFragmentManager().beginTransaction();
        tvDistrito.setVisibility(View.VISIBLE);
        if (v.getId() == R.id.btnFiltrar) {
            conseguirDistrito();
        } else if (v.getId() == R.id.btnConsultar) {
            if (btnConsultar.getText().toString().equals(getString(R.string.btn_consultar_mapa))) {
                if (tvDistrito.getText().toString().isEmpty()) {
                    if (getSupportFragmentManager().findFragmentById(R.id.flContenedor) != null) {
                        ft.remove(getSupportFragmentManager().findFragmentById(R.id.flContenedor));
                    }
                    //ft.add(R.id.flContenedor, new MapaFragment());
                } else {
                    //ft.replace(R.id.flContenedor, MapaFragment.newInstance(distrito));
                }
                ft.addToBackStack(null);
                ft.commit();
            } else if (btnConsultar.getText().toString().equals(getString(R.string.btn_consultar))) {
                if (tvDistrito.getText().toString().isEmpty()) {
                    if (getSupportFragmentManager().findFragmentById(R.id.flContenedor) != null) {
                        ft.remove(getSupportFragmentManager().findFragmentById(R.id.flContenedor));
                    }
                    ft.add(R.id.flContenedor, new ConsultaFragment());
                } else {
                    ft.replace(R.id.flContenedor, ConsultaFragment.newInstance(distrito));
                }
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }

    private void conseguirDistrito() {
        if (getSupportFragmentManager().findFragmentById(R.id.flContenedor) != null) {
            limpiarFragmento();
        }
        tvDistrito.setText("");
        FiltroDialog dialog = new FiltroDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "FiltroDialog");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuListado) {
            tvDistrito.setText("");
            btnConsultar.setText(R.string.btn_consultar);
            limpiarFragmento();
        } else if (item.getItemId() == R.id.menuMapa) {
            tvDistrito.setText("");
            btnConsultar.setText(R.string.btn_consultar_mapa);
            limpiarFragmento();
        }
        return super.onOptionsItemSelected(item);
    }

    private void limpiarFragmento() {
        tvDistrito.setText("");
        ft = getSupportFragmentManager().beginTransaction();
        ft.remove(getSupportFragmentManager().findFragmentById(R.id.flContenedor));
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onFiltroListener(String distrito) {
        tvDistrito.setText("Distrito: " + distrito);
        this.distrito = distrito;

    }
}