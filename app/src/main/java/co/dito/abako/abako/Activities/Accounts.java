package co.dito.abako.abako.Activities;

import android.content.Intent;
import android.os.Bundle;

import co.dito.abako.abako.Fragments.FragmentInformacion;
import co.dito.abako.abako.Fragments.FragmentInicio;
import co.dito.abako.abako.Fragments.FragmentRecibos;
import co.dito.abako.abako.Fragments.FragmentSincroniacion;
import co.dito.abako.abako.R;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;


public class Accounts extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle savedInstanceState) {

        // create sections
        setDrawerHeaderImage(R.drawable.side_nav_bar);
        setUsername("Abako");
        setUserEmail("Soluciones");
        setFirstAccountPhoto(getResources().getDrawable(R.drawable.logo_abako));

        // create sections
        this.addSection(newSection("Inicio", R.drawable.ic_home_black_36dp, new FragmentInicio()));
        this.addSection(newSection("Recibos", R.drawable.ic_border_color_black_36dp, new FragmentRecibos()));
        this.addSection(newSection("Sincronizar", R.drawable.ic_swap_vertical_circle_black_36dp, new FragmentSincroniacion()));
        //this.addSection(newSection("Mi Ubicación", R.drawable.icono5, new Intent(this, ActMaps.class)));
        this.addSection(newSection("Información", R.drawable.ic_record_voice_over_black_36dp, new FragmentInformacion()));

        // create bottom section
        this.addBottomSection(newSection("Salir",R.drawable.ic_exit_to_app_black_24dp,new Intent(this, Settings.class)));

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(Accounts.this, ActMenu.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();

        super.onBackPressed();  // optional depending on your needs

    }


}
