package com.matisoft.tp6mdiaz_matias_nicolas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //solicito los permisos :
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1000);
        }
         intent =  new Intent(this, Servicio.class);
    }

    public void iniciarServicio(View view) {
        this.startService(intent);
        Toast.makeText(this, "Inicio del servicio", Toast.LENGTH_SHORT).show();
    }

    public void pararServicio(View view) {
        this.stopService(intent);
        Toast.makeText(this, "Fin del servicio", Toast.LENGTH_SHORT).show();
    }

}
    /*
    * Crear una aplicación que cuando se ejecute su actividad principal
    * , se inicie un servicio que cada 9000 milisegundos acceda al Content Provider
    *  de los mensajes y muestre por la consola de debug (Log.d) los datos más
    *  importantes de los últimos 5(cinco) mensajes recibidos. La vista asociada
    *  a la Activity Principal poseerá 2 botones, uno para iniciar el servicio
    *  y otro para finalizarlo.
*/

