package com.example.proyectirso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity_Inicio extends AppCompatActivity {
    private TextView tv_name;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv_name = (TextView) findViewById(R.id.txt_clave);
        tv_name.setText("Hola " + name + "!");

    }

    public void goToCalendar(View view){
        Intent intent = new Intent(this, MainActivity_Calendar.class);
        startActivity(intent);
        finish();
    }

    public void goToStudents(View view){
        Intent intent = new Intent(this, MainActivity_Alumnos.class);
        startActivity(intent);
        finish();
    }
}