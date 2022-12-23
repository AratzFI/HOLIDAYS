package com.example.festivos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Principal extends AppCompatActivity {
    public static MotivosOpenHelper moh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        moh=new MotivosOpenHelper(this,"DBFestivos",null,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch(item.getItemId())
        {
            case R.id.mnu_completo:
                //actividad calendario completo
                i=new Intent(this,DiaMesActivity.class);
                startActivity(i);
                break;
            case R.id.mnu_diames:
                //actividad calendario por dia y mes

                break;
            case R.id.mnu_motivo:
                //actividad por motivo
                i=new Intent(this,MotivoActivity.class);
                startActivity(i);
                break;
            default:
                //actividad mantenimiento.
                i=new Intent(this,MantenimientoActivity.class);
                startActivity(i);


        }
        return true;
    }
}