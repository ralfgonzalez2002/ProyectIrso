package com.example.proyectirso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity_ListaAlumnos extends AppCompatActivity {
    private String stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "ListaAlumnos.csv";

    ListView lv;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__lista_alumnos);
        lv = (ListView) findViewById(R.id.lv_alumnos);
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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

    public void buttonShareFile(View view){
        GenerarCSV(view);
        String FILENAME = "listadoAlumnos.csv";
        File directoryDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File logDir = new File(directoryDownload, FILENAME);
        if (!logDir.exists()){
            Toast.makeText(this, "EL archivo no existe", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("application/csv");
        intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+logDir));
        startActivity(Intent.createChooser(intentShare, "Share the file ..."));
    }


    public void GenerarCSV(View view){

        String FILENAME = "listadoAlumnos.csv";
        File directoryDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File logDir = new File(directoryDownload, FILENAME);
        try {
            logDir.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(logDir));

            AdminSQLiteOpenHelper Admin = new AdminSQLiteOpenHelper(getBaseContext(), null, null, 1);
            SQLiteDatabase BaseDeDatos = Admin.getWritableDatabase();

            Cursor curCSV = BaseDeDatos.rawQuery("SELECT * FROM alumnos", null);
            csvWriter.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                String arrStr[] = {curCSV.getString(0) + "," +curCSV.getString(1) + ","+ curCSV.getString(2) + ","+curCSV.getString(4) + ","};
                csvWriter.writeNext(arrStr);
            }
            csvWriter.close();
            curCSV.close();

            Toast.makeText(this, "Archivo generado.", Toast.LENGTH_LONG).show();


            Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            startActivity(intent);

            // return true;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
            //   return false;
        }

    }

    public void IrAlumnos(View view) {
        Intent intent = new Intent(this, MainActivity_Alumnos.class);
        startActivity(intent);
        finish();
    }
}
