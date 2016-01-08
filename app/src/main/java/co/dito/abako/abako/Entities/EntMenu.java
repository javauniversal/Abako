package co.dito.abako.abako.Entities;


import java.util.List;

public class EntMenu {

    public int imageView;
    public String modulo;
    public String descripcion;
    public String presio;
    public String usuario;
    public String vistoBueno;
    public static List<EntMenu> entMenuStatic;

    public EntMenu(int imageView, String modulo, String descripcion, String usuario, String vistoBueno, String presio) {
        this.imageView = imageView;
        this.modulo = modulo;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.vistoBueno = vistoBueno;
        this.presio = presio;
    }


    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPresio() {
        return presio;
    }

    public void setPresio(String presio) {
        this.presio = presio;
    }

    public String getVistoBueno() {
        return vistoBueno;
    }

    public void setVistoBueno(String vistoBueno) {
        this.vistoBueno = vistoBueno;
    }

    public static List<EntMenu> getEntMenuStatic() {
        return entMenuStatic;
    }

    public static void setEntMenuStatic(List<EntMenu> entMenuStatic) {
        EntMenu.entMenuStatic = entMenuStatic;
    }

}
