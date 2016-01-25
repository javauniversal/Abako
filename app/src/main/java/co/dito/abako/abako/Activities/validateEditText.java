package co.dito.abako.abako.Activities;


import android.support.v7.app.AppCompatActivity;

public class validateEditText extends AppCompatActivity {

    public Boolean validate(String data){
        return data == null || data.length() == 0;
    }
}
