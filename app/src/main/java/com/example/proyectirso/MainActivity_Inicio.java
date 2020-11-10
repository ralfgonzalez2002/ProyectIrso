package com.example.proyectirso;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity_Inicio extends AppCompatActivity {
    private TextView tv_name;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inicio);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void goToCalendar(View view){
        Intent intent = new Intent(this, MainActivity_Calendar.class);
        startActivity(intent);
        finish();
    }

    public void goToStudents(View view){
        Intent intent = new Intent(this, MainActivity_ListaAlumnos.class);
        startActivity(intent);
        finish();
    }

    public void goToLessons(View view){
        Intent intent = new Intent(this, MainActivity_Unidades.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view){
        Intent intent = new Intent(this, MainActivity_Login.class);
        startActivity(intent);
        finish();
    }




}