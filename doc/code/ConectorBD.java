package ipo2.es.calculadorarcv.persistencia;

import android.content.ContentValues;
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

    static final String NOMBRE_BD = "RCV_BBDD";
    private DB_Helper dbHelper;
    private SQLiteDatabase db;

    //private DB_SQLiteHelper dbHelper;

    /*Constructor*/
    public ConectorBD (Context ctx)
    {
        dbHelper = new DB_Helper(ctx, NOMBRE_BD, null, 1);
    }


    /*Abre la conexión con la base de datos*/
    public ConectorBD abrir() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }


    /*Cierra la conexión con la base de datos*/
    public void cerrar()
    {
        if (db != null) db.close();
    }


    /*inserta un usuario en la BD*/
    public void insertarUsuario(Usuario usuario)
    {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", usuario.getEmail());
        values.put("pass", usuario.getPass());
        values.put("nombre", usuario.getNombre());
        values.put("apellidos", usuario.getApellidos());
        values.put("genero",Character.toString(usuario.getGenero()));
        values.put("fechaNacimiento", usuario.getFechaNacimiento());
        values.put("ultimoAcceso", usuario.getUltimoAcceso());
        values.put("foto",usuario.getFoto());

        // Inserting Row
        db.insert("Usuario", null, values);
        db.close();
    }

    /*inserta un usuario en la BD*/
    public void insertarCalculo(CalculoRCV calculo)
    {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idUser", calculo.getIdUser());
        values.put("fumador", calculo.getFumadorInt());
        values.put("diabetes", calculo.getDiabetesInt());
        values.put("hvi", calculo.getHviInt());
        values.put("hipertension",calculo.getHipertensionInt());
        values.put("peso", calculo.getPeso());
        values.put("altura", calculo.getAltura());
        values.put("actividadFisica", calculo.getActividadFisica());
        values.put("tas",calculo.getTensionSiastolica());
        values.put("tad",calculo.getTensionDiastolica());
        values.put("colHDL",calculo.getColHDL());
        values.put("colTotal",calculo.getColTotal());
        values.put("fecha",calculo.getFecha());


        // Inserting Row
        db.insert("CalculosRCV", null, values);
        db.close();
    }


    /*devuelve todos los Usuarios*/
    public Cursor listarUsuario()
    {
        return db.rawQuery("SELECT * FROM Usuario", null);
    }


    public int checkUser(String email, String pass)
    {
        db = dbHelper.getReadableDatabase();
        int id=-1;
        Cursor cursor=db.rawQuery("SELECT idUser FROM Usuario WHERE email=? AND pass=?",
                new String[]{email, pass});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            id=cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return id;
    }

    public int checkUser(String email)
    {
        db = dbHelper.getReadableDatabase();
        int id=-1;
        Cursor cursor=db.rawQuery("SELECT idUser FROM Usuario WHERE email=?",
                new String[]{email});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            id=cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return id;
    }

    public Usuario leerUsuario(int id){
        db = dbHelper.getReadableDatabase();
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
        db.close();

        return usuario;
    }

    private ArrayList<CalculoRCV> leerCalculos(Usuario usuario){
        db = dbHelper.getReadableDatabase();
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
        db.close();
        return calculoRCVS;
    }
}

