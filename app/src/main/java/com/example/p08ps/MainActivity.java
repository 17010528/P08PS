package com.example.p08ps;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Button btnNorth , btnCentral , btnEast;
    private GoogleMap map;
    private LatLng poi_North , poi_Central , poi_East;
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);



                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                LatLng poi_sg = new LatLng(1.353139,103.8582361);
                poi_North = new LatLng(1.3494108,103.868006);
                poi_Central = new LatLng(1.3495071,103.8373638);
                poi_East = new LatLng(1.371152,103.8548886);

                final Marker np = map.addMarker(new MarkerOptions()
                        .position(poi_North)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 ")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                final Marker cp = map.addMarker(new
                        MarkerOptions()
                        .position(poi_Central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                final Marker ep = map.addMarker(new
                        MarkerOptions()
                        .position(poi_East)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.stars)));

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_sg,
                        11));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //Using position get Value from arraylist
                            Toast.makeText(MainActivity.this, marker.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });





            }
        });

        btnNorth = findViewById(R.id.buttonNorth);
        btnCentral = findViewById(R.id.buttonCentral);
        btnEast = findViewById(R.id.buttonEast);
        spin = findViewById(R.id.spinner);

        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(map != null){

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North, 15));
                    }
            }
        });

        btnCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(map != null){

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central, 15));
                }
            }
        });

        btnEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(map != null){

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East, 15));

                }
            }
        });



        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                if(position == 1){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North,
                            15));
                }
                else if (position == 2){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central,
                            15));
                }
                else if(position == 3){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East,
                            15));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });



    }
}
