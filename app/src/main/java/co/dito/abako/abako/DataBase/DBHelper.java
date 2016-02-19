package co.dito.abako.abako.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.dito.abako.abako.Entities.Agencia;
import co.dito.abako.abako.Entities.Almacenes;
import co.dito.abako.abako.Entities.Configuraciones;
import co.dito.abako.abako.Entities.Empleado;
import co.dito.abako.abako.Entities.ListAgencia;
import co.dito.abako.abako.Entities.ListIp;
import co.dito.abako.abako.Entities.LoginResponce;
import co.dito.abako.abako.Entities.ResponseEmpleado;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlIntro = "CREATE TABLE intro (id integer primary key AUTOINCREMENT, idintro text )";
        String sqlNegocio = "CREATE TABLE negocio (CodigoNegocio text, Negocio text, UrlImg text )";
        String sqlAgencias = "CREATE TABLE agencia (key text, value text, idnegocio int)";
        String sqlIps = "CREATE TABLE ip (key text, value text, idnegocio int)";

        String sqlAgenciaEmple = "CREATE TABLE agencia_emple (Id text)";
        String sqlAlmacenEmple = "CREATE TABLE almacen_emple (descripcion text, id int, prd int)";
        String sqlConfiguracionesEmple = "CREATE TABLE configuracion_emple (valor text, variable text)";
        String sqlEmpleaso = "CREATE TABLE empleado_negocio (nombre_empleado text, id_empleado int)";


        db.execSQL(sqlIntro);
        db.execSQL(sqlNegocio);
        db.execSQL(sqlAgencias);
        db.execSQL(sqlIps);
        db.execSQL(sqlAgenciaEmple);
        db.execSQL(sqlAlmacenEmple);
        db.execSQL(sqlConfiguracionesEmple);
        db.execSQL(sqlEmpleaso);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS intro");
        db.execSQL("DROP TABLE IF EXISTS negocio");
        db.execSQL("DROP TABLE IF EXISTS agencia");
        db.execSQL("DROP TABLE IF EXISTS ip");
        db.execSQL("DROP TABLE IF EXISTS agencia_emple");
        db.execSQL("DROP TABLE IF EXISTS almacen_emple");
        db.execSQL("DROP TABLE IF EXISTS configuracion_emple");
        db.execSQL("DROP TABLE IF EXISTS empleado_negocio");
        this.onCreate(db);
    }

    //region Insertar empleado.
    public boolean insertEmpleado(ResponseEmpleado data){

        DeleteEmpleado();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            values.put("nombre_empleado", data.getNombre());
            values.put("id_empleado", data.getIdPersona());

            db.insert("empleado_negocio", null, values);

        }catch (SQLiteConstraintException e){
            db.close();
            return false;
        }

        db.close();
        return true;
    }
    //endregion

    //region Validar si el empleado existe.
    public boolean validateEmpleado(int data){
        boolean _validate = false;
        String sql_validate = "SELECT * FROM empleado_negocio WHERE id_empleado = "+data;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_validate, null);
        if (cursor.moveToFirst()) {
            _validate = true;
        }

        return _validate;
    }
    //endregion

    //region Validar si el empleado existe.
    public Empleado recuperarInfoEmpleado(){
        String sql_validate = "SELECT * FROM empleado_negocio LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_validate, null);

        Empleado empleado = new Empleado();

        if (cursor.moveToFirst()) {
            empleado.setNombre_empleado(cursor.getString(0));
            empleado.setId_empleado(Integer.parseInt(cursor.getString(1)));
        }

        return empleado;
    }
    //endregion

    public boolean DeleteEmpleado(){
        SQLiteDatabase db = this.getWritableDatabase();
        int p = db.delete("empleado_negocio", null, null);
        db.close();
        return p > 0;
    }


    //region Insertar La configuracion del empleado.
    public boolean insertConfiguracionEmple(List<Configuraciones> data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i = 0; i < data.size(); i++) {
            try {
                values.put("valor",data.get(i).getValor());
                values.put("variable",data.get(i).getVariable());

                db.insert("configuracion_emple", null, values);

            }catch (SQLiteConstraintException e){
                db.close();
                return false;
            }
        }

        db.close();
        return true;
    }
    //endregion

    //region Insertar Almacenes del empleado.
    public boolean insertAlmacenEmple(List<Almacenes> data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i = 0; i < data.size(); i++) {
            try {
                values.put("descripcion", data.get(i).getDescripcion());
                values.put("id", data.get(i).getId());
                values.put("prd", data.get(i).getPrd());

                db.insert("almacen_emple", null, values);

            } catch (SQLiteConstraintException e) {
                db.close();
                return false;
            }
        }

        db.close();
        return true;
    }
    //endregion

    //region Insertar Agencias de los Empleados.
    public boolean insertAgenciaEmple(List<Agencia> data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i = 0; i < data.size(); i++) {
            try {
                values.put("Id", data.get(i).getId());
                db.insert("agencia_emple", null, values);
            }catch (SQLiteConstraintException e){
                db.close();
                return false;
            }
        }

        db.close();
        return true;
    }
    //endregion

    //region Recuperar si ya tengo agencias en la bd
    public String selectAgenciasEmp(int data){

        boolean _id = false;
        boolean _validate = false;

        String sql = "SELECT * FROM agencia_emple";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
                // Se que encontro datos
            _validate = true;

            String sql_validate = "SELECT * FROM agencia_emple WHERE Id = "+data;
            Cursor cursor_validate = db.rawQuery(sql_validate, null);

            if (cursor_validate.getCount() > 0) {
                // la sede existe
                _id = true;
                _validate = false;
            }

        }

        if (_validate){
            return "1";
        } else if (_id) {
            return "2";
        } else {
            return "3";
        }

    }
    //endregion

    //region Insertar parametro al inicio de la aplicacion
    public boolean insertIntro(String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put("idintro",data);
            db.insert("intro", null, values);
        }catch (SQLiteConstraintException e){
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;

    }
    //endregion

    //region Insertar negocios
    public boolean insertNegocio(LoginResponce data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put("CodigoNegocio",data.getCodigoNegocio());
            values.put("Negocio",data.getNegocio());
            values.put("UrlImg",data.getUrlImg());
            db.insert("negocio", null, values);

            insertAgencias(data.getListAgencia());
            insertips(data.getListIp());

        }catch (SQLiteConstraintException e){
            db.close();
            return false;
        }

        db.close();
        return true;
    }
    //endregion

    //region Insertar Agencias..
    public boolean insertAgencias(List<ListAgencia> data){

        if(data != null && data.size() > 0){
            ContentValues valueAdicion = new ContentValues();
            SQLiteDatabase db = this.getWritableDatabase();
            int idNegocio = ultimoRegistro("negocio");

            for(int f = 0; f < data.size(); f++) {
                try {
                    valueAdicion.put("key", data.get(f).getKey());
                    valueAdicion.put("value", data.get(f).getValue());
                    valueAdicion.put("idnegocio", idNegocio);

                    db.insert("agencia", null, valueAdicion);

                }catch (SQLiteConstraintException e){
                    db.close();
                    return false;
                }
            }
        }

        return true;
    }
    //endregion

    //region Insertar ips..
    public boolean insertips(List<ListIp> data){

        if(data != null && data.size() > 0){
            ContentValues valueAdicion = new ContentValues();
            SQLiteDatabase db = this.getWritableDatabase();
            int idNegocio = ultimoRegistro("negocio");

            for(int i = 0; i < data.size(); i++) {
                try {
                    valueAdicion.put("key", data.get(i).getKey());
                    valueAdicion.put("value", data.get(i).getValue());
                    valueAdicion.put("idnegocio", idNegocio);

                    db.insert("ip", null, valueAdicion);

                }catch (SQLiteConstraintException e){
                    db.close();
                    return false;
                }
            }
        }

        return true;
    }
    //endregion

    //region Recuperar ultimo Registro
    public int ultimoRegistro(String table){
        int _id = 0;
        String sql = "SELECT CodigoNegocio FROM "+ table +" ORDER BY CodigoNegocio DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                _id = Integer.parseInt(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        return _id;
    }
    //endregion

    //region Validar si ya existe el Negocio.
    public boolean validarNegocio(String data){

        boolean _id = false;

        String sql = "SELECT CodigoNegocio FROM negocio WHERE CodigoNegocio = "+data+" LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                _id = true;
            } while(cursor.moveToNext());
        }
        return _id;
    }
    //endregion

    //region Validar Incio de sesion de la apliacion por primera ves
    public boolean getIntro(){
        Cursor cursor;
        boolean indicador = false;
        String sql = "SELECT * FROM intro LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = true;
        }
        return indicador;
    }
    //endregion

    public List<LoginResponce> selectNegocios(){

        ArrayList<LoginResponce> listNegocio = new ArrayList<>();

        String sql = "SELECT * FROM negocio ORDER BY CodigoNegocio ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {

            LoginResponce loginResponce = new LoginResponce();

            do {

                loginResponce.setCodigoNegocio(cursor.getString(0));
                loginResponce.setNegocio(cursor.getString(1));
                loginResponce.setUrlImg(cursor.getString(2));
                loginResponce.setListAgencia(listAgencias(cursor.getString(0)));
                loginResponce.setListIp(listIps(cursor.getString(0)));

                listNegocio.add(loginResponce);
            } while(cursor.moveToNext());
        }

        return listNegocio;

    }

    public List<ListIp> listIps(String idNegocio){
        ArrayList<ListIp> listIps = new ArrayList<>();
        String sql = "SELECT key, value FROM ip WHERE idnegocio = "+idNegocio+" ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {

            do {
                ListIp listIp = new ListIp();
                listIp.setKey(cursor.getString(0));
                listIp.setValue(cursor.getString(1));

                listIps.add(listIp);
            } while(cursor.moveToNext());
        }

        return listIps;
    }

    public List<ListAgencia> listAgencias(String idNegocio){
        ArrayList<ListAgencia> listAgencias = new ArrayList<>();
        String sql = "SELECT * FROM agencia WHERE idnegocio = "+idNegocio+" ORDER BY value ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {

            do {
                ListAgencia listAgencia = new ListAgencia();
                listAgencia.setKey(cursor.getString(0));
                listAgencia.setValue(cursor.getString(1));
                listAgencia.setIdNegocio(cursor.getString(2));

                listAgencias.add(listAgencia);
            } while(cursor.moveToNext());
        }

        return listAgencias;
    }

}
