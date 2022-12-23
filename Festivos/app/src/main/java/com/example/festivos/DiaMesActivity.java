package com.example.festivos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class DiaMesActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnVer;
    ListView lst;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_mes);
        btnVer=findViewById(R.id.btnVer);
        lst=findViewById(R.id.lstDiaMes);
        btnVer.setOnClickListener(this);
        db=Principal.moh.getReadableDatabase();
    }

    @Override
    public void onClick(View v) {
        if(v==btnVer) {
            Cursor c = db.rawQuery("SELECT dia as _id ,mes,m.motivo FROM " +
                    "dias AS d JOIN motivos AS m ON m.id=d.motivo ", null);
            DiaMesCursorAdapter dmca = new DiaMesCursorAdapter(this,c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            lst.setAdapter(dmca);
        }
    }
}