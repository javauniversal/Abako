package co.dito.abako.abako.Adapters;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import co.dito.abako.abako.Activities.Accounts;
import co.dito.abako.abako.Entities.EntMenu;
import co.dito.abako.abako.Entities.MenuInterface;
import co.dito.abako.abako.R;

import static co.dito.abako.abako.Entities.EntMenu.getEntMenuStatic;


public class AdapterRecyclerMenu extends RecyclerView.Adapter<MenuInterface> {

    private Activity context;

    public AdapterRecyclerMenu(Activity context) {
        super();
        this.context = context;

    }

    @Override
    public MenuInterface onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_menu, parent, false);
        return new MenuInterface(v, context);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MenuInterface holder, final int position) {

        final EntMenu items = getEntMenuStatic().get(position);
        holder.imageView.setImageResource(items.getImageView());
        holder.modulo.setText(items.getModulo());
        holder.descripcion.setText(items.getDescripcion());
        holder.usuario.setText(items.getUsuario());
        holder.vistobueno.setText(items.getVistoBueno());

        holder.presio.setText(items.getPresio());

        holder.item_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.item_menu:
                        PopupMenu popup = new PopupMenu(context, v);
                        popup.getMenuInflater().inflate(R.menu.clipboard_popup, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.install:
                                        //Or Some other code you want to put here.. This is just an example.
                                        Toast.makeText(context, " Install Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();
                                        break;

                                    case R.id.addtowishlist:
                                        Toast.makeText(context, "Add to Wish List Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();
                                        break;

                                    default:
                                        break;
                                }

                                return true;
                            }
                        });

                        break;

                }
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (items.getModulo()){
                    case "Pedidos":
                        Toast.makeText(context, "En construcción", Toast.LENGTH_LONG).show();
                        break;

                    case "Facturación":
                        Toast.makeText(context, "En construcción", Toast.LENGTH_LONG).show();
                        break;

                    case "Notas Credito":
                        Toast.makeText(context, "En construcción", Toast.LENGTH_LONG).show();
                        break;

                    case "Cartera":
                        context.startActivity(new Intent(context, Accounts.class));
                        context.finish();
                        break;

                    case "Entregas":
                        Toast.makeText(context, "En construcción", Toast.LENGTH_LONG).show();
                        break;

                    case "Proveedores":
                        Toast.makeText(context, "En construcción", Toast.LENGTH_LONG).show();
                        break;
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        if (getEntMenuStatic() == null) {
            return 0;
        } else {
            return getEntMenuStatic().size();
        }
    }

}
