package co.dito.abako.abako.Entities;

import com.google.gson.annotations.SerializedName;

public class Configuraciones {

    @SerializedName("Valor")
    private String Valor;

    @SerializedName("Variable")
    private String Variable;

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public String getVariable() {
        return Variable;
    }

    public void setVariable(String variable) {
        Variable = variable;
    }
}
