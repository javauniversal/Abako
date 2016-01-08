package co.dito.abako.abako.Entities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import co.dito.abako.abako.R;

public class MenuInterface extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public ImageView item_menu;
    public TextView modulo;
    public TextView descripcion;
    public TextView presio;
    public TextView usuario;
    public TextView vistobueno;

    public MenuInterface(View itemView, Context context) {
        super(itemView);

        this.modulo = (TextView) itemView.findViewById(R.id.txtNombreModulo);
        this.descripcion = (TextView) itemView.findViewById(R.id.txtDescripcion);
        this.usuario = (TextView) itemView.findViewById(R.id.txtUsuario);
        this.vistobueno = (TextView) itemView.findViewById(R.id.txtVistoBueno);
        this.presio = (TextView) itemView.findViewById(R.id.txtValor);
        this.imageView = (ImageView) itemView.findViewById(R.id.imgModule);
        this.item_menu = (ImageView) itemView.findViewById(R.id.item_menu);

    }
}
