package com.example.proyectirso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity_Unidades extends AppCompatActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__unidades);
        lv = (ListView) findViewById(R.id.lv_unidades);

        cargarUnidades();
    }

    private void cargarUnidades(){
        String desc = getIntent().getStringExtra("descripcion");
        String QR = "SELECT * FROM Unidades";
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Unidades", null, 1);
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
                    String linea = registros.getString(1)+": "+registros.getString(2);
                    arreglo[i] = linea;
                    i++;
                } while (registros.moveToNext());

            }

            if (cantidad == 0) {
                Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();

            } else {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo);
                ListView lista = (ListView) findViewById(R.id.lv_unidades); //Lista es el id del listview de la activity que muestra la data.
                lista.setAdapter(adapter);
               // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo);
                //final Spinner lista = (Spinner) findViewById(R.id.sp_unidades); //Lista es el id del listview de la activity que muestra la data.
               // lista.setAdapter(adapter);

            }
        }
    }

    public void IrInicio(View view) {
        Intent intent = new Intent(this, MainActivity_Inicio.class);
        startActivity(intent);
        finish();
    }

    public void IrCargarUnidad(View view) {
        Intent intent = new Intent(this, MainActivity_CargaUnidad.class);
        startActivity(intent);
        finish();
    }
}
