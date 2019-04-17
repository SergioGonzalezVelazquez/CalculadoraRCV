package ipo2.es.calculadorarcv.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB_Helper extends SQLiteOpenHelper {


    /*Sentencia SQL para crear la tabla de Usuarios*/
    String sqlCrearTablaUsuario = "CREATE TABLE `Usuario` (\n" +
            "\t`idUser`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`email`\tTEXT UNIQUE,\n" +
            "\t`pass`\tTEXT NOT NULL,\n" +
            "\t`nombre`\tTEXT NOT NULL,\n" +
            "\t`apellidos`\tTEXT NOT NULL,\n" +
            "\t`genero`\tTEXT NOT NULL,\n" +
            "\t`fechaNacimiento`\tTEXT NOT NULL,\n" +
            "\t`ultimoAcceso`\tTEXT,\n" +
            "\t`foto`\tTEXT\n" +
            ");";

    /*Sentencia SQL para crear la tabla de Cálculos*/
    String sqlCrearTablaCalculo = "CREATE TABLE `CalculosRCV` (\n" +
            "\t`idCalculo`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`idUser`\tINTEGER NOT NULL,\n" +
            "\t`fumador`\tINTEGER NOT NULL,\n" +
            "\t`diabetes`\tINTEGER NOT NULL,\n" +
            "\t`hvi`\tINTEGER NOT NULL,\n" +
            "\t`hipertension`\tINTEGER NOT NULL,\n" +
            "\t`peso`\tREAL NOT NULL,\n" +
            "\t`altura`\tINTEGER NOT NULL,\n" +
            "\t`actividadFisica`\tINTEGER NOT NULL,\n" +
            "\t`tas`\tINTEGER NOT NULL,\n" +
            "\t`tad`\tINTEGER NOT NULL,\n" +
            "\t`colHDL`\tINTEGER NOT NULL,\n" +
            "\t`colTotal`\tINTEGER NOT NULL,\n" +
            "\t`fecha`\tTEXT,\n" +
            "\tFOREIGN KEY(`idUser`) REFERENCES Usuario\n" +
            ");";

    public DB_Helper(Context contexto, String nombreBD, SQLiteDatabase.CursorFactory factory, int versionBD) {
        super(contexto, nombreBD, factory, versionBD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            /*Se ejecuta la sentencia SQL de creación de la tabla*/
            db.execSQL(sqlCrearTablaUsuario);
            db.execSQL(sqlCrearTablaCalculo);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            /*Se elimina la versión anterior de la table*/
            db.execSQL("DROP TABLE IF EXISTS Usuario");
            db.execSQL("DROP TABLE IF EXISTS CalculosRCV");
            /*Se crea la nueva versión de la table*/
            db.execSQL(sqlCrearTablaUsuario);
            db.execSQL(sqlCrearTablaCalculo);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}