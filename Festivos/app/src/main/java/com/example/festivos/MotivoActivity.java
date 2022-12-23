package com.example.festivos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class MotivoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s;
    ListView lst;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo);
        s=findViewById(R.id.spinner);
        lst=findViewById(R.id.listview);
        db=Principal.moh.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT motivo as _id FROM MOTIVOS",null);
        s.setAdapter(new SimpleCursorAdapter(this,R.layout.sp_linea_motivo,c,new String[]{"_id"}, new int[]{R.id.txtMotivo},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));
        s.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Cursor c=db.rawQuery("SELECT dia as _id ,mes,m.motivo FROM " +
                "dias AS d JOIN motivos AS m ON m.id=d.motivo " +
                "WHERE m.id=?",new String []{((Integer)(position+1)).toString()});
        DiaMesCursorAdapter dmca=new DiaMesCursorAdapter(this,c,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lst.setAdapter(dmca);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}