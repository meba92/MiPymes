package BaseDatos;

import android.content.ContentValues;

import BaseDatos.DataBaseManager.PedidoEntry;

/**
 *
 *
 *
 * Created by Emilia on 20/10/2016.
 */

//Entidad Pedido
public class Pedido {
    private String id;
    private String id_cliente;
    private String id_producto;
    private String cantidad;
    private String monto;
	private String total;

	//Constructor de la clase
    public Pedido(String id, String id_cliente, String id_producto, String cantidad, String monto, String total){
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.monto = monto;
        this.total = total;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId_cliente(){
        return id_cliente;
    }

    public void setId_cliente(String id_cliente){
        this.id_cliente = id_cliente;
    }

    public String getId_producto(){
        return id_producto;
    }

    public void setId_producto(String id_producto){
        this.id_producto = id_producto;
    }

    public String getCantidad(){
        return cantidad;
    }

    public void setCantidad(String cantidad){
        this.cantidad = cantidad;
    }

    public String getMonto(){
        return monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }

    public String getTotal(){
        return total;
    }

    public void setTotal(String total){
        this.total = total;
    }

    public ContentValues toContentValues() {
        //objeto de tipo ContentValues, almacena los valores de los atributos de los objetos.
        ContentValues cv = new ContentValues();

        cv.put(PedidoEntry.ID, id);
        cv.put(PedidoEntry.ID_CLIENTE, id_cliente);
        cv.put(PedidoEntry.ID_PRODUCTO, id_producto);
        cv.put(PedidoEntry.CANTIDAD, cantidad);
        cv.put(PedidoEntry.MONTO, monto);

        return cv;
    }
	
}
