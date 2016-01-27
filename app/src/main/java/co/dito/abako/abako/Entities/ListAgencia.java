package co.dito.abako.abako.Entities;

import com.google.gson.annotations.SerializedName;

public class ListAgencia {

    @SerializedName("Key")
    private String Key;

    @SerializedName("Value")
    private String Value;

    private String idNegocio;

    public String getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(String idNegocio) {
        this.idNegocio = idNegocio;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return Value;
    }
}
