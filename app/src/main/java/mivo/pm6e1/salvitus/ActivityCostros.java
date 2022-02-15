package mivo.pm6e1.salvitus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityCostros extends AppCompatActivity {
    private double PET;
    private double agua;
    EditText etAgua, etPet;
    TextView txtResultado, txtPET, txtGarrafon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costros);
        etAgua= findViewById(R.id.etAgua);
        etPet= findViewById(R.id.etPET);
        txtResultado= findViewById(R.id.txtResultado);
        txtPET= findViewById(R.id.txtPET);
        txtGarrafon= findViewById(R.id.txtGarrafon);

        Bundle parametros = this.getIntent().getExtras();
        PET = parametros.getDouble("PET");
        agua = parametros.getDouble("Agua");
        //Toast.makeText(this,"Agua: "+agua+"\n"+"Pet: "+PET, Toast.LENGTH_LONG).show();
    }

    public void Generar(View v){
        String precioagua= etAgua.getText().toString();
        String preciopet = etPet.getText().toString();

        if(precioagua.equals("")||precioagua.equals(null)&&
                preciopet.equals("")||preciopet.equals(null)){
            Toast.makeText(this,"Debes ingresar datos en todos los campos de texto.",Toast.LENGTH_LONG).show();
        }else {
            double a = (agua / 20.0) * Double.parseDouble(precioagua);
            double p = ((PET * 12.0) / 1000.0) * Double.parseDouble(preciopet);
            txtGarrafon.setText("Gastaste " + agua + " Lts equivale a $" + Math.round(a * 100) / 100d);
            txtPET.setText("Obtuviste " + PET + " botellas de PET equivale a " + ((PET * 12.0) / 1000.0) + " Kg");
            txtResultado.setText("Gastaste de agua: " + "$" + Math.round(a * 100) / 100d + "\n" + "Ganaras con el PET: " + "$" + Math.round(p * 100) / 100d);
        }
    }

    public void salir(View v){
        //Intent i= new Intent (this,ResultadoActivity.class);
        //startActivity(i);
    }

    public void ayuda(View v){
        new Ayuda(this,2);
    }
}

