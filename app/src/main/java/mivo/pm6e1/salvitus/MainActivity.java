package mivo.pm6e1.salvitus;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Usuario.OnFragmentInteractionListener,Consulta.OnFragmentInteractionListener,
        Estado.OnFragmentInteractionListener, FragmentModificarUsuario.OnFragmentInteractionListener,
            FragmentRestablecerUsuario.OnFragmentInteractionListener, FragmentBajaUsuario.OnFragmentInteractionListener, FragmentEncuesta.OnFragmentInteractionListener,
FragmentConsultaEspecifica.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Agua = database.getReference("Estado");

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference Pet = database2.getReference("Estado");

        final Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Agua.child("Agua").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(int.class);
                if(value==1){
                    NotificationCompat.Builder builder= new NotificationCompat.Builder(getApplicationContext());
                    builder.setColor(Color.RED);
                    builder.setVibrate(new long[]{1000,1000, 1000, 1000});
                    builder.setAutoCancel(false);
                    builder.setSmallIcon(R.drawable.botella);
                    builder.setContentTitle("Falta agua!");
                    builder.setSound(defaultSound);
                    builder.setLights(Color.RED, 1, 0);
                    NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(1,builder.build());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        Pet.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value2 = dataSnapshot.getValue(int.class);
                if(value2==1){
                    //txtPet.setText("El nivel de PET es incorrecto para trabajar!");
                    NotificationCompat.Builder builder2= new NotificationCompat.Builder(getApplicationContext());
                    builder2.setColor(Color.RED);
                    builder2.setVibrate(new long[]{1000,1000, 1000, 1000});
                    builder2.setAutoCancel(false);
                    builder2.setSound(defaultSound);
                    builder2.setSmallIcon(R.drawable.botella);
                    builder2.setLights(Color.RED, 1, 0);
                    builder2.setContentTitle("El PET esta lleno!");
                    NotificationManager notificationManager2 =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    notificationManager2.notify(1,builder2.build());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment= null;
        Boolean transacion=false;
        if (id == R.id.Usuario) {
            fragment = new Usuario();
            transacion = true;
        } else if (id == R.id.Consulta) {
            fragment = new Consulta();
            transacion = true;
        } else if (id == R.id.Estado) {
            fragment = new Estado();
            transacion = true;
        } else if (id == R.id.BajaUsuario) {
            fragment = new FragmentBajaUsuario();
            transacion = true;
        } else if (id == R.id.ModificarUsuario) {
            fragment = new FragmentModificarUsuario();
            transacion = true;
        } else if (id == R.id.RestablecerUsuario) {
            fragment = new FragmentRestablecerUsuario();
            transacion = true;
        }else if (id == R.id.Encuesta) {
            fragment = new FragmentEncuesta();
            transacion = true;
        }else if (id == R.id.EncuestaEspecifica) {
                fragment = new FragmentConsultaEspecifica();
                transacion = true;
        }

        if(transacion) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
