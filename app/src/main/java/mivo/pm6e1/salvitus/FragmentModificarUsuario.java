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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentModificarUsuario extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button btnEnviar;
    private TextInputEditText etEmail, etContra;
    private ProgressDialog progressDialog;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View mView;
    private TextInputEditText etNueva;
    private Button btnAyuda;

    public FragmentModificarUsuario() {


    }

    public static FragmentModificarUsuario newInstance(String param1, String param2) {
        FragmentModificarUsuario fragment = new FragmentModificarUsuario();
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

        mView= inflater.inflate(R.layout.fragment_fragment_modificar_usuario, container, false);
        progressDialog= new ProgressDialog(getContext());

        etEmail= mView.findViewById(R.id.etEmail);
        etContra= mView.findViewById(R.id.etContra);
        etNueva= mView.findViewById(R.id.etNueva);

        btnEnviar= mView.findViewById(R.id.btnEnviar);
        btnAyuda= mView.findViewById(R.id.btnAyuda);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((etContra.getText().toString().equals("")||
                        etContra.getText().toString().equals(null))||(etEmail.getText().toString().equals("")||
                        etEmail.getText().toString().equals(null))||(etNueva.getText().toString().equals("")||
                etNueva.getText().toString().equals(null))) {
                    Toast.makeText(mView.getContext(),"Por favor llena todos los campos de texto.",Toast.LENGTH_SHORT).show();
                }else{
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    AuthCredential credential = EmailAuthProvider.getCredential(etEmail.getText().toString(), etContra.getText().toString());
                    progressDialog.setMessage("Modificando usuario...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    user.updatePassword(etNueva.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getContext(), "Se ha modificado el usuario exitosamente", Toast.LENGTH_LONG).show();
                                                        Log.d("e", "Usuario modificado");
                                                        etContra.setText("");
                                                        etEmail.setText("");
                                                        etNueva.setText("");
                                                    }else {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(mView.getContext(), "Ocurrio un error, verifica que el correo electronico o la contraseña esten bien escritos.", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
