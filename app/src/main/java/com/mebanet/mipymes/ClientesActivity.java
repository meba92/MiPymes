package com.mebanet.mipymes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import BaseDatos.DataBaseManager;
import BaseDatos.DbHelperMiPymes;

public class ClientesActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {
    Spinner spClientes;
    TextView tvDireccion;
    TextView tvTelefono;
    Button btLevantarPedido;
    Cursor query;
    String idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        DbHelperMiPymes db = new DbHelperMiPymes(this);
        db.openReadableDB();    //Se abre la BD

        spClientes = (Spinner) findViewById(R.id.spinnerClientes);
        tvDireccion = (TextView) findViewById(R.id.Direccion);
        tvTelefono = (TextView) findViewById(R.id.Telefono);

        //tvDireccion.setText();
        btLevantarPedido = (Button) findViewById(R.id.btLevantarPedido);
        btLevantarPedido.setOnClickListener(this);


        //Creando Adaptador para spClientes
        SimpleCursorAdapter mClientesAdapter = new SimpleCursorAdapter(
                this, // Context context
                android.R.layout.simple_list_item_1, // int layout
                db.getClientes(), // Cursor c
                new String[]{DataBaseManager.ClienteEntry.NOMBRE + "_" + DataBaseManager.ClienteEntry.APELLIDO}, // String[] from
                new int[]{android.R.id.text1}, // int[] to
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER // int flags
        );

        //Seteando Adaptador de spClientes
        spClientes.setAdapter(mClientesAdapter);

        //Relacionado la escucha de selección de spClientes
        spClientes.setOnItemSelectedListener(this);

        db.closeDB(); //Cerramos la BD
    }

    //Método correspondiente al botón Levantar Pedido
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btLevantarPedido:
                if(!getIdCliente().isEmpty()) {
                    Intent intent = new Intent(ClientesActivity.this, LevantarActivity.class);
                    intent.putExtra("idCliente", getIdCliente());
                    startActivity(intent);
                }else{
                    getMsgIdClienteIsEmpty();
                }
                break;
        }


    }

    /*SETTERS AND GETTERS*/
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    /*MENSAJES MOSTRADOS AL USUARIO*/
    public void getMsgIdClienteIsEmpty(){
        Toast.makeText(getApplicationContext(), "Id Cliente no se ha encontrado", Toast.LENGTH_SHORT).show();
    }



    //Método que corresponde a la selección del Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        DbHelperMiPymes helper = new DbHelperMiPymes(this);
        SQLiteDatabase dataBase = helper.getWritableDatabase();

        //Recupera el ultimo id insertado
        Cursor cursorCliente = dataBase.rawQuery("SELECT * FROM cliente;", null);
        if(cursorCliente.moveToFirst()) {
            //Extrae el item correspondiente a la posición elegida en el Spinner
            Cursor c = (Cursor) spClientes.getItemAtPosition(spClientes.getSelectedItemPosition());
            //Extrae la dirección del cliente del Item seleccionado del cursor
            String direccion = c.getString(c.getColumnIndexOrThrow("direccion"));
            //Extrae el teléfono del cliente  del Item seleccionado del cursor
            String telefono = c.getString(c.getColumnIndexOrThrow("telefono"));
            //Extrae la id del cliente del Item seleccionado del cursor
            setIdCliente(c.getString(c.getColumnIndexOrThrow("_id")));
            //Refresca el TextView Direccion
            tvDireccion.setText(direccion);
            //Refresca el TextView Telefono
            tvTelefono.setText(telefono);
            //clearTextView();
        }
    }

    public void clearTextView(){
        tvDireccion.setText("");
        tvTelefono.setText("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
