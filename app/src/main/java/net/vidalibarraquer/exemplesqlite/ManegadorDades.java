package net.vidalibarraquer.exemplesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ManegadorDades extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "parking.db";
    private static final String TABLE = "vehicles";

    private static final String ID = "id";
    private static final String NOM = "nom";
    private static final String COGNOMS = "cognoms";
    private static final String TELEFON = "telefon";
    private static final String MARCA = "marca";
    private static final String MODEL = "model";
    private static final String MATRICULA = "matricula";

    public ManegadorDades(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOM + " TEXT," +
                COGNOMS + " TEXT," +
                TELEFON + " TEXT," +
                MARCA + " TEXT," +
                MODEL + " TEXT," +
                MATRICULA + " TEXT UNIQUE)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public boolean addVehicle(Vehicle v) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOM, v.getNom());
        cv.put(COGNOMS, v.getCognoms());
        cv.put(TELEFON, v.getTelefon());
        cv.put(MARCA, v.getMarca());
        cv.put(MODEL, v.getModel());
        cv.put(MATRICULA, v.getMatricula());

        long result = db.insert(TABLE, null, cv);
        db.close();
        return result != -1;
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE, null);

        if (c.moveToFirst()) {
            do {
                list.add(new Vehicle(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6)
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public Vehicle getByMatricula(String matricula) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE,
                null,
                MATRICULA + "=?",
                new String[]{matricula},
                null, null, null);

        if (c != null && c.moveToFirst()) {
            Vehicle v = new Vehicle(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6)
            );
            c.close();
            return v;
        }
        return null;
    }

    public void deleteVehicle(Vehicle v) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, ID + "=?", new String[]{String.valueOf(v.getId())});
        db.close();
    }

    public int updateVehicle(Vehicle v) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NOM, v.getNom());
        cv.put(COGNOMS, v.getCognoms());
        cv.put(TELEFON, v.getTelefon());
        cv.put(MARCA, v.getMarca());
        cv.put(MODEL, v.getModel());
        cv.put(MATRICULA, v.getMatricula());

        int rows = db.update(TABLE, cv, ID + "=?", new String[]{String.valueOf(v.getId())});
        db.close();
        return rows;
    }
}
