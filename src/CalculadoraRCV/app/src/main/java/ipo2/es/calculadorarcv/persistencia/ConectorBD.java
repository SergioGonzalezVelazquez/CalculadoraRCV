package ipo2.es.calculadorarcv.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import ipo2.es.calculadorarcv.dominio.Usuario;

public class ConectorBD {

    static final String NOMBRE_BD = "rcv_app";

    private DB_SQLiteHelper dbHelper;
    private SQLiteDatabase db;

    /*Constructor*/
    public ConectorBD (Context ctx)
    {
        dbHelper = new DB_SQLiteHelper(ctx, NOMBRE_BD, null, 1);
    }


    /*Abre la conexión con la base de datos*/
    public ConectorBD abrir() throws SQLException
    {
        db = dbHelper.getReadableDatabase();
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
    /*devuelve todos los Usuarios*/
    public Cursor listarUsuario()
    {
        return db.rawQuery("SELECT * FROM Usuario", null);
    }

    public int checkUser(Usuario user)
    {
        int id=-1;
        Cursor cursor=db.rawQuery("SELECT idUser FROM Usuario WHERE email=? AND pass=?",
                new String[]{user.getEmail(), user.getPass()});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            id=cursor.getInt(0);
            cursor.close();
        }
        return id;
    }

    public Usuario leerUsuario(int id){
        Usuario usuario = null;
        String[] campos = new String[] {"email","pass","nombre","apellidos","genero","fechaNacimiento","ultimoAcceso"};

        Cursor c = db.query("Usuario", campos, "idUser="+id, null, null, null,
                null);
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
        //Recorremos el cursor hasta que no haya más registros
            do {
                String email = c.getString(0);
                String pass = c.getString(1);
                String nombre = c.getString(2);
                String apellidos = c.getString(3);
                char genero = c.getString(4).charAt(0);;
                String fechaNacimiento = c.getString(5);
                String ultimoAcceso = c.getString(6);
                usuario = new Usuario(email, pass, nombre, apellidos, genero, fechaNacimiento, ultimoAcceso, "");
            } while(c.moveToNext());
        }

        return usuario;
    }
}

