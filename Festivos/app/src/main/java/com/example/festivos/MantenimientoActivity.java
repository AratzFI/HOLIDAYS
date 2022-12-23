package com.example.festivos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MantenimientoActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner s;
    SQLiteDatabase db;
    EditText txtDia,txtMes;
    Button btnBuscar,btnAlta,btnBaja,btnModificar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento);
        s=findViewById(R.id.spMotivos);
        db=Principal.moh.getWritableDatabase();
        txtDia=findViewById(R.id.txtDia);
        txtMes=findViewById(R.id.txtMes);
        btnBuscar=findViewById(R.id.btnBuscar);
        btnAlta=findViewById(R.id.btnAlta);
        btnBaja=findViewById(R.id.btnBorrar);
        btnModificar=findViewById(R.id.btnModificar);
        btnBuscar.setOnClickListener(this);
        btnModificar.setOnClickListener(this);
        btnBaja.setOnClickListener(this);
        btnAlta.setOnClickListener(this);
        Cursor c= db.query("motivos",new String[]{"motivo as _id"},null,null,null,null,null);
        SimpleCursorAdapter sca=new SimpleCursorAdapter(this,R.layout.sp_linea_motivo,c,new String[]{"_id"},
                            new int []{R.id.txtMotivo},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        s.setAdapter(sca);
    }

    @Override
    public void onClick(View v) {
            if(v==btnBuscar)
            {
                Cursor c= db.rawQuery("SELECT motivo FROM dias WHERE dia=? AND mes=?",new String[]{txtDia.getText().toString(),txtMes.getText().toString()});
                txtMes.setEnabled(false);
                txtDia.setEnabled(false);
                if(c.moveToNext())
                {
                    btnModificar.setEnabled(true);
                    btnBaja.setEnabled(true);
                    btnBuscar.setEnabled(false);
                    btnAlta.setEnabled(false);
                    s.setSelection(c.getInt(0)-1);
                }
                else
                {
                    btnModificar.setEnabled(false);
                    btnBaja.setEnabled(false);
                    btnBuscar.setEnabled(false);
                    btnAlta.setEnabled(true);

                }

            }
            else {
                if (v == btnAlta) {
                    ContentValues cv = new ContentValues();
                    cv.put("dia", txtDia.getText().toString());
                    cv.put("mes", txtMes.getText().toString());
                    cv.put("motivo", s.getSelectedItemPosition() + 1);
                    db.insert("dias", null, cv);
                }
                else if(v==btnBaja)
                {
                    AlertDialog.Builder dlgb=new AlertDialog.Builder(this);
                    dlgb.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.delete("dias","dia=? and mes=?",new String[]{txtDia.getText().toString(),txtMes.getText().toString()});
                        }
                    });
                    dlgb.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MantenimientoActivity.this,"Se canceló el borrado",Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog dlg=dlgb.create();
                    dlg.setMessage("Se eliminará definitivamente el registro. ¿Está seguro?");
                    dlg.show();
                }
                else if(v==btnModificar)
                {
                    ContentValues cv=new ContentValues();
                    cv.put("motivo",s.getSelectedItemPosition()+1);
                    db.update("dias",cv,"dia=? and mes=?",new String[]{txtDia.getText().toString(),txtMes.getText().toString()});
                    Toast.makeText(this,"Registro modificado",Toast.LENGTH_LONG).show();
                }
                txtMes.setEnabled(true);
                txtDia.setEnabled(true);
                btnBuscar.setEnabled(true);
                btnModificar.setEnabled(false);
                btnBaja.setEnabled(false);
                btnAlta.setEnabled(false);
            }
    }
}