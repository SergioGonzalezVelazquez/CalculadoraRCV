
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
