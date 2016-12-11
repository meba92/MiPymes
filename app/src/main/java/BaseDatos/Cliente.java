package BaseDatos;

import android.content.ContentValues;

import BaseDatos.DataBaseManager.ClienteEntry;

/**
 * Created by Emilia on 20/10/2016..
 *
 */

// Entidad Cliente
public class Cliente {
    private String id;
    private String id_vendedor;
    private String nombre;
    private String apellido;
    private String cedula;
    private String direccion;
    private String telefono;

	//Constructor de la clase
    public Cliente(String id, String id_vendedor, String nombre, String apellido, String cedula, String direccion, String telefono){
        this.id = id;
        this.id_vendedor = id_vendedor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getId(){ return id; }

    public void setId(String id){ this.id = id; }

    public String getId_vendedor(){ return id_vendedor; }

    public void setId_vendedor(String id_vendedor){ this.id_vendedor = id_vendedor; }

    public String getNombre(){ return nombre; }

    public void setNombre(String nombre){ this.nombre = nombre; }

    public String getApellido(){ return apellido; }

    public void setApellido(String apellido){ this.apellido = apellido; }

    public String getCedula(){ return cedula; }

    public void setCedula(String cedula){ this.cedula = cedula; }

    public String getDireccion(){ return direccion; }

    public void setDireccion(String direccion){ this.direccion = direccion; }

    public String getTelefono(){ return telefono; }

    public void setTelefono(String telefono){ this.telefono = telefono; }


    public ContentValues toContentValues() {
        //objeto de tipo ContentValues, almacena los valores de los atributos de los objetos.
        ContentValues cv = new ContentValues();

        cv.put(ClienteEntry.ID, id);
        cv.put(ClienteEntry.ID_VENDEDOR, id_vendedor);
        cv.put(ClienteEntry.NOMBRE, nombre);
        cv.put(ClienteEntry.APELLIDO, apellido);
        cv.put(ClienteEntry.CEDULA, cedula);
        cv.put(ClienteEntry.DIRECCION, direccion);
        cv.put(ClienteEntry.TELEFONO, telefono);

        return cv;
    }
}
