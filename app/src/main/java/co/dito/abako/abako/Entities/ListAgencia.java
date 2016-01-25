package co.dito.abako.abako.Entities;

import com.google.gson.annotations.SerializedName;

public class ListAgencia {

    @SerializedName("Key")
    public String Key;

    @SerializedName("Value")
    public String Value;

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

}
