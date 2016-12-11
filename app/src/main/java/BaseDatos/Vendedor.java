package BaseDatos;


import android.content.ContentValues;

import BaseDatos.DataBaseManager.VendedorEntry;

/**
 * Created by Emilia on 20/10/2016.
 */
 
//Entidad Vendedor
public class Vendedor {
    private String id;
    private String nombre;
    private String apellido;
    private String nombre_usuario;
    private String cedula;
    private String telefono;

    //Constructor de la clase
    public Vendedor(String id, String nombre, String apellido, String nombre_usuario, String cedula, String telefono){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombre_usuario = nombre_usuario;
        this.cedula = cedula;
        this.telefono = telefono;
    }

    public String getId(){ return id;}

    public void setId(String id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public String getNombre_usuario(){
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario){
        this.nombre_usuario = nombre_usuario;
    }

    public String getCedula(){
        return cedula;
    }

    public void setCedula(String cedula){
        this.cedula = cedula;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }


    public ContentValues toContentValues() {
        //objeto de tipo ContentValues, almacena los valores de los atributos de los objetos.
        ContentValues cv = new ContentValues();

        cv.put(VendedorEntry.ID, id);
        cv.put(VendedorEntry.NOMBRE, nombre);
        cv.put(VendedorEntry.APELLIDO, apellido);
        cv.put(VendedorEntry.NOMBRE_USUARIO, nombre_usuario);
        cv.put(VendedorEntry.CEDULA, cedula);
        cv.put(VendedorEntry.TELEFONO, telefono);

        return cv;
    }
}
