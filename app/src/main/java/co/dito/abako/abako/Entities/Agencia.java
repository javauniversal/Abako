package co.dito.abako.abako.Entities;

import com.google.gson.annotations.SerializedName;

public class Agencia {

    @SerializedName("Id")
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

}
