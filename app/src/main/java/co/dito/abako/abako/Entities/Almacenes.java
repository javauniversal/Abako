package co.dito.abako.abako.Entities;


import com.google.gson.annotations.SerializedName;

public class Almacenes {

    @SerializedName("Descripcion")
    private String Descripcion;

    @SerializedName("Id")
    private int Id;

    @SerializedName("Prd")
    private int Prd;

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getPrd() {
        return Prd;
    }

    public void setPrd(int prd) {
        Prd = prd;
    }
}
