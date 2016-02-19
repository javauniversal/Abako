package co.dito.abako.abako.Entities;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseEmpleado {

    @SerializedName("IdPersona")
    private int IdPersona;

    @SerializedName("Nombre")
    private String Nombre;

    @SerializedName("Agencias")
    private List<Agencia> listAgencias;

    @SerializedName("Almacenes")
    private List<Almacenes> almacenesList;

    @SerializedName("Configuraciones")
    private List<Configuraciones> configuracionesList;

    @SerializedName("Mensajes")
    private List<Mensajes> mensajesList;

    //@SerializedName("Permisos")
    //private Permisos permisos;

    public int getIdPersona() {
        return IdPersona;
    }

    public void setIdPersona(int idPersona) {
        IdPersona = idPersona;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public List<Agencia> getListAgencias() {
        return listAgencias;
    }

    public void setListAgencias(List<Agencia> listAgencias) {
        this.listAgencias = listAgencias;
    }

    public List<Almacenes> getAlmacenesList() {
        return almacenesList;
    }

    public void setAlmacenesList(List<Almacenes> almacenesList) {
        this.almacenesList = almacenesList;
    }

    public List<Configuraciones> getConfiguracionesList() {
        return configuracionesList;
    }

    public void setConfiguracionesList(List<Configuraciones> configuracionesList) {
        this.configuracionesList = configuracionesList;
    }

    public List<Mensajes> getMensajesList() {
        return mensajesList;
    }

    public void setMensajesList(List<Mensajes> mensajesList) {
        this.mensajesList = mensajesList;
    }

}
