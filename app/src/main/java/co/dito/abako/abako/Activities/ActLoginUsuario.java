package co.dito.abako.abako.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
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
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.dito.abako.abako.DataBase.DBHelper;
import co.dito.abako.abako.Entities.ListAgencia;
import co.dito.abako.abako.Entities.LoginResponce;
import co.dito.abako.abako.Entities.UsuarioResponse;
import co.dito.abako.abako.R;

public class ActLoginUsuario extends AvtivityBase {

    @InjectView(R.id.btnIngresar)
    Button btnIngresar;

    @InjectView(R.id.spinnerNegocio)
    Spinner spinnerNegocio;

    @InjectView(R.id.spinnerAgencia)
    Spinner spinnerAgencias;

    @InjectView(R.id.codeUsu)
    MaterialEditText codeUsu;

    @InjectView(R.id.passwordUsu)
    MaterialEditText passUsu;

    private DBHelper mydb;
    private List<LoginResponce> loginRes;
    private String idAgencia;
    private String URLNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mydb = new DBHelper(this);
        ButterKnife.inject(this);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(codeUsu.getText().toString().trim())){
                    codeUsu.setError("Campo requerido");
                    codeUsu.requestFocus();
                }else if (validate(passUsu.getText().toString().trim())) {
                    passUsu.setError("Campo requerido");
                    passUsu.requestFocus();
                }else {
                    validateUsuario();
                }

                /*startActivity(new Intent(ActLoginUsuario.this, ActMenu.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();*/
            }
        });

        loadeNegocio();

    }

    private void validateUsuario() {

        String url = String.format("%1$s%2$s", URLNegocio,"/LoginUsuario");
        requestQueue = Volley.newRequestQueue(this);

        try {

            HashMap<String, Object> postParameters = new HashMap<>();
            postParameters.put("IdGoogle", "");
            postParameters.put("IdAgencia", idAgencia);
            postParameters.put("Usuario", codeUsu.getText().toString().trim());
            postParameters.put("Password", passUsu.getText().toString().trim());
            postParameters.put("ClaveNotificacion", "");
            postParameters.put("Fecha", "01/01/2005");

            String jsonParameters = new Gson().toJson(postParameters);
            JSONObject jsonRootObject = new JSONObject(jsonParameters);

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
                                Toast.makeText(ActLoginUsuario.this, "Error de tiempo de espera",Toast.LENGTH_LONG).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(ActLoginUsuario.this, "Error Servidor",Toast.LENGTH_LONG).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(ActLoginUsuario.this, "Server Error",Toast.LENGTH_LONG).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(ActLoginUsuario.this, "Error de red",Toast.LENGTH_LONG).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(ActLoginUsuario.this, "Error al serializar los datos",Toast.LENGTH_LONG).show();
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
        UsuarioResponse login = gson.fromJson(String.valueOf(response), UsuarioResponse.class);
    }

    private void loadeNegocio() {

        new AsyncTask<String[], Long, Long>(){
            @Override
            protected Long doInBackground(String[]... params) {

                loginRes = mydb.selectNegocios();

                return null;
            }

            protected void onPreExecute() { }

            @Override
            public void onProgressUpdate(Long... value) { }

            @Override
            protected void onPostExecute(Long result){

                ArrayAdapter<LoginResponce> dataNegocio = new ArrayAdapter<LoginResponce>(ActLoginUsuario.this, R.layout.item_txt_spinner, loginRes);
                spinnerNegocio.setAdapter(dataNegocio);
                spinnerNegocio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        URLNegocio = loginRes.get(position).getListIp().get(0).getValue();

                        loadeAgencias(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });
            }

        }.execute();

    }

    private void loadeAgencias(final int positionE) {

        ArrayAdapter<ListAgencia> dataAgencia = new ArrayAdapter<ListAgencia>(ActLoginUsuario.this, R.layout.item_txt_spinner, loginRes.get(positionE).getListAgencia());
        spinnerAgencias.setAdapter(dataAgencia);
        spinnerAgencias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idAgencia = loginRes.get(positionE).getListAgencia().get(position).getKey();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(ActLoginUsuario.this, ActLoginNegocio.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
