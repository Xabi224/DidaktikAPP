package com.elorrieta.didaktikapp.map;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.elorrieta.didaktikapp.R;
import com.elorrieta.didaktikapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private LocationManager locationManager;
    private LatLng currentLocation;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
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

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        // move camera using gps
        currentLocation = getCurrentLocation();
        mMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }

    private LatLng getCurrentLocation() {
        Location location = null;
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert location != null;
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}