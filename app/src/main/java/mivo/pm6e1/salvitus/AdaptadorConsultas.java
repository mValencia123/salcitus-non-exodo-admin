package mivo.pm6e1.salvitus;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorConsultas extends RecyclerView.Adapter<AdaptadorConsultas.ViewHolderConsltas>
        implements View.OnClickListener {

    ArrayList<SetConsultas> ListaConsultas;
    private View.OnClickListener listener;

    public AdaptadorConsultas(ArrayList<SetConsultas> listaConsultas) {
        ListaConsultas = listaConsultas;
    }

    @NonNull
    @Override
    public ViewHolderConsltas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptadorconsultas, null, false);

        view.setOnClickListener(this);

        return new ViewHolderConsltas(view);
    }

    public void setOnClickListener(View.OnClickListener listener)
    {

        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderConsltas holder, int position) {
        holder.txtPet.setText("Cantidad de botellas de PET: "+Integer.toString(ListaConsultas.get(position).getPet()));
        holder.txtAgua.setText("Litros de agua: "+Integer.toString(ListaConsultas.get(position).getAgua()));
        holder.txtUsuarios.setText("Cantidad de usuarios: "+Integer.toString(ListaConsultas.get(position).getUsuarios()));
        holder.txtFecha.setText("Fecha: "+ListaConsultas.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return ListaConsultas.size()    ;
    }

    public class ViewHolderConsltas extends RecyclerView.ViewHolder {
        TextView txtPet, txtAgua, txtUsuarios, txtFecha;
        public ViewHolderConsltas(View itemView) {
            super(itemView);
            txtAgua= itemView.findViewById(R.id.txtAgua);
            txtPet= itemView.findViewById(R.id.txtPet);
            txtFecha= itemView.findViewById(R.id.txtFecha);
            txtUsuarios= itemView.findViewById(R.id.txtUsuarios);
        }
    }
}
