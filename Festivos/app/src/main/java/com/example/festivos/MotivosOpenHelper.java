package com.example.festivos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MotivosOpenHelper extends SQLiteOpenHelper {
    String []motivos;
    String DBDias="CREATE TABLE dias(dia INTEGER,mes INTEGER,motivo INTEGER, PRIMARY KEY(dia,mes),FOREIGN KEY (motivo) REFERENCES motivos(id))";
    String DBMotivos="CREATE TABLE motivos(id INTEGER, motivo TEXT)";
    String TrNewDiasMotivos="CREATE TRIGGER IF NOT EXISTS insertar_motivo BEFORE INSERT ON dias " +
                          "FOR EACH ROW " +
                            "BEGIN " +
                              "SELECT RAISE(ABORT,'El motivo no existe o es nulo') WHERE new.motivo IS NULL OR " +
                              "NOT EXISTS (SELECT id FROM motivos WHERE id=new.motivo);" +
                            "END";

    String TrUpdDiasMotivos="CREATE TRIGGER IF NOT EXISTS modificar_motivo  BEFORE UPDATE OF motivo ON dias " +
            "FOR EACH ROW " +
            "BEGIN " +
            "SELECT RAISE(ABORT,'El motivo no existe o es nulo') WHERE new.motivo IS NULL OR " +
            "NOT EXISTS (SELECT id FROM motivos WHERE id=new.motivo); " +
            "END";

    public MotivosOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        motivos=((Principal)context).getResources().getStringArray(R.array.motivos);
    }
    void inicializarMotivos(SQLiteDatabase db)
    {
           for (int i=0;i<motivos.length;i++)
           {
               ContentValues cv=new ContentValues();
               cv.put("id",i+1);
               cv.put("motivo",motivos[i]);
               db.insert("motivos",null,cv);
           }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBMotivos);
            db.execSQL(DBDias);
            db.execSQL(TrNewDiasMotivos);
            db.execSQL(TrUpdDiasMotivos);
            inicializarMotivos(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS dias");
            db.execSQL("DROP TABLE IF EXISTS motivos");
            db.execSQL(DBMotivos);
            db.execSQL(DBDias);
            inicializarMotivos(db);
    }
}
