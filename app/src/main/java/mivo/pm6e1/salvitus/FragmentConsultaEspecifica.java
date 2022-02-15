package mivo.pm6e1.salvitus;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class FragmentConsultaEspecifica extends Fragment {

    private DatabaseReference SalvitusmyRef;
    public SetConsultas setConsultas;
    private FirebaseDatabase database;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View mView;
    Calendar dateAndTime = Calendar.getInstance();
    private int valor=1;
    private DatabaseReference SalvitusmyRef2;

    public FragmentConsultaEspecifica() {

    }

    public static FragmentConsultaEspecifica newInstance(String param1, String param2) {
        FragmentConsultaEspecifica fragment = new FragmentConsultaEspecifica();
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
        mView= inflater.inflate(R.layout.fragment_consulta_especifica, container, false);
        Button btnConsulta = mView.findViewById(R.id.btnConsulta);
        Button btnAyuda = mView.findViewById(R.id.btnAyuda);
        database = FirebaseDatabase.getInstance();
        SalvitusmyRef = database.getReference();
        SalvitusmyRef2= database.getReference("General");

        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (valor){
                    case 1:
                        consultaEspecifica("agua");
                        break;
                    case 2:
                        consultaEspecifica("pet");
                        break;
                    case 3:
                        consultaEspecifica("usuarios");
                        break;
                    case 4:
                        new DatePickerDialog(getContext(), d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH),
                                dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
                        break;
                }
            }
        });

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Ayuda(mView.getContext(),4);
            }
        });

        Spinner spinner1 = (Spinner) mView.findViewById(R.id.spiner1);
        String[] letra = {"Consultar agua","Consulta PET","Consulta usuarios", "Por fecha"};
        spinner1.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, letra));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                switch ((String) adapterView.getItemAtPosition(pos)){
                    case "Consultar agua":
                         valor= 1;
                        break;
                    case "Consulta PET":
                        valor=2;
                        break;
                    case "Consulta usuarios":
                        valor = 3;
                        break;
                    case "Por fecha":
                        valor=4;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });



        return mView;
    }

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

        void onFragmentInteraction(Uri uri);
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Query q = null;

            if((new Integer(monthOfYear).toString().length()<2)&&(new Integer(dayOfMonth).toString().length()<2)) {
                 q = SalvitusmyRef2.orderByChild("fecha").equalTo(year + "-" + 0 + (monthOfYear + 1) + "-" + 0 + dayOfMonth);
                Toast.makeText(getContext(), year + "-" + 0 + (monthOfYear + 1) + "-" + 0 + dayOfMonth, Toast.LENGTH_SHORT).show();
            }else if((new Integer(monthOfYear).toString().length()<2)&&(new Integer(dayOfMonth).toString().length()==2)){
                 q = SalvitusmyRef2.orderByChild("fecha").equalTo(year + "-" + 0 + (monthOfYear + 1) + "-" + dayOfMonth);
                Toast.makeText(getContext(), year + "-" + 0 + (monthOfYear + 1) + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }else if ((new Integer(monthOfYear).toString().length()==2)&&(new Integer(dayOfMonth).toString().length()<2)){
                 q = SalvitusmyRef2.orderByChild("fecha").equalTo(year + "-" + (monthOfYear + 1) + "-" + 0 + dayOfMonth);
                Toast.makeText(getContext(), year + "-" + (monthOfYear + 1) + "-" + 0 + dayOfMonth, Toast.LENGTH_SHORT).show();
            }else if ((new Integer(monthOfYear).toString().length()==2)&&(new Integer(dayOfMonth).toString().length()==2)) {
                 q = SalvitusmyRef2.orderByChild("fecha").equalTo(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                Toast.makeText(getContext(), year + "-" + (monthOfYear + 1) + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
           q.addListenerForSingleValueEvent(new ValueEventListener() {
               int cont=0;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        cont++;
                        Log.e("Valor de la ref", ""+dataSnapshot1.getValue());
                        setConsultas = dataSnapshot1.getValue(SetConsultas.class);
                    }
                    if(cont>=1){
                        new DialogoConsultaEspecifica(getContext(),setConsultas.getPet(),setConsultas.getAgua(),setConsultas.getUsuarios(),setConsultas.getFecha());
                    }else
                        Toast.makeText(getContext(),"No se encontraron datos con este criterio de busqueda.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    };

    private void consultaEspecifica(final String parametro){
        Log.e("prueba ", "Ya entro");
        Query q = SalvitusmyRef.child("General").orderByChild(parametro);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    switch (parametro){
                        case "agua":
                            SetConsultas setConsultas= datasnapshot.getValue(SetConsultas.class);
                            Log.e("Hola ", String.valueOf(setConsultas.getAgua()));
                            i=setConsultas.getAgua()+i;
                            break;
                        case "pet":
                            SetConsultas setConsultas2= datasnapshot.getValue(SetConsultas.class);
                            Log.e("Hola ", String.valueOf(setConsultas2.getPet()));
                            i=setConsultas2.getPet()+i;
                            break;
                        case "usuarios":
                            SetConsultas setConsultas3= datasnapshot.getValue(SetConsultas.class);
                            Log.e("Hola ", String.valueOf(setConsultas3.getUsuarios()));
                            i=setConsultas3.getUsuarios()+i;
                            break;
                    }

                }
                switch (parametro){
                    case "agua":
                        Toast.makeText(mView.getContext(),"Se ha gastado en todal "+i+" litro de agua",Toast.LENGTH_SHORT).show();
                        break;
                    case "pet":
                        Toast.makeText(mView.getContext(),"Se han producido "+i+" botellas de PET",Toast.LENGTH_SHORT).show();
                        break;
                    case "usuarios":
                        Toast.makeText(mView.getContext(),"El sistema ha sido utilizado por: "+i+" usuarios",Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
