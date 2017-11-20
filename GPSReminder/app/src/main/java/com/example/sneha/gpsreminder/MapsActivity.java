package com.example.sneha.gpsreminder;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private Marker marker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        DataController dataController=new DataController(getBaseContext());
        dataController.open();
        Cursor cursor = dataController.retrieve();
        ArrayList<Task> array = new ArrayList<Task>();
        if(cursor != null)
        {
            while(cursor.moveToNext())
            {
                Task t = new Task();
                t.setName(cursor.getString(0));
                t.setDescription(cursor.getString(1));
                t.setLocation(cursor.getString(2));
                t.setLatitude(cursor.getDouble(3));
                t.setLongitude(cursor.getDouble(4));
                array.add(t);
            }
        }
        cursor.close();
        dataController.close();

        mMap = googleMap;
        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        // Add a marker in Sydney and move the camera
        //   LatLng sanjose = new LatLng(37.33944, -121.89388);
        // mMap.addMarker(new MarkerOptions().position(sanjose).title("Marker in san Jose"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sanjose));
        for(Task task:array) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(task.getLatitude(),
                    task.getLongitude())).title(task.getName()).snippet(task.getDescription()));
        }
        if(array.size() > 0) {
            Log.d("Hello", "in here");
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(array.get(0).getLatitude(), array.get(0).getLongitude())));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }

    /**
     * Validate and add data
     * @param view
     */
    public void onBack(View view){
       finish();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }
}
