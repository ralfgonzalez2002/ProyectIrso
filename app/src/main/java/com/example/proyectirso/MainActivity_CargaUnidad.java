package com.example.proyectirso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity_CargaUnidad extends AppCompatActivity {
    private EditText et_unidad, et_descripcionu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__carga_unidad);

        et_unidad = (EditText) findViewById(R.id.txt_unidad);
        et_descripcionu = (EditText) findViewById(R.id.txt_descripcion);
    }

    public String RegistrarUnidad(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Unidades", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String nombreUnidad = et_unidad.getText().toString();
        String descUnidad = et_descripcionu.getText().toString();

        if(!nombreUnidad.isEmpty() && !descUnidad.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("nombre", nombreUnidad);
            registro.put("descripcion", descUnidad);

            BaseDeDatos.insert("unidades", null, registro);
            BaseDeDatos.close();
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, MainActivity_CargaUnidad.class);
            startActivity(intent);
            finish();
        }else Toast.makeText(this, "Debe completar ambos campos", Toast.LENGTH_SHORT).show();
        return null;
    }

    public void IrUnidades(View view) {
        Intent intent = new Intent(this, MainActivity_Unidades.class);
        startActivity(intent);
        finish();
    }
}