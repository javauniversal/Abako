package co.dito.abako.abako.Activities;

import android.content.Intent;
import android.os.Bundle;

import co.dito.abako.abako.Fragments.FragmentIndex;
import co.dito.abako.abako.R;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;


public class Accounts extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle savedInstanceState) {

        // create sections
        setDrawerHeaderImage(R.drawable.side_nav_bar);
        setUsername("Abako");
        setUserEmail("Soluciones");
        setFirstAccountPhoto(getResources().getDrawable(R.drawable.cartera));

        // create sections
        this.addSection(newSection("Menú 1", R.drawable.icono5, new FragmentIndex()));
        //this.addSection(newSection("Mis Pedidos", R.drawable.icono5,new Intent(this, ActEstadoPedido.class)));
        this.addSection(newSection("Menú 2", R.drawable.icono5, new FragmentIndex()));
        //this.addSection(newSection("Mi Ubicación", R.drawable.icono5, new Intent(this, ActMaps.class)));
        this.addSection(newSection("Carrito", R.drawable.icono5, new FragmentIndex()));

        // create bottom section
        //this.addBottomSection(newSection("Salir",R.drawable.ic_exit_to_app_black_24dp,new Intent(this,Settings.class)));

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(Accounts.this, ActMenu.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();

        super.onBackPressed();  // optional depending on your needs

    }


}
