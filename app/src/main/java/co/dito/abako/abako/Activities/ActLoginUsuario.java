package co.dito.abako.abako.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.dito.abako.abako.DataBase.DBHelper;
import co.dito.abako.abako.Entities.Empleado;
import co.dito.abako.abako.Entities.ListAgencia;
import co.dito.abako.abako.Entities.LoginResponce;
import co.dito.abako.abako.Entities.ResponseEmpleado;
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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mydb = new DBHelper(this);
        ButterKnife.inject(this);

        Empleado empleado = mydb.recuperarInfoEmpleado();

        if (empleado.getId_empleado() > 0) {
            codeUsu.setText(empleado.getNombre_empleado());
            codeUsu.setEnabled(false);
            passUsu.setFocusable(true);
        }

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(codeUsu.getText().toString().trim())) {
                    codeUsu.setError("Campo requerido");
                    codeUsu.requestFocus();
                } else if (validate(passUsu.getText().toString().trim())) {
                    passUsu.setError("Campo requerido");
                    passUsu.requestFocus();
                } else {
                    switch (mydb.selectAgenciasEmp(Integer.parseInt(idAgencia))) {
                        case "1":
                            // Si esta llena la tabla pero no lo encontro.
                            Toast.makeText(ActLoginUsuario.this, "El usuario no esta registrado a la agencia seleccionada.", Toast.LENGTH_LONG).show();
                            break;
                        case "2":
                            // Encontro el registro.
                            startActivity(new Intent(ActLoginUsuario.this, ActMenu.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                            break;
                        case "3":
                            // No esta llena y no lo encontro.
                            validateUsuario();
                            break;
                    }
                }
            }

        });

        loadeNegocio();

    }

    private void validateUsuario() {

        String url = String.format("%1$s%2$s", URLNegocio, "/LoginUsuario");
        requestQueue = Volley.newRequestQueue(this);

        try {

            String date = (DateFormat.format("dd/MM/yyyy HH:mm", new Date()).toString());

            HashMap<String, Object> postParameters = new HashMap<>();
            postParameters.put("IdGoogle", "");
            postParameters.put("IdAgencia", idAgencia);
            postParameters.put("Usuario", codeUsu.getText().toString().trim());
            postParameters.put("Password", passUsu.getText().toString().trim());
            postParameters.put("ClaveNotificacion", "");
            postParameters.put("Fecha", date);

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
                                Toast.makeText(ActLoginUsuario.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(ActLoginUsuario.this, "Error Servidor", Toast.LENGTH_LONG).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(ActLoginUsuario.this, "Server Error", Toast.LENGTH_LONG).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(ActLoginUsuario.this, "Error de red", Toast.LENGTH_LONG).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(ActLoginUsuario.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                            }
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
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
        ResponseEmpleado login = gson.fromJson(String.valueOf(response), ResponseEmpleado.class);

        switch (login.getMensajesList().get(0).getEst()) {
            case -1:
                // -1 - Error
                Toast.makeText(this, login.getMensajesList().get(0).getMsg(), Toast.LENGTH_LONG).show();
                break;
            case 0:
                // 0 - Advertencia
                Toast.makeText(this, login.getMensajesList().get(0).getMsg(), Toast.LENGTH_LONG).show();

                startActivity(new Intent(ActLoginUsuario.this, ActMenu.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

                break;
            case 1:
                // 1 - OK
                if (mydb.insertEmpleado(login) && mydb.insertAgenciaEmple(login.getListAgencias()) && mydb.insertAlmacenEmple(login.getAlmacenesList())
                        && mydb.insertConfiguracionEmple(login.getConfiguracionesList())) {

                    startActivity(new Intent(ActLoginUsuario.this, ActMenu.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                } else {
                    Toast.makeText(this, "Problemas al guardar en la base de datos.", Toast.LENGTH_LONG).show();
                }

                break;

        }

    }

    private void loadeNegocio() {

        new AsyncTask<String[], Long, Long>() {
            @Override
            protected Long doInBackground(String[]... params) {

                loginRes = mydb.selectNegocios();

                return null;
            }

            protected void onPreExecute() {
            }

            @Override
            public void onProgressUpdate(Long... value) {
            }

            @Override
            protected void onPostExecute(Long result) {

                ArrayAdapter<LoginResponce> dataNegocio = new ArrayAdapter<>(ActLoginUsuario.this, R.layout.item_txt_spinner, loginRes);
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

        ArrayAdapter<ListAgencia> dataAgencia = new ArrayAdapter<>(ActLoginUsuario.this, R.layout.item_txt_spinner, loginRes.get(positionE).getListAgencia());
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
