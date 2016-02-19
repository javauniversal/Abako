package co.dito.abako.abako.Entities;

import com.google.gson.annotations.SerializedName;

public class Mensajes {

    @SerializedName("Est")
    private int Est;

    @SerializedName("Msg")
    private String Msg;

    public int getEst() {
        return Est;
    }

    public void setEst(int est) {
        Est = est;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
