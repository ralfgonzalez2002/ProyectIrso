package com.example.proyectirso;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity_Login extends AppCompatActivity {

    EditText et_usuario, et_pass;
    ImageView iv_logo;
    //private Object MainActivity_Registro;
    //private Object MainActivity_Inicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        et_usuario = (EditText)findViewById(R.id.txt_usuario);
        et_usuario.requestFocus();
        et_pass = (EditText)findViewById(R.id.txt_clave);
        iv_logo = (ImageView)findViewById(R.id.image_logo);

        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        int id = getResources().getIdentifier("iconoirso", "drawable", getPackageName());
        iv_logo.setImageResource(id);*/
    }

    public void IrRegistrar(View view){
        Intent intent = new Intent(this, MainActivity_Registro.class);
        startActivity(intent);
        finish();
    }

    public void Ingresar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Profesores", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();//modo lectura y scritura

        String usuario = et_usuario.getText().toString();
        String pass = et_pass.getText().toString();


        if((!usuario.isEmpty()) || (!pass.isEmpty())){
            Cursor fila = BaseDeDatos.rawQuery("select mail, clave, nombre from Profesores " +
                    "where mail ='"+ usuario +"' and clave = '"+ pass +"'", null);

            //fijarnos si tiene valores en la bd y mostrar
            if(fila.moveToFirst()){
                et_usuario.setText(fila.getString(0));
                et_pass.setText(fila.getString(1));
                MainActivity_Inicio.name = fila.getString(2);
                BaseDeDatos.close();
               // Toast.makeText(this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity_Inicio.class);

                startActivity(i);

            } else {
                Toast.makeText(this, "Usuario y/o cotraseña incorrecta", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }

        } else{
            Toast.makeText(this, "Debes ingresar los datos", Toast.LENGTH_SHORT).show();
        }

    }

}