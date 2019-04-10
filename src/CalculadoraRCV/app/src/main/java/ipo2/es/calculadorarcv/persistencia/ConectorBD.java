package ipo2.es.calculadorarcv.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import ipo2.es.calculadorarcv.dominio.CalculoRCV;
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
        String[] campos = new String[] {"email","pass","nombre","apellidos","genero",
                "fechaNacimiento","ultimoAcceso","foto"};

        Cursor c = db.query("Usuario", campos, "idUser="+id, null,
                null, null, null);
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
                String foto = c.getString(7);
                usuario = new Usuario(id, email, pass, nombre, apellidos, genero, fechaNacimiento,
                        ultimoAcceso, "");
            } while(c.moveToNext());
        }

        usuario.setCalculosRCV(leerCalculos(usuario));

        return usuario;
    }

    private ArrayList<CalculoRCV> leerCalculos(Usuario usuario){
        ArrayList<CalculoRCV> calculoRCVS = new ArrayList<CalculoRCV>();
        String[] campos = new String[] {"fumador","diabetes","hvi","hipertension","peso","altura",
                     "actividadFisica", "tas", "tad", "colHDL", "colTotal", "fecha"};
        int idUser = usuario.getIdUser();

        Cursor c = db.query("CalculosRCV", campos, "idUser="+idUser, null,
                null, null, "fecha");

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                boolean fumador = false,diabetes = false, hvi = false,hipertension = false;
                int altura,actividadFisica,tas,tad,colHDL,colTotal;
                double peso;
                String fecha;
                if (c.getInt(0)==1) fumador=true;
                if (c.getInt(1)==1) diabetes=true;
                if (c.getInt(2)==1) hvi=true;
                if (c.getInt(3)==1) hipertension=true;
                peso = c.getDouble(4);
                altura = c.getInt(5);
                actividadFisica = c.getInt(6);
                tas = c.getInt(7);
                tad = c.getInt(8);
                colHDL = c.getInt(9);
                colTotal = c.getInt(10);
                fecha= c.getString(11);
                CalculoRCV calculo = new CalculoRCV(usuario, fumador, diabetes, hvi, hipertension, peso,
                        altura,  actividadFisica, tad,  tas,  colHDL,
                        colTotal, fecha);
                calculoRCVS.add(calculo);
                Log.d("Debug_BBDD","CalculosRCV leído:"+calculo.toString());
            } while(c.moveToNext());
        }

        return calculoRCVS;
    }
}

