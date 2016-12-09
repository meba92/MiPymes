package com.mebanet.mipymes;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;
import java.util.ArrayList;

import BaseDatos.DataBaseManager;
import BaseDatos.DbHelperMiPymes;

public class LevantarActivity extends AppCompatActivity implements OnClickListener,
        AdapterView.OnItemSelectedListener, Serializable {
    Spinner spProductos;
    Spinner spCantidad;
    TextView tvPrecio;
    TextView tvImporte;
    Button btRegistrar;
    Button btVerPedido;
    String productoName;
    String cantidad;
    ArrayList pedidoList = new ArrayList();
    String idCliente;
    String idProducto;
    boolean flag;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levantar);

        DbHelperMiPymes db = new DbHelperMiPymes(this);
        db.openReadableDB();    //Se abre la BD

        spProductos = (Spinner) findViewById(R.id.spinnerProductos); //spinner de productos
        spCantidad = (Spinner) findViewById(R.id.spinnerCantidad); //spinner de cantidad

        tvPrecio = (TextView) findViewById(R.id.precio_unitario);

        tvImporte = (TextView) findViewById(R.id.importe);
        btRegistrar = (Button) findViewById(R.id.button_registrar);
        btRegistrar.setOnClickListener(this);

        btVerPedido = (Button) findViewById(R.id.button_ver_pedido);
        btVerPedido.setOnClickListener(this);

        //Creando Adaptador para spClientes
        SimpleCursorAdapter mProductosAdapter = new SimpleCursorAdapter(
                this, // Context context
                android.R.layout.simple_list_item_1, // int layout
                db.getProductos(), // Cursor c
                new String[]{DataBaseManager.ProductoEntry.NOMBRE}, // String[] from
                new int[]{android.R.id.text1}, // int[] to
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER // int flags

        );


        //Extrae el idCliente que en la actividad ClienteActivity se selecciono
        try{
            Bundle extras = getIntent().getExtras();
            idCliente = (String) extras.getSerializable("idCliente"); //Extrae el String de ClienteActivity
        }catch (Exception e){
           /*
           *No hace nada, simplemente captura la excepción que se genera
           *al volver de la actividad RegistroActivity para que la aplicación
           *no se detenga en ese momento.
           * */
        }

        //Seteando Adaptador de spClientes
        spProductos.setAdapter(mProductosAdapter);

        //Relacionado la escucha de selección de spClientes
        spProductos.setOnItemSelectedListener(this);
        //Relacionado la escucha de selección de spProducto

        spCantidad.setOnItemSelectedListener(this);
        //spCantidad.setSelection(0);

        db.closeDB(); //Cerramos la BD
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //Muestra mensaje de bienvenida
        getMsgWelcome();
    }

    /*SETTERS AND GETTERS*/
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public boolean getPedidoSelected() {
        return flag;
    }

    public void setPedidoSelected(boolean flag) {
        this.flag = flag;
    }

    /*MENSAJES MOSTRADOS AL USUARIO*/
    public void getMsgIfItDoesNotExistsPedido(){
        Toast.makeText(getApplicationContext(), "No se ha registrado ningún pedido. Por favor realicelo primero y " +
                                                "luego presione el botón 'Ver pedido'", Toast.LENGTH_SHORT).show();
    }
    public void getMsgIfItDoesNotExistsProducto(){
        Toast.makeText(getApplicationContext(), "No se ha ingresado la cantidad y tampoco seleccionado un producto. " +
                                                "Por favor realicelo primero y luego presione el botón 'Regitrar'",
                                                Toast.LENGTH_SHORT).show();
    }

    public void getMsgCantidadIsZero(){
        Toast.makeText(getApplicationContext(), "Valor de cantidad es 0", Toast.LENGTH_SHORT).show();
    }

    public void getMsgWelcome(){
        Toast.makeText(getApplicationContext(), "Seleccione un producto y la cantidad antes de registrarlo, " +
                                                "luego presione el botón 'Ver Pedido' para ver sus pedidos " +
                                                "realizados hasta el momento", Toast.LENGTH_LONG
        ).show();
    }

    public void getMsgRegisteredProducto(String prodName){
        Toast.makeText(getApplicationContext(), "Producto " + prodName + " registrado", Toast.LENGTH_SHORT).show();
    }

    public void getMsgStockActualIsZero(String prodName){
        Toast.makeText(getApplicationContext(), "Es Stock Actual de " + prodName + " es cero", Toast.LENGTH_SHORT).show();
    }

    public void getMsgCantidadIsHigher(String prodName){
        Toast.makeText(getApplicationContext(), "La cantidad seleccionada es mayor al Stock Actual de " +
                                                "" + prodName + "", Toast.LENGTH_SHORT).show();
    }

    public void getMsgRegistroDenied(){
        Toast.makeText(getApplicationContext(), "Registro de producto denegado", Toast.LENGTH_SHORT).show();
    }

    public void resetFields(){
        spProductos.setSelection(0);
        spCantidad.setSelection(0);
        tvPrecio.setText("");
        tvImporte.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ver_pedido:
                Intent intent = new Intent(LevantarActivity.this, RegistroActivity.class);
                if (pedidoList.isEmpty()){
                    getMsgIfItDoesNotExistsPedido();
                }else{
                    intent.putExtra("pedidoIntent", pedidoList);
                    startActivity(intent);
                }

                break;
            case R.id.button_registrar:
                if (getPedidoSelected() && Integer.parseInt(cantidad.toString()) > 0){
                    try{
                    /*
                    * Este String contendra el nombre del producto,
                    * la cantidad y el total de todos los productos
                    * seleccionados, hasta el último.
                    * */
                        String [] pedidoSimpleArray = {
                                getIdCliente(),                     //id del cliente POS 0
                                getIdProducto(),                    //id del producto POS 1
                                productoName.toString(),            //nombre del producto POS 2
                                cantidad.toString(),                //cantidad total  POS 3
                                tvImporte.getText().toString()}; //cantidad
                    /*
                    * Agrega los elementos seleccionados al ArrayList pedidoList
                    * que se enviará al RegistroActivity
                    * */
                        pedidoList.add(pedidoSimpleArray);

                        getMsgRegisteredProducto(productoName.toString()); //Muestra msg de confirmación de registro de producto

                    }catch (NullPointerException e){
                        getMsgIfItDoesNotExistsProducto();
                    }

                }else{
                    getMsgRegistroDenied();
                }
                resetFields(); //Resetea los campos Producto, Cantidad, Precio e Importe
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Extrae el item correspondiente a la posición elegida en el Spinner
        Cursor c = (Cursor) spProductos.getItemAtPosition(spProductos.getSelectedItemPosition());

        //Extrae la dirección del Item seleccionado del cursor
        Integer stockMinimo = Integer.valueOf(c.getString(c.getColumnIndexOrThrow("stock_minimo")));

        Integer stockActual = Integer.valueOf(c.getString(c.getColumnIndexOrThrow("stock_actual")));

        if(stockActual == 0){
            getMsgStockActualIsZero(c.getString(c.getColumnIndexOrThrow("nombre")));
            resetFields(); //Resetea los campos Producto, Cantidad, Precio e Importe
            setPedidoSelected(false);

        }else if (stockMinimo > stockActual || stockMinimo < stockActual || stockMinimo == stockActual){

            cantidad = (String) spCantidad.getItemAtPosition(spCantidad.getSelectedItemPosition());

            if(Integer.parseInt(cantidad.toString()) > stockActual) {
                getMsgCantidadIsHigher(c.getString(c.getColumnIndexOrThrow("nombre")));
                resetFields(); //Resetea los campos Producto, Cantidad, Precio e Importe
                setPedidoSelected(false);
            }else{
                setPedidoSelected(true);

                tvImporte.setText("");

                //Extrae la dirección del Item seleccionado del cursor
                String precio = c.getString(c.getColumnIndexOrThrow("precio"));

                //Extrae el nombre del Item seleccionado del cursor
                productoName = c.getString(c.getColumnIndexOrThrow("nombre"));

                //Extrae el id del Item seleccionado del cursor
                setIdProducto(c.getString(c.getColumnIndexOrThrow("id")));

                //Refresca el TextView Precio
                tvPrecio.setText(precio);

                Integer importe = Integer.parseInt(precio) * Integer.parseInt(cantidad);

                //Refresca el TextView Importe
                tvImporte.setText(String.valueOf(importe)); //Convierte a String cantidad

            }
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Levantar Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
