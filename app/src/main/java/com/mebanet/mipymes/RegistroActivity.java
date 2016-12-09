package com.mebanet.mipymes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import BaseDatos.DbHelperMiPymes;

public class RegistroActivity extends AppCompatActivity implements OnClickListener {
    ListView lvPedidos;
    Button btFinalizarPedido;
    ArrayList pedidosListView = new ArrayList();
    ArrayList<String []> pedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mebanet.mipymes.R.layout.activity_registro);

        DbHelperMiPymes db = new DbHelperMiPymes(this);
        db.openReadableDB();    //Se abre la BD

        lvPedidos = (ListView)findViewById(com.mebanet.mipymes.R.id.lvPedidos);

        //Extrae el ArrayList de LevantarActivity
        Bundle extras = getIntent().getExtras();
        pedList = (ArrayList<String []>) extras.getSerializable("pedidoIntent");

        //Se crea un string para mostrar en el ListView
        for (Integer i = 0; i < pedList.size(); i++) {
            String stringPedido = "Producto: " + pedList.get(i)[2] + ", " +
                                  "Cantidad: " + pedList.get(i)[3] + ", " +
                                  "Importe: " + pedList.get(i)[4] + " Gs";
            pedidosListView.add(stringPedido);
        }

        if (pedList.isEmpty()){
            //Se muestra mensaje de ListView vacio en caso de que lo esté
            getMsgPedListIsEmpty();
        }else{
            //Se carga el ListView con los string contruidos
            ArrayAdapter adapterPedido = new ArrayAdapter (this, android.R.layout.simple_list_item_1, pedidosListView);
            lvPedidos.setAdapter(adapterPedido);
        }

        btFinalizarPedido = (Button)findViewById(com.mebanet.mipymes.R.id.button_finalizar);
        btFinalizarPedido.setOnClickListener(this);

        db.closeDB(); //Cerramos la BD
    }

    /*MENSAJES MOSTRADOS AL USUARIO*/
    public void getMsgPedListIsEmpty(){
        Toast.makeText(getApplicationContext(), "Lista de pedidos vacía", Toast.LENGTH_SHORT).show();
    }

    public void getMsgCorrectInsertion(){
        Toast.makeText(getApplicationContext(), "Inserción realizada con éxito", Toast.LENGTH_SHORT).show();
    }

    public void getMsgInsertionCanceled(){
        Toast.makeText(getApplicationContext(), "Inserción de productos abortada", Toast.LENGTH_SHORT).show();
    }

    public void getMsgCorrectUpdate(){
        Toast.makeText(getApplicationContext(), "Actualización realizada con éxito", Toast.LENGTH_SHORT).show();
    }

    public void getMsgUpdateCanceled(){
        Toast.makeText(getApplicationContext(), "Actualización de productos abortada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case com.mebanet.mipymes.R.id.button_finalizar:
                DbHelperMiPymes helper = new DbHelperMiPymes(this);
                SQLiteDatabase dataBase = helper.getWritableDatabase();

                ContentValues registro = new ContentValues();
                try{
                    for (Integer i = 0; i < pedList.size(); i++) {
                        //Recupera el ultimo id insertado
                        Cursor c = dataBase.rawQuery("SELECT id FROM pedido ORDER BY id DESC LIMIT 1;", null);

                        //Se extrae el idProducto
                        String [] idProducto = new String []{pedList.get(i)[1]};

                        //Consulta el stock_actual del producto insertado para actualizar con la
                        //operación stock_actual - cantidad
                        Cursor cProducto = dataBase.rawQuery("SELECT nombre, stock_actual FROM producto WHERE id = ? ;", idProducto);

                        if(!c.moveToFirst()){
                            //Se inserta aqui cuando sera la primera insercion en la tabla Pedido
                            registro.put("id", "0");
                            registro.put("id_cliente", pedList.get(i)[0]);
                            registro.put("id_producto", pedList.get(i)[1]);
                            registro.put("cantidad", pedList.get(i)[3]);
                            registro.put("montoINTEGER", pedList.get(i)[4]);
                            getMsgCorrectInsertion();

                        }else {
                            //Se inserta de aqui en adelante con el ultimo id
                            Integer idPedido = c.getInt(0);

                            registro.put("id", String.valueOf(idPedido + 1));
                            registro.put("id_cliente", pedList.get(i)[0]);
                            registro.put("id_producto", pedList.get(i)[1]);
                            registro.put("cantidad", pedList.get(i)[3]);
                            registro.put("montoINTEGER", pedList.get(i)[4]);
                            getMsgCorrectInsertion();

                        }

                        if(cProducto.moveToFirst()){
                            Integer stockActual = Integer.valueOf(cProducto.getString(cProducto.getColumnIndexOrThrow("stock_actual")));
                            Integer cantidad = Integer.valueOf(pedList.get(i)[3]);
                            String prodName = cProducto.getString(cProducto.getColumnIndexOrThrow("nombre"));
                            /*
                            *Pregunta si el stockActual es menor a la cantidad, si es
                            *true la condición se emite un msg informando que la cantidad
                            *es mayor y que la actualización se detiene
                            * */
                            if (stockActual < cantidad){
                                getMsgInsertionCanceled();
                                getMsgUpdateCanceled();
                            }else{
                                //Se verifica si la cantidad es mayor al stock actual
                                //Si es mayor, la insecion y la actualizacion se cancelan
                                dataBase.insert("pedido", null, registro);
                                getMsgCorrectInsertion();

                                ContentValues registroUpdate = new ContentValues();

                                //registroUpdate.put('nombre del campo a actualizar', 'valor del campo a actualizar')
                                registroUpdate.put("stock_actual", String.valueOf(stockActual - cantidad));

                                //UPDATE producto SET stock_actual = stock_actual - cantidad WHERE id = id_producto;
                                dataBase.update("producto", registroUpdate, "id = ?", idProducto);
                                getMsgCorrectUpdate();

                            }
                        }
                    }
                }catch (Exception e){
                    Log.e("EXCEPTION REGISTRO", e.toString());
                }

                Intent intent = new Intent(RegistroActivity.this,ClientesActivity.class);
                startActivity(intent);
        }
    }
}
