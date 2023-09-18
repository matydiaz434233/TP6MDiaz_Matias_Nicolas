package com.matisoft.tp6mdiaz_matias_nicolas;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Servicio extends Service {
    private Thread hilo;
    private Boolean bandera = true;
    private Handler handler;
    private int contadorMensajes = 0;

    public Servicio() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        iniciarAccesoPeriodico();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bandera = false;
        handler.removeCallbacksAndMessages(null); // Detiene las ejecuciones programadas del Runnable
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void iniciarAccesoPeriodico() {
        // Programa la ejecución del Runnable cada 9000 milisegundos (9 segundos)
        handler.postDelayed(accionesPeriodicas, 9000);
    }

    private Runnable accionesPeriodicas = new Runnable() {
        @Override
        public void run() {
            acceder();
            // Programa la siguiente ejecución del Runnable después de 9000 milisegundos
            handler.postDelayed(this, 9000);
        }
    };

    public void acceder() {
        try {
            Uri mensajes = Uri.parse("content://sms/inbox");
            ContentResolver cr = this.getContentResolver();
            Cursor cursor = cr.query(mensajes, null, null, null, "date DESC LIMIT 5");

            if (cursor != null && cursor.moveToFirst()) {
                int fechaIndex = cursor.getColumnIndex("date");
                int numeroIndex = cursor.getColumnIndex("address");
                int cuerpoIndex = cursor.getColumnIndex("body");

                do {
                    String fecha = cursor.getString(fechaIndex);
                    String numero = cursor.getString(numeroIndex);
                    String cuerpo = cursor.getString(cuerpoIndex);

                    // Muestra los datos en la consola
                    Log.d("Mensaje SMS", "fecha : " + fecha + " Número: " + numero + ", Cuerpo: " + cuerpo);

                    // Incrementa el contador de mensajes
                    contadorMensajes++;

                } while (cursor.moveToNext() && contadorMensajes < 5); //logica de los ultimos 5 sms

                cursor.close();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Hubo un fallo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
