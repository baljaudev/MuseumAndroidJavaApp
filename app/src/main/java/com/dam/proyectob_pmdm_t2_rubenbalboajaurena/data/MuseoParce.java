package com.dam.proyectob_pmdm_t2_rubenbalboajaurena.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MuseoParce implements Parcelable {

    private String url;

    public MuseoParce(String url) {
        this.url = url;
    }

    protected MuseoParce(Parcel in) {
        url = in.readString();
    }

    public static final Creator<MuseoParce> CREATOR = new Creator<MuseoParce>() {
        @Override
        public MuseoParce createFromParcel(Parcel in) {
            return new MuseoParce(in);
        }

        @Override
        public MuseoParce[] newArray(int size) {
            return new MuseoParce[size];
        }
    };

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(url);
    }
}
