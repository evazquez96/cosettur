package com.example.israelc.cosettur;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.israelc.cosettur.helpers.Bitacora;
import com.example.israelc.cosettur.helpers.Dream;
import com.example.israelc.cosettur.helpers.Registro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class registro extends AppCompatActivity {
    Button inicio;
    EditText usuario;
    EditText nombre;
    EditText telefono;
    EditText correo;
    EditText contrasena;
    EditText confirmar;
    String resulting;
    String aa;
    String bb;
    String cc;
    String dd;
    String ee;


    // Metodo que queremos ejecutar en el servicio web
    //private static final String Metodo = "getRegistroBitacora";

    private static final String Metodo = "insertaUsuario";

    // Namespace definido en el servicio web
    //private static final String namespace = "http://app.mexamerik.com";
    private static final String namespace = "http://webservice.cosettur.com/";

    // namespace + metodo
    private static final String accionSoap = "http://webservice.cosettur.com/insertaUsuario";
    // Fichero de definicion del servcio web
    private static final String url = "http://node37874-env-3073930.jl.serv.net.mx/UserWS?wsdl";

    private SoapPrimitive resultado;
    public boolean consumirWS(){
        Boolean bandera=true;
        try {

            // Modelo el request
            SoapObject request = new SoapObject(namespace, Metodo);

            request.addProperty("user", aa);
            request.addProperty("nombre", bb);
            request.addProperty("pass", cc);
            request.addProperty("telefono", dd);
            request.addProperty("correo", ee);

            //request.addProperty("name", "MA0096");
            // String s=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date());

            //request.addProperty("date", s);
            //Log.e("Date", s);

            //request.addProperty("user_id", 2); // Paso parametros al WS

            // Modelo el Sobre
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            sobre.dotNet = false;

            sobre.setOutputSoapObject(request);

            // Modelo el transporte
            HttpTransportSE transporte = new HttpTransportSE(url);

            // Llamada
            transporte.call(accionSoap, sobre);

            // Resultado
            Log.e("Consumi", "Consumi el servicio correctamente");
            resultado= (SoapPrimitive) sobre.getResponse();
            Log.e("Consumi", "Consumi el servicio correctamente");



        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            bandera=false;
        }finally {

            return bandera;
            /*
             * El finally siempre se va a ejecutar, sin importar que se lanze
             * una exepction
             */
        }

    }

    class asyncBitacora extends AsyncTask<String,String,String> {


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if(consumirWS()){
                return "ok";
            }else
                return "error";
        }

        @Override
        protected void onPostExecute(String result){
            if(result.equals("ok")){
                Log.e("resultado",resultado.toString());
                resulting = resultado.toString();
                LlenarDatos();
            }else{
                Log.e("ERROR", "Error al consumir el webService");
            }
        }
    }

    public void LlenarDatos() {

        if (resulting.equals("1")) {

            Intent registro = new Intent(registro.this, MainActivity.class);
            startActivity(registro);
            Toast.makeText(registro.this, usuario.getText().toString() + " ha sido registrado correctaente", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(registro.this, "No te has podido registrar intentalo nuevamente", Toast.LENGTH_LONG).show();


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        inicio=(Button)findViewById(R.id.res);
        usuario = (EditText)findViewById(R.id.usuari);
        nombre = (EditText)findViewById(R.id.names);
        telefono = (EditText)findViewById(R.id.tel);
        correo = (EditText)findViewById(R.id.correo);
        contrasena = (EditText)findViewById(R.id.pass);
        confirmar = (EditText)findViewById(R.id.pass2);
usuario.setHint(" Nombre de Usuario*");
nombre.setHint(" Nombre Completo*");
telefono.setHint(" Telefono*");
correo.setHint(" Correo Electronico*");
contrasena.setHint(" Contraseña*");
confirmar.setHint(" Confirmar contraseña*");

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comprobando();

            }
        });
    }

    public void comprobando() {


        if (usuario.getText().toString().equals("")){
            usuario.setHint(" Campo obligatorio*");
        } else {
            if(nombre.getText().toString().equals("")){
                nombre.setHint(" Campo obligatorio*");
            } else {
                if (telefono.getText().toString().equals("")){
                    telefono.setHint(" Campo obligatorio*");
                } else {
                    if (correo.getText().toString().equals("")){
                        correo.setHint(" Campo obligatorio*");
                    } else {
                        if (contrasena.getText().toString().equals("")){
                            contrasena.setHint(" Campo obligatorio*");
                        } else {
                            if (confirmar.getText().toString().equals(contrasena.getText().toString())){

                                        aa = usuario.getText().toString();
                                        bb = nombre.getText().toString();
                                        cc = confirmar.getText().toString();
                                        dd = telefono.getText().toString();
                                        cc = correo.getText().toString();

                                        asyncBitacora v = new asyncBitacora();
                                        v.execute();

                            } else {
                                confirmar.setText("");
                                contrasena.setText("");
                                confirmar.setHint(" Las contraseñas no coinciden*");
                                contrasena.setHint(" Las contraseñas no coinciden*");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
