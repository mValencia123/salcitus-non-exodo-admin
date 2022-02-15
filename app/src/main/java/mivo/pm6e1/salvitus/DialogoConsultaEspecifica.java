package mivo.pm6e1.salvitus;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DialogoConsultaEspecifica {
        public TextView tvAgua, tvPet, tvFecha, tvUsuarios;

    public DialogoConsultaEspecifica(final Context context, int pet, int agua, int usuarios, String fecha){
        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialogo.setContentView(R.layout.fragment_dialogo_consulta);

        tvAgua= dialogo.findViewById(R.id.tvAgua);
        tvPet= dialogo.findViewById(R.id.tvPet);
        tvUsuarios= dialogo.findViewById(R.id.tvUuarios);
        tvFecha= dialogo.findViewById(R.id.tvFecha);

        tvPet.setText("Botellas: "+pet);
        tvAgua.setText("Litros: "+agua);
        tvUsuarios.setText("Usuarios: "+usuarios);
        tvFecha.setText("Fecha: "+fecha);

        dialogo.show();
    }
}
