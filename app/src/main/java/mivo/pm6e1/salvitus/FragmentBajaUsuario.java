package mivo.pm6e1.salvitus;

import android.app.AlertDialog;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FragmentBajaUsuario extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnAyuda;

    private OnFragmentInteractionListener mListener;
    private View mView;
    private Button btnEnviar;
    private TextInputEditText etEmail, etContra;
    private ProgressDialog progressDialog;

    public FragmentBajaUsuario() {

    }

    public static FragmentBajaUsuario newInstance(String param1, String param2) {
        FragmentBajaUsuario fragment = new FragmentBajaUsuario();
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

        mView= inflater.inflate(R.layout.fragment_baja_usuario, container, false);
        progressDialog= new ProgressDialog(getContext());
        etEmail= mView.findViewById(R.id.etEmail);
        etContra= mView.findViewById(R.id.etContra);
        btnEnviar= mView.findViewById(R.id.btnEnviar);
        btnAyuda= mView.findViewById(R.id.btnAyuda);


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().equals(null) || etEmail.getText().toString().equals("") ||
                        etContra.getText().toString().equals(null) || etContra.getText().toString().equals("")) {
                        Toast.makeText(mView.getContext(),"Llena todos los campos de texto, por favor.",Toast.LENGTH_LONG).show();
                } else {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(etEmail.getText().toString(), etContra.getText().toString());
                    Log.e("MIRA*************",""+credential);
                    //Toast.makeText(getContext(), ""+etEmail.getText().toString()+" "+etContra.getText().toString(), Toast.LENGTH_LONG).show();
                    progressDialog.setMessage("Eliminando usuario...");
                    //progressDialog.setCancelable(false);
                    progressDialog.show();
                    Log.e("Her mira", "" + credential);

                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    user.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getContext(), "Se ha eliminado el usuario exitosamente", Toast.LENGTH_LONG).show();
                                                        Log.d("e", "User account deleted.");
                                                        etContra.setText("");
                                                        etEmail.setText("");
                                                    }
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(mView.getContext(), "Revisa que los datos sean correctos.", Toast.LENGTH_LONG).show();
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
