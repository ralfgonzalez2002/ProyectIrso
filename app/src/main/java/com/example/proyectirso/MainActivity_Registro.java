package com.example.proyectirso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity_Registro extends AppCompatActivity {
    private EditText et_mail, et_clave, et_confirmclave, et_nombre, et_telf, et_materia;
    String phone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__registro);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity_Registro.this, new String[]{
                    Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS
            }, 121);
            return;
        }


        phone = telephonyManager.getLine1Number();

        et_mail = (EditText) findViewById(R.id.txt_mail);
        et_mail.requestFocus();
        et_clave = (EditText) findViewById(R.id.txt_clave);
        et_confirmclave = (EditText) findViewById(R.id.txt_cofirmclave);
        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        et_telf = (EditText) findViewById((R.id.txt_telf));
        et_telf.setText(phone, EditText.BufferType.EDITABLE);
        et_materia = (EditText) findViewById(R.id.txt_materia);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 121 && resultCode == RESULT_OK) {
            startActivity(new Intent(MainActivity_Registro.this, MainActivity_Registro.class));
            finish();

        }
    }

    //Metodo para dar de alta usuario
    public String Registrar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Profesores", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String mail = et_mail.getText().toString();
        String clave = et_clave.getText().toString();
        String confirmclave = et_confirmclave.getText().toString();
        String nombre = et_nombre.getText().toString();
        String telf = et_telf.getText().toString();
        String materia = et_materia.getText().toString();

        if (!mail.isEmpty() && !clave.isEmpty() && !confirmclave.isEmpty() && !nombre.isEmpty() &&
                !telf.isEmpty()) {
            if (EmailValido(mail)) {
                Cursor fila = BaseDeDatos.rawQuery("select mail from Profesores " +
                        "where mail = '" + mail + "'", null);
                if (!fila.moveToFirst()) {
                    if (clave.trim().length() >= 8) {
                        if (confirmclave.trim().length() >= 8) {
                            if (nombre.trim().length() >= 5) {
                                if (materia.trim().length() >= 5) {
                                    if (clave.equals(confirmclave)) {
                                        ContentValues registro = new ContentValues();

                                        registro.put("mail", mail);
                                        registro.put("clave", clave);
                                        registro.put("nombre", nombre);
                                        registro.put("telf", telf);

                                        BaseDeDatos.insert("profesores", null, registro);
                                        BaseDeDatos.close();
                                        IrAtras(view);
                                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(this, "Las contraseñas no coinciden, verifique", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "El nombre de su Materia debe tener al menos 5 caracteres", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Su nombre debe tener al menos 5 caracteres", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Su contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Su contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Este email ya se encuentra registrado", Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(this, "Mail inválido, por favor verifiques", Toast.LENGTH_SHORT).show();
            return null;
        } else
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        return null;
    }


    public boolean EmailValido(String email) {
        String expresiones = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(expresiones, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void IrAtras(View view) {
        Intent intent = new Intent(this, MainActivity_Login.class);
        startActivity(intent);
        finish();
    }

}