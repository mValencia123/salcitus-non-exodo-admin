package mivo.pm6e1.salvitus;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.NOTIFICATION_SERVICE;


public class Estado extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView txtPet, txtAgua;
    private Context context=getContext();

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View mView;

    public Estado() {
        // Required empty public constructor
    }
    public static Estado newInstance(String param1, String param2) {
        Estado fragment = new Estado();
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
        mView= inflater.inflate(R.layout.fragment_estado, container, false);

        txtAgua= mView.findViewById(R.id.txtAgua);
        txtPet= mView.findViewById(R.id.txtPet);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Agua = database.getReference("Agua");

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference Pet = database2.getReference("Pet");

        final Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        txtAgua.setText("El nivel de agua es correcto para trabajar!");
        txtPet.setText("El nivel de PET es correcto para trabajar!");


        Agua.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(int.class);
                if(value==1){
                    txtAgua.setText("El nivel de agua es incorrecto para trabajar!");
                    NotificationCompat.Builder builder= new NotificationCompat.Builder(getContext());
                    builder.setColor(Color.RED);
                    builder.setVibrate(new long[]{1000,1000, 1000, 1000});
                    builder.setAutoCancel(false);
                    builder.setSmallIcon(R.drawable.botella);
                    builder.setContentTitle("Falta agua!");
                    builder.setSound(defaultSound);
                    builder.setLights(Color.RED, 1, 0);
                    NotificationManager notificationManager =(NotificationManager)getContext().getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(1,builder.build());
                }else
                    txtAgua.setText("El nivel de agua es correcto para trabajar!");
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        Pet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value2 = dataSnapshot.getValue(int.class);
                if(value2==1){
                    txtPet.setText("El nivel de PET es incorrecto para trabajar!");
                    NotificationCompat.Builder builder2= new NotificationCompat.Builder(getContext());
                    builder2.setColor(Color.RED);
                    builder2.setVibrate(new long[]{1000,1000, 1000, 1000});
                    builder2.setAutoCancel(false);
                    builder2.setSound(defaultSound);
                    builder2.setSmallIcon(R.drawable.botella);
                    builder2.setLights(Color.RED, 1, 0);
                    builder2.setContentTitle("El PET esta lleno!");
                    NotificationManager notificationManager2 =(NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
                    notificationManager2.notify(1,builder2.build());
                }else
                    txtPet.setText("El nivel de PET es correcto para trabajar!");
            }
            @Override
            public void onCancelled(DatabaseError error) {
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
