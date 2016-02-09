package co.dito.abako.abako.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.dito.abako.abako.Adapters.AdapterRecyclerMenu;
import co.dito.abako.abako.Entities.EntMenu;
import co.dito.abako.abako.R;

import static co.dito.abako.abako.Entities.EntMenu.setEntMenuStatic;

public class ActMenu extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recyclerViewTurnos);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        //Llamar servicio de usuario
        List<EntMenu> menuList = new ArrayList<EntMenu>();

        menuList.add(new EntMenu(R.mipmap.ic_pedidos,"Pedidos","Crea tus órdenes de pedidos","40", "10", "$ 100.000"));
        menuList.add(new EntMenu(R.mipmap.ic_facturacion,"Facturación","Genera factura de venta","50", "8", "$ 200.000"));
        menuList.add(new EntMenu(R.mipmap.ic_notas,"Notas Credito","Tramita tu devolución","70", "14", "$ 200.000"));
        menuList.add(new EntMenu(R.mipmap.ic_cartera,"Cartera","Gestion de cuentas por cobrar","10", "19", "$ 200.000"));
        menuList.add(new EntMenu(R.mipmap.ic_entregas, "Entregas","Controla tus pedidos","30","10", "$ 400.000"));
        menuList.add(new EntMenu(R.mipmap.ic_proveedores,"Proveedores","Administrar tu cliente","78","11", "$ 100.000"));

        setEntMenuStatic(menuList);


        adapter = new AdapterRecyclerMenu(ActMenu.this);
        recycler.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else { Toast.makeText(getBaseContext(), "Pulse otra vez para salir", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();

    }

}
