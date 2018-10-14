package com.cos.israelc.cosettur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.cos.israelc.cosettur.WebService;
import com.itextpdf.text.DocumentException;

public class horario extends AppCompatActivity {
    Button fin;
    String lu1,lu2,ma1,ma2,mie1,mie2,ju1,ju2,vie1,vie2, user;

    Spinner lunes1;
    Spinner lunes2;
    Spinner martes1;
    Spinner martes2;
    Spinner miercoles1;
    Spinner miercoles2;
    Spinner jueves1;
    Spinner jueves2;
    Spinner   viernes1;
    Spinner   viernes2;
    String[] grade1 = {"07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);
        fin=(Button)findViewById(R.id.siguiente);
        lunes1=(Spinner)findViewById(R.id.l1);
        lunes2=(Spinner)findViewById(R.id.l2);
        martes1=(Spinner)findViewById(R.id.m1);
        martes2=(Spinner)findViewById(R.id.m2);
        miercoles1=(Spinner)findViewById(R.id.mi1);
        miercoles2=(Spinner)findViewById(R.id.mi2);
        jueves1=(Spinner)findViewById(R.id.j1);
        jueves2=(Spinner)findViewById(R.id.j2);
        viernes1=(Spinner)findViewById(R.id.v1);
        viernes2=(Spinner)findViewById(R.id.v2);
        lunes1.setAdapter(new ArrayAdapter<String>(horario.this, android.R.layout.simple_spinner_item, grade1));
        lunes2.setAdapter(new ArrayAdapter<String>(horario.this, android.R.layout.simple_spinner_item, grade1));
        martes1.setAdapter(new ArrayAdapter<String>(horario.this, android.R.layout.simple_spinner_item, grade1));
        martes2.setAdapter(new ArrayAdapter<String>(horario.this, android.R.layout.simple_spinner_item, grade1));
        miercoles1.setAdapter(new ArrayAdapter<String>(horario.this, android.R.layout.simple_spinner_item, grade1));
        miercoles2.setAdapter(new ArrayAdapter<String>(horario.this, android.R.layout.simple_spinner_item, grade1));
        jueves1.setAdapter(new ArrayAdapter<String>(horario.this, android.R.layout.simple_spinner_item, grade1));
        jueves2.setAdapter(new ArrayAdapter<String>(horario.this, android.R.layout.simple_spinner_item, grade1));
        viernes1.setAdapter(new ArrayAdapter<String>(horario.this, android.R.layout.simple_spinner_item, grade1));
        viernes2.setAdapter(new ArrayAdapter<String>(horario.this, android.R.layout.simple_spinner_item, grade1));

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    create();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

    }
    public void create() throws DocumentException {
        String alumnos=getIntent().getStringExtra("alumn_name");
        String lu1=lunes1.getSelectedItem().toString();
        String lu2=lunes2.getSelectedItem().toString();
        String ma1=martes1.getSelectedItem().toString();
        String ma2=martes2.getSelectedItem().toString();
        String m11=miercoles1.getSelectedItem().toString();
        String mi2=miercoles2.getSelectedItem().toString();
        String je1=jueves1.getSelectedItem().toString();
        String je2=jueves2.getSelectedItem().toString();
        String vi1=viernes1.getSelectedItem().toString();
        String vi2=viernes2.getSelectedItem().toString();
        Toast.makeText(horario.this,"Guardando archivo", Toast.LENGTH_SHORT).show();

        Templatepdf tem=new Templatepdf(getApplicationContext());
        tem.createfile(alumnos+"horario");
        tem.opendocument();

        tem.addMetaData("COSSETTUR","Ficha de Inscripcion","Cosetturapps");
        tem.addtitle("Proveedor de Servicios Educativos\n" +
                "Transportación y Turismo","RFC COS 160907 JZ5","atencionunitec@cosettur.com\n" +
                "cosettur@yahoo.com.mx");
        tem.lines("Horario Sugerido");

        tem.addparagraph("Nombre del alumno:  "+alumnos);
        tem.addparagraph("                               Lunes  Martes  Miercoles  Juevez  Viernes");
        tem.addparagraph("Hora de Ascenso:    "+lu1+"     "+ma1+"     "+m11+"     "+je1+"     "+vi1 );
        tem.addparagraph("Hora de Descenso:   "+lu2+"     "+ma2+"     "+mi2+"     "+je2+"     "+vi2 );
        tem.closedocument();




    }

    public void datos(){

       lu1=lunes1.getSelectedItem().toString();
       lu2=lunes2.getSelectedItem().toString();
       ma1=martes1.getSelectedItem().toString();
       ma2=martes2.getSelectedItem().toString();
       mie1=miercoles1.getSelectedItem().toString();
       mie2=miercoles2.getSelectedItem().toString();
       ju1=jueves1.getSelectedItem().toString();
       ju2=jueves2.getSelectedItem().toString();
       vie1=viernes1.getSelectedItem().toString();
       vie2=viernes2.getSelectedItem().toString();
       String entradas[] = new String[5];
       String salidas[]= new String[5];
        user=getIntent().getStringExtra("user");

        entradas[0]=lu1;
        entradas[1]=ma1;
        entradas[2]=mie1;
        entradas[3]=ju1;
        entradas[4]=vie1;

        salidas[0]=lu2;
        salidas[1]=ma2;
        salidas[2]=mie2;
        salidas[3]=ju2;
        salidas[4]=vie2;

        WebService servicio= new WebService();


servicio.consumirWs(user,"","","","","","","","",entradas,salidas,"","");
    }

}
