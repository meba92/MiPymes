package BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static BaseDatos.DataBaseManager.DT_CLIENTE;
import static BaseDatos.DataBaseManager.DT_PEDIDO;
import static BaseDatos.DataBaseManager.DT_PRODUCTO;
import static BaseDatos.DataBaseManager.DT_USUARIO;
import static BaseDatos.DataBaseManager.DT_VENDEDOR;
import static BaseDatos.DataBaseManager.T_CLIENTE;
import static BaseDatos.DataBaseManager.T_PEDIDO;
import static BaseDatos.DataBaseManager.T_PRODUCTO;
import static BaseDatos.DataBaseManager.T_USUARIO;
import static BaseDatos.DataBaseManager.T_VENDEDOR;

/**
 *
 *Created by Emilia.
 */

public class DbHelperMiPymes extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    // Si cambia el esquema de base de datos, debe incrementar la versión de la base de datos.
    private static final String DATABASE_NAME = "DbGestorVentas";
    private static final int DATABASE_VERSION = 3;

    //CONSTRUCTOR
    public DbHelperMiPymes(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //ABRIR LA BASE DE DATOS EN MODO LECTURA
    public void openReadableDB(){
        getReadableDatabase();
    }

    //ABRE LA BASE DE DATOS EN MODO LECTURA/ESCRITURA
    public void openWriteableDB(){
        getWritableDatabase();
    }

    //CERRAR LA BASE DE DATOS
    public void closeDB(){
        if(db!=null){
            db.close();
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Si no existe la base de datos entonces la crea y ejecuta los siguientes comandos
        db.execSQL(T_USUARIO);
        db.execSQL(T_VENDEDOR);
        db.execSQL(T_CLIENTE);
        db.execSQL(T_PEDIDO);
        db.execSQL(T_PRODUCTO);

        //se insertan los datos de las tablas
        mockDataUsuario(db);
        mockDataVendedor(db);
        mockDataCliente(db);
        mockDataProducto(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( newVersion > oldVersion )
        {
            //eliminamos las tablas
            db.execSQL(DT_USUARIO);
            db.execSQL(DT_VENDEDOR);
            db.execSQL(DT_CLIENTE);
            db.execSQL(DT_PEDIDO);
            db.execSQL(DT_PRODUCTO);

            onCreate(db);
        }
    }

    //CREACION DE DATOS FICTICIOS
    private void mockDataUsuario(SQLiteDatabase db) {
        mockUsuario(db, new Usuario("admin","admin"));
        mockUsuario(db, new Usuario("diegoviedo","diegoviedo"));
        mockUsuario(db, new Usuario("yanisosa","yanisosa"));
    }

    private void mockDataVendedor(SQLiteDatabase db) {
        mockVendedor(db, new Vendedor("1","Pedro","Campuzano","admin","4512197","0971690167"));
        mockVendedor(db, new Vendedor("2","Diego","Oviedo","diegoviedo","1234567","0962148182"));
        mockVendedor(db, new Vendedor("3","Yanina","Sosa","yanisosa","6543210","0982701339"));
    }

    private void mockDataCliente(SQLiteDatabase db) {
        mockCliente(db, new Cliente("1","1","Roque","Santa Cruz","1230983","Defensores del chaco c/ sajonia","0977984321"));
        mockCliente(db, new Cliente("2","1","Diego","Aquino","83612936","Ingavi casi mcal Estigarribia","0965827482"));
        mockCliente(db, new Cliente("3","1","Eduardo","Mendieta","7650384","San Lorenzo esquina Mayo","0981677689"));
        mockCliente(db, new Cliente("4","1","Roberto","Colman","1299983","Destacamento Cazal","0943226321"));

        mockCliente(db, new Cliente("5","2","Jose","Alvarez","5544346","Mercado 4","0965827482"));
        mockCliente(db, new Cliente("6","2","Flash","Gordon","1150384","Asuncion","0981644489"));

        mockCliente(db, new Cliente("7","3","Carlos","Valdez","5111246","Capiata","096580009"));
    }

    private void mockDataProducto(SQLiteDatabase db) {
        mockProducto(db, new Producto("1","Leche Entera","1 litro","5500","35","5"));
        mockProducto(db, new Producto("2","Leche Descremada","1 lt","5500","35","5"));
        mockProducto(db, new Producto("3","Coca Cola","1/2 lt","5000","30","5"));
        mockProducto(db, new Producto("4","Galletita Oreo","140 Gr","5000","40","5"));
        mockProducto(db, new Producto("5","Yogurt Lactopar","1/2 lt","4000","25","5"));
        mockProducto(db, new Producto("6","Hamburguesa FP","100 Gr","2000","155","30"));
        mockProducto(db, new Producto("7","Arveja Sur","350 Gr","3500","25","5"));
        mockProducto(db, new Producto("8","Boligrafo PF bic","-","2000","35","5"));
        mockProducto(db, new Producto("9","Jugo Ades","1 litro","7500","25","5"));
        mockProducto(db, new Producto("10","Dulce de Guayaba","1/4 Gr","3500","30","7"));
    }

    //PARA LA INSERCION DE LOS DATOS FICTICIOS
    private long mockUsuario(SQLiteDatabase db, Usuario usuario) {
        return db.insert(DataBaseManager.UsuarioEntry.TABLE_NAME, null,usuario.toContentValues());
    }

    private long mockVendedor(SQLiteDatabase db, Vendedor vendedor) {
        return db.insert(DataBaseManager.VendedorEntry.TABLE_NAME, null,vendedor.toContentValues());
    }

    private long mockCliente(SQLiteDatabase db, Cliente cliente) {
        return db.insert(DataBaseManager.ClienteEntry.TABLE_NAME, null,cliente.toContentValues());
    }

    private long mockProducto(SQLiteDatabase db, Producto producto) {
        return db.insert(DataBaseManager.ProductoEntry.TABLE_NAME, null,producto.toContentValues());
    }

    //COMPROBAR UN USUARIO


    //OBTENER TODOS LOS CLIENTES ORDENADOS
    public Cursor getClientes() {

        //String [] fields = new String[]{ClienteEntry.NOMBRE, ClienteEntry.APELLIDO};
        //String orderBy = ClienteEntry.NOMBRE;
        return getReadableDatabase().rawQuery("SELECT id AS _id, nombre || ', ' || apellido as nombre_apellido, " +
                                              "cedula ,direccion, telefono FROM Cliente ORDER by _id;", null);
    }

    public Cursor getProductos() {
        return getReadableDatabase()
                .query(
                        DataBaseManager.ProductoEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }
}
