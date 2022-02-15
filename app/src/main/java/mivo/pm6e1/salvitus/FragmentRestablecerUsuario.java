package mivo.pm6e1.salvitus;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class FragmentRestablecerUsuario extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View mView;
    private ProgressDialog progressDialog;
    private TextInputEditText etEmail;
    private Button btnEnviar;
    private Button btnAyuda;

    public FragmentRestablecerUsuario() {

    }

    public static FragmentRestablecerUsuario newInstance(String param1, String param2) {
        FragmentRestablecerUsuario fragment = new FragmentRestablecerUsuario();
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

        mView= inflater.inflate(R.layout.fragment_fragment_restablecer_usuario, container, false);
        progressDialog= new ProgressDialog(getContext());
        etEmail= mView.findViewById(R.id.etEmail);
        btnEnviar= mView.findViewById(R.id.btnEnviar);
        btnAyuda= mView.findViewById(R.id.btnAyuda);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().equals("")||etEmail.getText().toString().equals(null)) {
                    Toast.makeText(mView.getContext(),"Por favor llena todos los campos de texto.",Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String emailAddress = etEmail.getText().toString();

                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Nax", "Email sent.");
                                        Toast.makeText(mView.getContext(), "El email ha sido enviado exitosamente.", Toast.LENGTH_LONG).show();
                                        etEmail.setText("");
                                    }else{

                                        Toast.makeText(mView.getContext(), "Ocurrio un error, verifica que el correo este bien escrito.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Ayuda(getContext(),1);
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
