package co.dito.abako.abako.Entities;


import com.google.gson.annotations.SerializedName;

public class UsuarioRequest {

    @SerializedName("IdGoogle")
    private String IdGoogle;

    @SerializedName("IdAgencia")
    private String IdAgencia;

    @SerializedName("Usuario")
    private String Usuario;

    @SerializedName("Password")
    private String Password;

    @SerializedName("ClaveNotificacion")
    private String ClaveNotificacion;

    @SerializedName("Fecha")
    private String Fecha;

    public String getIdGoogle() {
        return IdGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        IdGoogle = idGoogle;
    }

    public String getIdAgencia() {
        return IdAgencia;
    }

    public void setIdAgencia(String idAgencia) {
        IdAgencia = idAgencia;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getClaveNotificacion() {
        return ClaveNotificacion;
    }

    public void setClaveNotificacion(String claveNotificacion) {
        ClaveNotificacion = claveNotificacion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

}
