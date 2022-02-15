package mivo.pm6e1.salvitus;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Usuario extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextInputEditText etEmail, etContra, etRepetir;
    Button btnEnviar;
    Button btnAyuda;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private OnFragmentInteractionListener mListener;
    private View mView;

    public Usuario() {
    }
    public static Usuario newInstance(String param1, String param2) {
        Usuario fragment = new Usuario();
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
        mView=  inflater.inflate(R.layout.fragment_usuario, container, false);
        btnAyuda= mView.findViewById(R.id.btnAyuda);
        progressDialog= new ProgressDialog(getContext());
        etEmail= mView.findViewById(R.id.etEmail);
        etContra= mView.findViewById(R.id.etContra);
        etRepetir= mView.findViewById(R.id.etConfirmacion);
        btnEnviar= mView.findViewById(R.id.btnEnviar);
        mAuth = FirebaseAuth.getInstance();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((etContra.getText().toString().equals("") ||
                        etContra.getText().toString().equals(null)) || (etEmail.getText().toString().equals("") ||
                        etEmail.getText().toString().equals(null)) || (etRepetir.getText().toString().equals("") ||
                        etRepetir.getText().toString().equals(null))) {
                    Toast.makeText(mView.getContext(), "Por favor llena todos los campos de textp.", Toast.LENGTH_SHORT).show();
                } else {
                    if ((etRepetir.getText().toString().equals(etContra.getText().toString()))) {
                        if(etContra.getText().toString().length()>=6) {
                            progressDialog.setMessage("Creando usuario...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etContra.getText().toString())
                                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                progressDialog.dismiss();
                                                Toast.makeText(mView.getContext(), "Se ha creado el usuario correctamente", Toast.LENGTH_LONG).show();
                                                etRepetir.setText("");
                                                etContra.setText("");
                                                etEmail.setText("");
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Log.w("", "createUserWithEmail:failure", task.getException());
                                                Toast.makeText(mView.getContext(), "Ocurrio un error, por favor ingresa un correo elecronico valido.",
                                                        Toast.LENGTH_LONG).show();
                                                progressDialog.dismiss();
                                            }
                                        }
                                    });
                        }else
                            Toast.makeText(mView.getContext(), "La contraseña debe tener minimo 6 caracteres.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mView.getContext(), "Las contraseñas son diferentes", Toast.LENGTH_LONG).show();
                    }
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
