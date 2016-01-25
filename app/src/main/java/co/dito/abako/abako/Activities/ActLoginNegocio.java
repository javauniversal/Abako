package co.dito.abako.abako.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.dito.abako.abako.DataBase.DBHelper;
import co.dito.abako.abako.Entities.LoginResponce;
import co.dito.abako.abako.R;

public class ActLoginNegocio extends validateEditText {

    @InjectView(R.id.btnConfigurar)
    Button btnConfigurar;
    MaterialEditText negocio;
    MaterialEditText password;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    RequestQueue requestQueue;
    private CoordinatorLayout coordinatorLayout;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actmain);
        ButterKnife.inject(this);

        mydb = new DBHelper(this);

        negocio = (MaterialEditText) findViewById(R.id.codeNegocio);
        password = (MaterialEditText) findViewById(R.id.password);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        btnConfigurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(negocio.getText().toString().trim())){
                    negocio.setError("Campo requerido");
                    negocio.requestFocus();
                }else if (validate(password.getText().toString().trim())) {
                    password.setError("Campo requerido");
                    password.requestFocus();
                }else {
                    enviarPedido();
                }
            }
        });

    }

    //region Logiar Usuario + Respuesta
    public void enviarPedido(){

        String url = String.format("%1$s%2$s", getString(R.string.url_base),"LoginNegocio");
        requestQueue = Volley.newRequestQueue(this);

        try {

            HashMap<String, Object> postParameters = new HashMap<>();
            postParameters.put("Negocio", negocio.getText().toString().trim());
            postParameters.put("Fecha", "");
            postParameters.put("Password", password.getText().toString().trim());
            postParameters.put("App", "ABKMOV");
            String jsonParameters = new Gson().toJson(postParameters);
            JSONObject  jsonRootObject = new JSONObject(jsonParameters);

            JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonRootObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            parceJson(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Toast.makeText(ActLoginNegocio.this, "Error de tiempo de espera",Toast.LENGTH_LONG).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(ActLoginNegocio.this, "Error Servidor",Toast.LENGTH_LONG).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(ActLoginNegocio.this, "Server Error",Toast.LENGTH_LONG).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(ActLoginNegocio.this, "Error de red",Toast.LENGTH_LONG).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(ActLoginNegocio.this, "Error al serializar los datos",Toast.LENGTH_LONG).show();
                            }
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            requestQueue.add(jsArrayRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parceJson(JSONObject response) {
        Gson gson = new Gson();
        LoginResponce login = gson.fromJson(String.valueOf(response), LoginResponce.class);
        if (login.getResulSer()){
            //Validar si ya Existe..
            if(mydb.validarNegocio(login.getCodigoNegocio())){
                Snackbar.make(coordinatorLayout, "El negocio ya esta registrado en la app", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }else {
                if (mydb.insertNegocio(login)){
                    startActivity(new Intent(ActLoginNegocio.this, ActLoginUsuario.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                } else {
                    Snackbar.make(coordinatorLayout, "Problemas al guardar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        }else {
            Snackbar.make(coordinatorLayout, "Negocio/Password incorrectos", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }
    //endregion

    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null)
            requestQueue.cancelAll("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (requestQueue != null)
            requestQueue.cancelAll("");
    }

    //region Retroceder
    @Override
    public void onBackPressed() {

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else { Toast.makeText(getBaseContext(), "Pulse otra vez para salir", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
    //endregion

}
