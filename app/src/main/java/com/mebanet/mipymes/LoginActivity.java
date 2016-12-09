package com.mebanet.mipymes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import BaseDatos.DbHelperMiPymes;

public class LoginActivity extends AppCompatActivity implements OnClickListener {
    EditText etUsuario;
    EditText etContrasenha;
    Button btAceptar;
    Cursor query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mebanet.mipymes.R.layout.activity_login);

        DbHelperMiPymes db = new DbHelperMiPymes(this);
        db.openReadableDB();    //Se abre la BD

        etUsuario = (EditText) findViewById(com.mebanet.mipymes.R.id.et_usuario);
        etContrasenha = (EditText) findViewById(com.mebanet.mipymes.R.id.et_contrasenha);

        btAceptar = (Button) findViewById(com.mebanet.mipymes.R.id.bt_aceptar);
        btAceptar.setOnClickListener(this);

        db.closeDB(); //Cerramos la BD
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.mebanet.mipymes.R.id.bt_aceptar:
                DbHelperMiPymes helper = new DbHelperMiPymes(this);
                SQLiteDatabase dataBase = helper.getWritableDatabase();


                String user = etUsuario.getText().toString();
                String password = etContrasenha.getText().toString();

                if (user.isEmpty() && password.isEmpty()) {
                    //Si usuario y contraseña vacíos muestra mensaje
                    Toast.makeText(getApplicationContext(), "Campos Usuario y Contraseña vacíos", Toast.LENGTH_SHORT).show();
                } else {
                    //Consulta si existe usuario y contraseña en la base de datos
                    query = dataBase.rawQuery("SELECT nombre, clave FROM usuario WHERE " +
                            "nombre = '" + user + "' AND clave = '" + password + "'", null);

                    //preguntamos si la variable query no está vacía
                    if (query.moveToFirst()) {
                        String u = query.getString(0); //extraemos el usuario en la pos 0
                        String p = query.getString(1); //extraemos la contraseña en la pos 1

                        if (user.equals(u) && password.equals(p)) {
                            Intent intent = new Intent(LoginActivity.this, ClientesActivity.class);
                            startActivity(intent);

                            clearTextBox(); //limpiamos las las cajas de texto

                            String usuario = etUsuario.getText().toString();

                            //Comentario que aparece al iniciar la app
                            Toast.makeText(getApplicationContext(), "Bienvendio al Gestor Ventas " + usuario, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario o Contraseña inválidos", Toast.LENGTH_SHORT).show();
                        clearTextBox(); //limpiamos las las cajas de texto

                    }
                }
                break;

        }
    }

    public void clearTextBox(){
        etUsuario.setText("");
        etContrasenha.setText("");
    }

}
