package com.example.tarea1database;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etCedul, etNomb, etTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCedul = findViewById(R.id.etCedula);
        etNomb = findViewById(R.id.etNombre);
        etTel = findViewById(R.id.etTelefono);

    }

    public void registrar(View view) {
        AdminBD admin = new AdminBD(this, "BaseDatos", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        String cedula = etCedul.getText().toString();
        String nombre = etNomb.getText().toString();
        String telefono = etTel.getText().toString();
        if (!cedula.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("cedula", cedula);
            registro.put("nombre", nombre);
            registro.put("telefono", telefono);
            BaseDatos.insert("usuario", null, registro);
            BaseDatos.close();
            etCedul.setText("");
            etNomb.setText("");
            etTel.setText("");
            Toast.makeText(this, "Registro almacenado exitosamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Ingrese correctamente todos los datos", Toast.LENGTH_LONG).show();
        }
    }

    public void consultar(View view) {
        AdminBD admin = new AdminBD(this, "BaseDatos", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        String cedula1 = etCedul.getText().toString();
        if (!cedula1.isEmpty()) {
            Cursor fila = BaseDatos.rawQuery("Select nombre, telefono from usuario where cedula = " + cedula1, null);
            if (fila.moveToFirst()) {
                etNomb.setText(fila.getString(0));
                etTel.setText(fila.getString(1));
                BaseDatos.close();
            } else {
                Toast.makeText(this, "No se encontro el usuario", Toast.LENGTH_LONG).show();
            }
        }
    }
}
