package co.dito.abako.abako.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import co.dito.abako.abako.Entities.ListAgencia;
import co.dito.abako.abako.Entities.ListIp;
import co.dito.abako.abako.Entities.LoginResponce;

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

        db.execSQL(sqlIntro);
        db.execSQL(sqlNegocio);
        db.execSQL(sqlAgencias);
        db.execSQL(sqlIps);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS intro");
        db.execSQL("DROP TABLE IF EXISTS negocio");
        db.execSQL("DROP TABLE IF EXISTS agencia");
        db.execSQL("DROP TABLE IF EXISTS ip");
        this.onCreate(db);
    }

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
                    valueAdicion.put("key", data.get(f).getValue());
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

            for(int f = 0; f < data.size(); f++) {
                try {
                    valueAdicion.put("key", data.get(f).getValue());
                    valueAdicion.put("value", data.get(f).getValue());
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

    //region Validar si ya existe el registro
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

}
