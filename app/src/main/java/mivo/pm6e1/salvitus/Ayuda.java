package mivo.pm6e1.salvitus;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Ayuda  {

    private TextView tv1, tv2, tv3;
    public Ayuda(final Context context, int codigo){

        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialogo.setContentView(R.layout.ayuda);

        tv1= dialogo.findViewById(R.id.tv1);
        tv2= dialogo.findViewById(R.id.tv2);
        tv3= dialogo.findViewById(R.id.tv3);

        switch (codigo){
            case 1:
                tv1.setText("1.- Debes ingresar los datos en los espacios de texto que le corresponde.");
                tv2.setText("2.- Da click sobre el boton de la parte inferior.");
                tv3.setText("3.- Los datos se habran actualizado exitosamente.");
                break;
            case 2:
                tv1.setText("1.- Debes ingresar los valores en los espacios de texto que le corresponde.");
                tv2.setText("2.- Da click sobre el boton de generar costos.");
                tv3.setText("3.- Los datos se mostraran en la parte media la ventana.");
                break;
            case 3:
                tv1.setText("1.- Cada dia se divide por una linea negra y cada apartado por una verde.");
                tv2.setText("2.- La lista muestra multiples datos y al dar cick te mandara a otra ventana.");
                tv3.setText("3.- Da click sobre el día que quieras ver.");
                break;
            case 4:
                tv1.setText("1.- Encontramos en la parte media de la ventana un recuadro negro.");
                tv2.setText("2.- Al dar click sobre él, nos despliega un menu de datos donde pregunta que parametros queremos consultar.");
                tv3.setText("3.- Ahora solo da click en consultar.");
                break;
        }
        dialogo.show();
    }
}

