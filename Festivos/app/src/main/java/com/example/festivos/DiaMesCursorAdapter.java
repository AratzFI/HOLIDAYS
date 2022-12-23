package com.example.festivos;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DiaMesCursorAdapter extends CursorAdapter {
    TextView txtColumna;
    public DiaMesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v=((AppCompatActivity)context).getLayoutInflater().inflate(R.layout.dia_mes_linea,null);
        txtColumna=v.findViewById(R.id.txtColumna);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor c) {
            String columna= String.format("%d/%d (%s)",c.getInt(0),c.getInt(1),c.getString(2));
            txtColumna.setText(columna);
    }
}
