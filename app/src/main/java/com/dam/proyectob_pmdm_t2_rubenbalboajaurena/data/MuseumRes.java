
package com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MuseumRes {

    @SerializedName("@context")
    @Expose
    private Context context;
    @SerializedName("@graph")
    @Expose
    private List<Museo> museo;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Museo> getMuseo() {
        return museo;
    }

    public void setMuseo(List<Museo> museo) {
        this.museo = museo;
    }

}
