package com.elorrieta.didaktikapp.map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.elorrieta.didaktikapp.DescriptionActivity;
import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.databinding.ActivityMapsBinding;
import com.elorrieta.didaktikapp.model.database.AppDatabase;
import com.elorrieta.didaktikapp.model.entities.PlaceOfInterest;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Objects;

@SuppressLint("MissingPermission")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private SharedPreferences preferences;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LatLng currentLocation;
    private GoogleMap mMap;
    private final HashMap<String, PlaceOfInterest> placesMap = new HashMap<>();
    private Button navigateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // configuramos la localizacion
        createLocationRequest();

        for (PlaceOfInterest poi : AppDatabase.getDatabase(getApplicationContext()).placeOfInterestDao().getAll()) {
            placesMap.put(poi.name, poi);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        navigateButton = binding.navigateButton;

        // funcion de callback que se ejecuta cuando la localizacion cambia
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location lastLocation = locationResult.getLastLocation();
                assert lastLocation != null;
                currentLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));
                checkDistanceWithMarkers();
            }
        };

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // hacemos zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.31543429260586, -2.6777311296364155), 16));

        // Activamos el marcador de posicion actual, bloqueamos el arrastrar el mapa y activamos el zoom
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Funcion al pulsar sobre la posicion actual
        mMap.setOnMyLocationClickListener(location -> {
            if (preferences.getBoolean("freeMode", false)) {
                stopFreeMode();
            } else {
                promptFreeMode();
            }
        });

        // A??adimos los marcadores
        for (PlaceOfInterest poi : placesMap.values()) {
            mMap.addMarker(new MarkerOptions()
                    .position(poi.getLatLng())
                    .title(poi.name));
        }

        // Funcion para mostrar el boton de navegacion si estamos en modo libre
        mMap.setOnMarkerClickListener(marker -> {
            if (preferences.getBoolean("freeMode", false)) {
                navigateButton.setVisibility(View.GONE);
                showButton(Objects.requireNonNull(placesMap.get(marker.getTitle())));
            } else {
                marker.showInfoWindow();
            }
            // Devolvemos true para que no se mueva la camara
            return true;
        });

        // si estabamos en modo libre, volvemos a activarlo
        if (preferences.getBoolean("freeMode", false)) startFreeMode();

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
                Toast.makeText(this, "Konfigurazioa onartu behar da", Toast.LENGTH_SHORT).show();
                createLocationRequest();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!preferences.getBoolean("freeMode", false)) startLocationUpdates();
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
        // Abrimos un prompt para preguntar la contrase??a del modo sin GPS
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Modu librea");
        alert.setMessage("Sartu pasahitza modu librea aktibatzeko");

        // Input para la contrase??a
        final EditText input = new EditText(this);
        alert.setView(input);

        // Funcion del boton OK
        alert.setPositiveButton("Baieztatu", (dialog, whichButton) -> {
            String value = input.getText().toString();
            if (value.equals("1234")) {
                // Si la contrase??a es correcta, se activa el modo libre
                startFreeMode();
            } else {
                // Si la contrase??a es incorrecta, se muestra un mensaje de error
                Toast.makeText(getApplicationContext(), "Pasahitza ez da zuzena", Toast.LENGTH_SHORT).show();
            }
        });

        // Funcion del boton de cancelar
        alert.setNegativeButton("Ezeztatu", (dialog, whichButton) -> {
            // No hacemos nada
            dialog.dismiss();
        });

        alert.show();
    }

    private void startFreeMode() {
        preferences.edit().putBoolean("freeMode", true).apply();
        stopLocationUpdates();
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.31543429260586, -2.6777311296364155), 16));
    }

    private void stopFreeMode() {
        preferences.edit().putBoolean("freeMode", false).apply();
        startLocationUpdates();
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    private void checkDistanceWithMarkers() {
        // Comprobamos si estamos cerca de algun marcador
        for (PlaceOfInterest poi : placesMap.values()) {
            float[] results = new float[1];
            Location.distanceBetween(poi.latitude, poi.longitude, currentLocation.latitude, currentLocation.longitude, results);
            if (results[0] < 20) {
                showButton(poi);
                return;
            }
        }
        // Si no estamos cerca de ningun marcador, ocultamos el boton
        navigateButton.setVisibility(View.GONE);
    }

    private void showButton(PlaceOfInterest poi) {
        // Mostramos el boton y le asignamos el titulo del marcador
        navigateButton.setVisibility(View.VISIBLE);
        navigateButton.setText(poi.name);
        navigateButton.setOnClickListener(v -> {
            // Al pulsar el boton, se abre el la actividad de la descripcion
            Intent intent = new Intent(this, DescriptionActivity.class);
            intent.putExtra("poi", poi);
            startActivity(intent);
        });
    }
}