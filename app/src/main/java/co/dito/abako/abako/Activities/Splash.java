package co.dito.abako.abako.Activities;


import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import co.dito.abako.abako.DataBase.DBHelper;
import co.dito.abako.abako.R;


public class Splash extends AwesomeSplashCustom {

    @Override
    public void initSplash(ConfigSplash configSplash) {

        /* you don't have to override every property */

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorBlanco); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1500); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.logo_dito); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.DropOut); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title
        configSplash.setTitleSplash("Abako");
        configSplash.setTitleTextColor(R.color.colorPrimary);
        configSplash.setTitleTextSize(41f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.Tada);
        configSplash.setTitleFont("fonts/Roboto-Bold.ttf"); //provide string to your font located in assets/fonts/

    }

    @Override
    public void animationsFinished() {

        DBHelper mydb = new DBHelper(Splash.this);
        mydb.insertIntro("Inicio_sesion");

        startActivity(new Intent(Splash.this, ActLoginNegocio.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();

    }

}
