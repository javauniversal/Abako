package co.dito.abako.abako.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponce {

    @SerializedName("CodigoNegocio")
    private String CodigoNegocio;

    @SerializedName("ListAgencia")
    private List<ListAgencia> ListAgencia;

    @SerializedName("ListIp")
    private List<ListIp> ListIp;

    @SerializedName("Negocio")
    private String Negocio;

    @SerializedName("ResulSer")
    private Boolean ResulSer;

    @SerializedName("UrlImg")
    private String UrlImg;

    public static LoginResponce loginResponceStatic;

    public static LoginResponce getLoginResponceStatic() {
        return loginResponceStatic;
    }

    public static void setLoginResponceStatic(LoginResponce loginResponceStatic) {
        LoginResponce.loginResponceStatic = loginResponceStatic;
    }

    public String getCodigoNegocio() {
        return CodigoNegocio;
    }

    public void setCodigoNegocio(String codigoNegocio) {
        CodigoNegocio = codigoNegocio;
    }

    public String getNegocio() {
        return Negocio;
    }

    public void setNegocio(String negocio) {
        Negocio = negocio;
    }

    public Boolean getResulSer() {
        return ResulSer;
    }

    public void setResulSer(Boolean resulSer) {
        ResulSer = resulSer;
    }

    public String getUrlImg() {
        return UrlImg;
    }

    public void setUrlImg(String urlImg) {
        UrlImg = urlImg;
    }

    public List<co.dito.abako.abako.Entities.ListAgencia> getListAgencia() {
        return ListAgencia;
    }

    public void setListAgencia(List<co.dito.abako.abako.Entities.ListAgencia> listAgencia) {
        ListAgencia = listAgencia;
    }

    public List<co.dito.abako.abako.Entities.ListIp> getListIp() {
        return ListIp;
    }

    public void setListIp(List<co.dito.abako.abako.Entities.ListIp> listIp) {
        ListIp = listIp;
    }

}
