package com.example.tp06hiloslp;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

public class MiServicioContent extends Service {

    private Thread hilo;
    private boolean flag=true;
    private int contador=0;
    public MiServicioContent() {
    }



    @Override
    public void onCreate() {
        super.onCreate();
        acceder();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // acceder();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag=false;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
    public void acceder() {
        // borrar el context
        //content://sms/inbox
        Uri llamadas = Uri.parse("content://sms/inbox");
        ContentResolver cr = this.getContentResolver();
        //esta parte va con el hilo

        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (flag) {

                        //Cursor cursor = cr.query(llamadas, null, null, null, null);
                        Cursor cursor = cr.query(llamadas, null, null, null, "date DESC LIMIT 5");
                        String fechaMensaje = null;
                        String textoMensaje = null;
                        String contactoMensaje = null;
                        //es un string que puede mutar, el stringbuffer es igual pero con os metodos sincronizados
                        StringBuilder resultado = new StringBuilder();
                        if (cursor.getCount() > 0) {

                            while (cursor.moveToNext()) {

                                int fecha = cursor.getColumnIndex(Telephony.Sms.DATE);
                                int contacto = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
                                int mensaje = cursor.getColumnIndex(Telephony.Sms.BODY);

                                fechaMensaje = cursor.getString(fecha);
                                textoMensaje = cursor.getString(mensaje);
                                contactoMensaje = cursor.getString(contacto);
                                resultado.append("fecha" + fechaMensaje + " contacto" + contactoMensaje + " mensaje " + textoMensaje + "\n");

                            }
                        }
                        Log.d("salida", resultado.toString());


                        Log.d("Contador", "numero : " + contador);
                        contador++;
                        Thread.sleep(5000);
                    }

                } catch (Exception ex) {

                }
            }
        });
        hilo.start();

    }
}