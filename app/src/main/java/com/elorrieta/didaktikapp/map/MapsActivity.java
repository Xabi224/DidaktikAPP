package com.elorrieta.didaktikapp.map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.databinding.ActivityMapsBinding;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("MissingPermission")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LatLng currentLocation;
    private GoogleMap mMap;
    private boolean freeMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.elorrieta.didaktikapp.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        // funcion de callback que se ejecuta cuando la localizacion cambia
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location lastLocation = locationResult.getLastLocation();
                assert lastLocation != null;
                currentLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                mMap.animateCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLng(currentLocation));
            }
        };

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Activamos el marcador de posicion actual, bloqueamos el arrastrar el mapa y activamos el zoom
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Funcion al pulsar sobre la posicion actual
        mMap.setOnMyLocationClickListener(location -> {
            if (freeMode){
                stopFreeMode();
            } else {
                promptFreeMode();
            }
        });

        // Añadimos los marcadores
        LatLng murala = new LatLng(43.315504984331355, -2.6800469989231344);
        LatLng bunker = new LatLng(43.313752475470565, -2.6790814036719923);
        LatLng zuhaitza = new LatLng(43.313350799395316, -2.6800029250450503);
        LatLng frontoia = new LatLng(43.317368972699555, -2.678771362726186);
        LatLng sanJuanIbarra = new LatLng(43.31717205318912, -2.6772421608758155);
        LatLng zubia = new LatLng(43.31747284222259, -2.675430714429948);
        LatLng auditorio = new LatLng(43.31344383698917, -2.678843086461924);

        mMap.addMarker(new MarkerOptions().position(murala).title("Gernikako bonbardaketa (Guernica murala)"));
        mMap.addMarker(new MarkerOptions().position(bunker).title("Pasealeku Bunkerra"));
        mMap.addMarker(new MarkerOptions().position(zuhaitza).title("Gernikako zuhaitza"));
        mMap.addMarker(new MarkerOptions().position(frontoia).title("Jai Alai frontoia"));
        mMap.addMarker(new MarkerOptions().position(sanJuanIbarra).title("Urriko azken astelehena (San Juan Ibarra plaza)"));
        mMap.addMarker(new MarkerOptions().position(zubia).title("Urdaibaiko Biosfera Erreserba (Errenteriako zubia)"));
        mMap.addMarker(new MarkerOptions().position(auditorio).title("Gatibu eta Ken Zazpi (Auditorio Seber Altube)"));

        // hacemos zoom
        mMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.zoomTo(18));

        // configuramos la localizacion
        createLocationRequest();
    }

    protected void createLocationRequest() {
        // inicializamosmos la peticion de localizacion
        locationRequest = new LocationRequest.Builder(1000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        // Recogemos la configuracion del usuario y la comparamos con la peticion de localizacion
        SettingsClient client = LocationServices.getSettingsClient(this);
        client.checkLocationSettings(builder.build()).addOnSuccessListener(e -> {
            // si la configuracion es correcta, empezamos a pedir la localizacion
            startLocationUpdates();
        }).addOnFailureListener(this, e -> {
            // Alguna condicion de localizacion no esta cumplida
            if (e instanceof ResolvableApiException) {
                try {
                    // Mostramos un dialogo para cambiar la configuracion y comprobamos la respuesta en onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(this, 1);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignoramos el error.
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // El usuario ha aceptado la configuracion de localizacion
                createLocationRequest();
            } else {
                // El usuario ha rechazado la configuracion de localizacion
                Toast.makeText(this, "Es necesario aceptar la configuracion", Toast.LENGTH_SHORT).show();
                createLocationRequest();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void promptFreeMode() {
        // Abrimos un prompt para preguntar la contraseña del modo sin GPS
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Modo libre");
        alert.setMessage("Introduce la contraseña para activar el modo libre");

        // Input para la contraseña
        final EditText input = new EditText(this);
        alert.setView(input);

        // Funcion del boton OK
        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            String value = input.getText().toString();
            if (value.equals("1234")) {
                // Si la contraseña es correcta, se activa el modo libre
                startFreeMode();
            } else {
                // Si la contraseña es incorrecta, se muestra un mensaje de error
                Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        });

        // Funcion del boton de cancelar
        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            // No hacemos nada
            dialog.dismiss();
        });

        alert.show();
    }

    private void startFreeMode(){
        freeMode = true;
        stopLocationUpdates();
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        Toast.makeText(this, "Modo libre activado", Toast.LENGTH_SHORT).show();
    }

    private void stopFreeMode(){
        freeMode = false;
        startLocationUpdates();
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        Toast.makeText(this, "Modo libre desactivado", Toast.LENGTH_SHORT).show();
    }

}