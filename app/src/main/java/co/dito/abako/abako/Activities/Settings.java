package co.dito.abako.abako.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.settings);

        startActivity(new Intent(Settings.this, ActMenu.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();


    }

}
