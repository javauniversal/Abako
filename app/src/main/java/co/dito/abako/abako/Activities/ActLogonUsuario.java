package co.dito.abako.abako.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.dito.abako.abako.Entities.EntMenu;
import co.dito.abako.abako.R;

import static co.dito.abako.abako.Entities.EntMenu.setEntMenuStatic;

public class ActLogonUsuario extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @InjectView(R.id.btnIngresar)
    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logon_usuario);
        ButterKnife.inject(this);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Llamar servicio de usuario
                List<EntMenu> menuList = new ArrayList<EntMenu>();

                menuList.add(new EntMenu("http://zonaapp.co/images/icono1.png","Pedidos","Crea tus órdenes de pedidos","40", "10", "$ 100.000"));
                menuList.add(new EntMenu("http://zonaapp.co/images/icono2.png","Facturación","Genera factura de venta","50", "8", "$ 200.000"));
                menuList.add(new EntMenu("http://zonaapp.co/images/icono3.png","Notas Credito","Tramita tu devolución","70", "14", "$ 200.000"));
                menuList.add(new EntMenu("http://zonaapp.co/images/icono4.png","Cartera","Gestion de cuentas por cobrar","10", "19", "$ 200.000"));
                menuList.add(new EntMenu("http://zonaapp.co/images/icono5.png","Entregas","Controla tus pedidos","30","10", "$ 400.000"));
                menuList.add(new EntMenu("http://zonaapp.co/images/icono6.png","Proveedores","Administrar tu cliente","78","11", "$ 100.000"));

                setEntMenuStatic(menuList);

                startActivity(new Intent(ActLogonUsuario.this, ActMenu.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

            }
        });
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
