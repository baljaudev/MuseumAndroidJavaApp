package com.dam.proyectob_pmdm_t2_rubenbalboajaurena.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dam.proyectob_pmdm_t2_rubenbalboajaurena.R;

public class FiltroDialog extends DialogFragment {

    Spinner spnDistritos;

    OnFiltroListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_filtro, null);

        spnDistritos = view.findViewById(R.id.spnDistritos);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(R.string.dialog_titulo)
                .setPositiveButton(R.string.btnAceptar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onFiltroListener(spnDistritos.getSelectedItem().toString());
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(R.string.btnCancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFiltroListener) {
            listener = (OnFiltroListener) context;
        } else {
            throw new RuntimeException(context + " debe implementar la interfaz: 'OnFiltroListener'");
        }
    }

    @Override
    public void onDetach() {
        if (listener != null) {
            listener = null;
        }
        super.onDetach();
    }

}
