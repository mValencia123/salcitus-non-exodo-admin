package mivo.pm6e1.salvitus;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

class AdaptadorEncuesta extends RecyclerView.Adapter<AdaptadorEncuesta.ViewHolderEncuesta>
        implements View.OnClickListener {

    ArrayList<SetEncuesta> ListaEncuesta;
    private View.OnClickListener listener;

    public AdaptadorEncuesta(ArrayList<SetEncuesta> listaEncuesta) {
        ListaEncuesta = listaEncuesta;
    }

    @NonNull
    @Override
    public AdaptadorEncuesta.ViewHolderEncuesta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptadorencuesta, null, false);

        view.setOnClickListener(this);

        return new AdaptadorEncuesta.ViewHolderEncuesta(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEncuesta holder, int position) {
        holder.estrella1.setRating(ListaEncuesta.get(position).getEstrella1());
        holder.estrella2.setRating(ListaEncuesta.get(position).getEstrella2());
        holder.estrella3.setRating(ListaEncuesta.get(position).getEstrella3());
        holder.estrella4.setRating(ListaEncuesta.get(position).getEstrella4());
        holder.estrella5.setRating(ListaEncuesta.get(position).getEstrella5());
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
    public int getItemCount() {
        return ListaEncuesta.size()    ;
    }

    public class ViewHolderEncuesta extends RecyclerView.ViewHolder {
        private RatingBar estrella1, estrella2, estrella3, estrella4, estrella5;
        public ViewHolderEncuesta(View itemView) {
            super(itemView);
            estrella1= itemView.findViewById(R.id.estrella1);
            estrella2= itemView.findViewById(R.id.estrella2);
            estrella3= itemView.findViewById(R.id.estrella3);
            estrella4= itemView.findViewById(R.id.estrella4);
            estrella5= itemView.findViewById(R.id.estrella5);

            estrella1.setEnabled(false);
            estrella2.setEnabled(false);
            estrella3.setEnabled(false);
            estrella4.setEnabled(false);
            estrella5.setEnabled(false);
        }
    }
}
