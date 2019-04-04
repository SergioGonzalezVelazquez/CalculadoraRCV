package ipo2.es.calculadorarcv.persistencia;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalculoSQLiteHelper extends SQLiteOpenHelper {
    /*Sentencia SQL para crear la tabla de Contactos*/
    String sqlCrearTabla = "CREATE TABLE Contactos(nombre TEXT, telefono TEXT)";
    public CalculoSQLiteHelper(Context contexto, String nombreBD, SQLiteDatabase.CursorFactory factory, int versionBD) {
        super(contexto, nombreBD, factory, versionBD);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            /*Se ejecuta la sentencia SQL de creación de la tabla*/
            db.execSQL(sqlCrearTabla);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
    /*NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de eliminar la tabla anterior
    y crearla de nuevo vacía con el nuevo formato. Sin embargo lo normal será que haya que migrar datos de
    la tabla antigua a la nueva, por lo que este método debería ser más elaborado.*/
        try {
            /*Se elimina la versión anterior de la table*/
            db.execSQL("DROP TABLE IF EXISTS Contactos");
            /*Se crea la nueva versión de la table*/
            db.execSQL(sqlCrearTabla);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
