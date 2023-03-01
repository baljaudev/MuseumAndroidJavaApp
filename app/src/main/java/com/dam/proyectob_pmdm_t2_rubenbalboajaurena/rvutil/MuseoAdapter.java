package com.dam.proyectob_pmdm_t2_rubenbalboajaurena.rvutil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.R;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data.Museo;
import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data.MuseumRes;

import java.util.ArrayList;

public class MuseoAdapter extends RecyclerView.Adapter<MuseoAdapter.MuseoViewHolder> implements View.OnClickListener{

    ArrayList<Museo> listaMuseos;

    private View.OnClickListener listener;

    public MuseoAdapter(ArrayList<Museo> listaMuseos) {
        this.listaMuseos = listaMuseos;
    }

    public void setOnClckListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MuseoAdapter.MuseoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.museo_item, parent, false);
        v.setOnClickListener(this);
        return new MuseoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MuseoAdapter.MuseoViewHolder holder, int position) {
        holder.bindMuseo(listaMuseos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaMuseos.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class MuseoViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreMuseo;

        public MuseoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreMuseo = itemView.findViewById(R.id.tvNombreMuseo);
        }

        public void bindMuseo(Museo museo) {
            tvNombreMuseo.setText(museo.getTitle());
        }
    }
}
