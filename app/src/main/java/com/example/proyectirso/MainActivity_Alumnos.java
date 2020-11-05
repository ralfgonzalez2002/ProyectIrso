package com.example.proyectirso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity_Alumnos extends AppCompatActivity {
    private EditText et_nameALumno, et_surnameAlumno, et_dniAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__alumnos);

        et_nameALumno = (EditText) findViewById(R.id.txt_namealumno);
        et_surnameAlumno = (EditText) findViewById(R.id.txt_surnamealumno);
        et_dniAlumno = (EditText) findViewById(R.id.txt_dnialumno);

    }

    //Metodo para dar de registrar alumno
    public String RegistrarAlumno(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Alumnos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String nameAlumno = et_nameALumno.getText().toString();
        String surnameAlumno = et_surnameAlumno.getText().toString();
        String dniAlumno = et_dniAlumno.getText().toString();

        if(!nameAlumno.isEmpty() && !surnameAlumno.isEmpty() && !dniAlumno.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("nombre", nameAlumno);
            registro.put("apellido", surnameAlumno);
            registro.put("dni", dniAlumno);

            BaseDeDatos.insert("alumnos", null, registro);
            BaseDeDatos.close();
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();


            Intent intent = new Intent(this, MainActivity_ListaAlumnos.class);
            startActivity(intent);
            finish();
        }else Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        return null;
    }
}

/*try{
        BaseDeDatos.insert("alumnos", null, registro);
        BaseDeDatos.close();
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
        } catch (SQLException e){
        Toast.makeText(this, "No se realizó el registro", Toast.LENGTH_LONG).show();
        }*/