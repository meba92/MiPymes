package BaseDatos;

import android.content.ContentValues;

import BaseDatos.DataBaseManager.ProductoEntry;

/**
 *
 * Created by Emilia on 20/10/2016..
 */

//Entidad Producto
public class Producto {
    private String id;
    private String nombre;
    private String unidad_medida;
    private String precio;
    private String stock_actual;
    private String stock_minimo;

	//Constructor de la clase
    public Producto(String id, String nombre, String unidad_medida, String precio, String stock_actual,
                    String stock_minimo){
        this.id = id;
        this.nombre = nombre;
        this.unidad_medida = unidad_medida;
        this.precio = precio;
        this.stock_actual = stock_actual;
        this.stock_minimo = stock_minimo;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre= nombre;
    }

    public String getUnidad_medida(){
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida){
        this.unidad_medida = unidad_medida;
    }

    public String getPrecio(){
        return precio;
    }

    public void setPrecio(String precio){
        this.precio = precio;
    }

    public String getStock_actual(){
        return stock_actual;
    }

    public void setStock_actual(String stock_actual){
        this.stock_actual = stock_actual;
    }

    public String getStock_minimo(){
        return stock_minimo;
    }

    public void setStock_minimo(String stock_minimo){
        this.stock_minimo = stock_minimo;
    }


    public ContentValues toContentValues() {
        //objeto de tipo ContentValues, almacena los valores de los atributos de los objetos.
        ContentValues cv = new ContentValues();

        cv.put(ProductoEntry.ID, id);
        cv.put(ProductoEntry.NOMBRE, nombre);
        cv.put(ProductoEntry.UNIDAD_MEDIDA, unidad_medida);
        cv.put(ProductoEntry.PRECIO, precio);
        cv.put(ProductoEntry.STOCK_ACTUAL, stock_actual);
        cv.put(ProductoEntry.STOCK_MINIMO, stock_minimo);

        return cv;
    }

}
