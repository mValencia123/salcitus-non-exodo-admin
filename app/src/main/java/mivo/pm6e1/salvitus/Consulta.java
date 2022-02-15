package mivo.pm6e1.salvitus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Consulta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    RecyclerView recyclerView;
    ArrayList<SetConsultas> consultas;
    AdaptadorConsultas adaptadorConsultas;
    private Context context;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View mView;

    public Consulta() {
    }
    public static Consulta newInstance(String param1, String param2) {
        Consulta fragment = new Consulta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=  inflater.inflate(R.layout.fragment_consulta, container, false);

        Button btnAuda = mView.findViewById(R.id.btnAyuda);
        recyclerView= mView.findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        consultas= new ArrayList<>();
        adaptadorConsultas= new AdaptadorConsultas(consultas);
        recyclerView.setAdapter(adaptadorConsultas);

        FirebaseDatabase salvitus= FirebaseDatabase.getInstance();

        DatabaseReference salvitusmyRef = salvitus.getReference("General");

        btnAuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Ayuda(mView.getContext(),3);
            }
        });

        salvitusmyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                consultas.removeAll(consultas);

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    SetConsultas consulta = datasnapshot.getValue(SetConsultas.class);
                    consultas.add(consulta);
                }
                adaptadorConsultas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adaptadorConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context= v.getContext();
                Bundle bundle= new Bundle();
                //Toast.makeText(v.getContext(),"Click en: "+consultas.get(recyclerView.getChildAdapterPosition(v)),Toast.LENGTH_SHORT).show();
                bundle.putDouble("PET",consultas.get(recyclerView.getChildAdapterPosition(v)).getPet());
                bundle.putDouble("Agua",consultas.get(recyclerView.getChildAdapterPosition(v)).getUsuarios());
                Intent i= new Intent(context, ActivityCostros.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });


        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
