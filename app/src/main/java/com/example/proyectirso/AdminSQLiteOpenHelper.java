package com.example.proyectirso;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("create table profesores(codigo int primary key, mail text, clave text, nombre text, telf text)");
        BaseDeDatos.execSQL("create table alumnos(codigo integer primary key autoincrement, nombre String, apellido String, dni String)");
    }

    public void onUpgrade(SQLiteDatabase BaseDeDatos, int oldversion, int newVersion){

    }

    /*@Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }*/

    public ArrayList llenar_lv(){

        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM alumnos";
        Cursor registros = database.rawQuery(q, null);
        if(registros.moveToFirst()){
            do {
                lista.add(registros.getString(0) + " " + registros.getString(1) + " " +
                        registros.getString(2));

            } while(registros.moveToNext());

        }
        return lista;
    }
}
