package ipo2.es.calculadorarcv.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ConectorBD {

    static final String NOMBRE_BD = "rcv_app";
    private CalculoSQLiteHelper dbCalculoHelper;
    private UsuarioSQLiteHelper dbUsuarioHelper;
    private SQLiteDatabase db;
    /*Constructor*/
    public ConectorBD (Context ctx)
    {
        dbCalculoHelper = new CalculoSQLiteHelper(ctx, NOMBRE_BD, null, 1);
        dbUsuarioHelper = new UsuarioSQLiteHelper(ctx, NOMBRE_BD, null, 1);
    }
    /*Abre la conexión con la base de datos*/
    public ConectorBD abrir() throws SQLException
    {
        db = dbCalculoHelper.getWritableDatabase();
        return this;
    }
    /*Cierra la conexión con la base de datos*/
    public void cerrar()
    {
        if (db != null) db.close();
    }

    /*inserta un contacto en la BD*/
    public void insertarContacto(String nombre, String telefono)
    {
        String consultaSQL = "INSERT INTO contactos VALUES('"+nombre+"','"+telefono+"')";
        db.execSQL(consultaSQL);
    }
    /*devuelve todos los contactos*/
    public Cursor listarContactos()
    {
        return db.rawQuery("SELECT * FROM Contactos", null);
    }
}
