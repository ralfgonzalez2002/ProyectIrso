package com.example.proyectirso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity_ListaAlumnos extends AppCompatActivity {
    ListView lv;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__lista_alumnos);
        lv = (ListView) findViewById(R.id.lv_alumnos);
        /*AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(getApplicationContext(), null, null, 1);
        lista = db.llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv.setAdapter(adaptador);*/
        cargar();

    }

    public void cargar() {
        String desc = getIntent().getStringExtra("apellido");
        String QR = "SELECT * FROM Alumnos";
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Alumnos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase(); //este metodo se supone que es para abrir y poder escribir en la bd.

        if (BaseDeDatos != null) {
            if (desc != null) {

                QR = QR + "Descripcion =" + desc;
            }
            Cursor registros = BaseDeDatos.rawQuery(QR, null);
            int cantidad = registros.getCount(); //obtengo la cantidad de registros.
            int i = 0;
            String[] arreglo = new String[cantidad];
            if (registros.moveToFirst()) {
                //con esto verificamos si hay un proximo registro
                do {
                    String linea = registros.getInt(0)+" - "+ registros.getString(1)+" "+ registros.getString(2)+
                            "                               "+ registros.getString(3);
                    arreglo[i] = linea;
                    i++;
                } while (registros.moveToNext());

            }

            if (cantidad == 0) {
                Toast.makeText(this, "No se encontraron registros", Toast.LENGTH_SHORT).show();

            } else {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo);
                ListView lista = (ListView) findViewById(R.id.lv_alumnos); //Lista es el id del listview de la activity que muestra la data.
                lista.setAdapter(adapter);
            }
        }

    }

    public void IrAlumnos(View view) {
        Intent intent = new Intent(this, MainActivity_Alumnos.class);
        startActivity(intent);
        finish();
    }
}
