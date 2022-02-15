package mivo.pm6e1.salvitus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentEncuesta extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View mView;

    RecyclerView recyclerView;
    ArrayList<SetEncuesta> encuesta;
    AdaptadorEncuesta adaptadorEncuesta;

    public FragmentEncuesta() {

    }

    public static FragmentEncuesta newInstance(String param1, String param2) {
        FragmentEncuesta fragment = new FragmentEncuesta();
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

        mView= inflater.inflate(R.layout.fragment_encuesta, container, false);

        recyclerView= mView.findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        encuesta= new ArrayList<>();
        adaptadorEncuesta= new AdaptadorEncuesta(encuesta);
        recyclerView.setAdapter(adaptadorEncuesta);

        FirebaseDatabase salvitus= FirebaseDatabase.getInstance();

        DatabaseReference salvitusmyRef = salvitus.getReference("Encuesta");

        salvitusmyRef.child("Datos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                encuesta.removeAll(encuesta);

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    SetEncuesta encuestas = datasnapshot.getValue(SetEncuesta.class);
                    encuesta.add(encuestas);
                }
                adaptadorEncuesta.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
