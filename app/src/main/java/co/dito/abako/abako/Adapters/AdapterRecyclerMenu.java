package co.dito.abako.abako.Adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import co.dito.abako.abako.Activities.Accounts;
import co.dito.abako.abako.Entities.EntMenu;
import co.dito.abako.abako.Entities.MenuInterface;
import co.dito.abako.abako.R;

import static co.dito.abako.abako.Entities.EntMenu.getEntMenuStatic;


public class AdapterRecyclerMenu extends RecyclerView.Adapter<MenuInterface> {

    private Context context;
    private ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    private ImageLoadingListener listener;

    public AdapterRecyclerMenu(Context context) {
        super();
        this.context = context;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        imageLoader1 = ImageLoader.getInstance();
        imageLoader1.init(config);

        //Setup options for ImageLoader so it will handle caching for us.
        options1 = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .build();
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

        EntMenu items = getEntMenuStatic().get(position);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Accounts.class));
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
