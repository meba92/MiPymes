package BaseDatos;

import android.provider.BaseColumns;

/**
 *
 * Created by Emilia on 20/10/2016..
 */

/*Se implementó la interfaz BaseColumns con el fin de agregar una columna extra que se recomienda tenga toda tabla*/

public class DataBaseManager {
    //Clases internas que definen los contenidos de las tablas.
    public static class UsuarioEntry implements BaseColumns {
        public static final String TABLE_NAME = "Usuario";

        public static final String NOMBRE = "nombre";
        public static final String CLAVE = "clave";
    }

    public static class VendedorEntry implements BaseColumns {
        public static final String TABLE_NAME = "Vendedor";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDO = "apellido";
        public static final String NOMBRE_USUARIO = "nombre_usuario";
        public static final String CEDULA = "cedula";
        public static final String TELEFONO = "telefono";
    }

    public static class ClienteEntry implements BaseColumns {
        public static final String TABLE_NAME = "Cliente";

        public static final String ID = "id";
        public static final String ID_VENDEDOR = "id_vendedor";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDO = "apellido";
        public static final String CEDULA = "cedula";
        public static final String DIRECCION = "direccion";
        public static final String TELEFONO = "telefono";
    }

    public static class PedidoEntry implements BaseColumns {
        public static final String TABLE_NAME = "Pedido";

        public static final String ID = "id";
        public static final String ID_CLIENTE = "id_cliente";
        public static final String ID_PRODUCTO = "id_producto";
        public static final String CANTIDAD = "cantidad";
        public static final String MONTO = "monto";
    }

    public static class ProductoEntry implements BaseColumns {
        public static final String TABLE_NAME = "Producto";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String UNIDAD_MEDIDA = "unidad_medida";
        public static final String PRECIO = "precio";
        public static final String STOCK_ACTUAL = "stock_actual";
        public static final String STOCK_MINIMO = "stock_minimo";
    }

    //Creacion de Tablas en variables constantes
    public static final String T_USUARIO =
            "CREATE TABLE " + UsuarioEntry.TABLE_NAME + "("
                    + UsuarioEntry.NOMBRE + " TEXT PRIMARY KEY,"
                    + UsuarioEntry.CLAVE + " TEXT NOT NULL);";

    public static final String T_VENDEDOR =
            "CREATE TABLE " + VendedorEntry.TABLE_NAME + " ("
                    + VendedorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + VendedorEntry.ID + " TEXT NOT NULL,"
                    + VendedorEntry.NOMBRE + " TEXT NOT NULL,"
                    + VendedorEntry.APELLIDO + " TEXT NOT NULL,"
                    + VendedorEntry.NOMBRE_USUARIO + " TEXT NOT NULL,"
                    + VendedorEntry.CEDULA + " INTEGER,"
                    + VendedorEntry.TELEFONO + " INTEGER,"
                    + "UNIQUE (" + VendedorEntry.ID + "));";

    public static final String T_CLIENTE =
            "CREATE TABLE " + ClienteEntry.TABLE_NAME + " ("
                    + ClienteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ClienteEntry.ID + " INTEGER NOT NULL,"
                    + ClienteEntry.ID_VENDEDOR + " INTEGER NOT NULL,"
                    + ClienteEntry.NOMBRE + " TEXT NOT NULL,"
                    + ClienteEntry.APELLIDO + " TEXT NOT NULL,"
                    + ClienteEntry.CEDULA + " INTEGER NOT NULL,"
                    + ClienteEntry.DIRECCION + " TEXT NOT NULL,"
                    + ClienteEntry.TELEFONO + " INTEGER NOT NULL,"
                    + "UNIQUE (" + ClienteEntry.ID + "));";

    public static final String T_PEDIDO =
            "CREATE TABLE " + PedidoEntry.TABLE_NAME + " ("
                    + PedidoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PedidoEntry.ID + " INTEGER NOT NULL,"
                    + PedidoEntry.ID_CLIENTE + " INTEGER NOT NULL,"
                    + PedidoEntry.ID_PRODUCTO + " INTEGER NOT NULL,"
                    + PedidoEntry.CANTIDAD + " INTEGER NOT NULL,"
                    + PedidoEntry.MONTO + "INTEGER,"
                    + "UNIQUE (" + PedidoEntry.ID + "));";

    public static final String T_PRODUCTO =
            "CREATE TABLE " + ProductoEntry.TABLE_NAME + " ("
                    + ProductoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ProductoEntry.ID + " INTEGER NOT NULL,"
                    + ProductoEntry.NOMBRE + " TEXT NOT NULL,"
                    + ProductoEntry.UNIDAD_MEDIDA + " TEXT,"
                    + ProductoEntry.PRECIO + " INTEGER NOT NULL,"
                    + ProductoEntry.STOCK_ACTUAL + " INTEGER,"
                    + ProductoEntry.STOCK_MINIMO + " INTEGER,"
                    + "UNIQUE (" + ProductoEntry.ID + "));";

    //Para eliminar tablas
    public static final String DT_USUARIO = "DROP TABLE IF EXISTS " + UsuarioEntry.TABLE_NAME;

    public static final String DT_VENDEDOR = "DROP TABLE IF EXISTS " + VendedorEntry.TABLE_NAME;

    public static final String DT_CLIENTE = "DROP TABLE IF EXISTS " + ClienteEntry.TABLE_NAME;

    public static final String DT_PEDIDO = "DROP TABLE IF EXISTS " + PedidoEntry.TABLE_NAME;

    public static final String DT_PRODUCTO = "DROP TABLE IF EXISTS " + ProductoEntry.TABLE_NAME;



}
