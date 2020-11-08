package com.example.proyectirso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity_Calendar extends AppCompatActivity {
    EditText titulo;
    EditText ubicacion;
    EditText descripcion;
    Button agregarEvento;
    Button consultarEventos;
    Button volverAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__calendar);

        titulo = findViewById(R.id.et_titulo);
        ubicacion = findViewById(R.id.et_ubicacion);
        descripcion = findViewById(R.id.txt_descripcion);
        agregarEvento = findViewById(R.id.btn_agregar);
        consultarEventos = findViewById(R.id.btn_consultar);
        volverAtras = findViewById(R.id.btn_atras);

        agregarEvento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!titulo.getText().toString().isEmpty() && !ubicacion.getText().toString().isEmpty()
                        && !descripcion.getText().toString().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, titulo.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, ubicacion.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, descripcion.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, "true");
                    intent.putExtra(Intent.EXTRA_EMAIL, "test@mailinator.com, tes2@mailinator.com, test3@mailinator.com");

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity_Calendar.this, "Esta acción no está permitida",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity_Calendar.this, "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });

       consultarEventos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // A date-time specified in milliseconds since the epoch.
                long startMillis = System.currentTimeMillis();
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
                startActivity(intent);
            }
       });
    }

    public void volverInicio(View view) {
        Intent intent = new Intent(this, MainActivity_Inicio.class);
        startActivity(intent);
        finish();
    }
}