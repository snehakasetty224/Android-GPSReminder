package com.example.sneha.gpsreminder;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.List;

public class AddTaskActivity  extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

        private GoogleMap mMap;

        private Marker marker = null;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
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

    public void onSearch(View view){
        EditText locationEditText = (EditText) findViewById(R.id.locationText);
        EditText nameEditText = (EditText) findViewById(R.id.nameText);
        EditText descEditText = (EditText) findViewById(R.id.descriptionText);

        String name = nameEditText.getText().toString();
        String desc = descEditText.getText().toString();
        String location = locationEditText.getText().toString();
        List<Address> addressList = null;
        if(location != null && !location.isEmpty()){
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng =  new LatLng(address.getLatitude(), address.getLongitude());
            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(name).snippet(desc));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }

    }

    /**
     * Empty or clear the text and map
     * @param view
     */
    public void onClear(View view){
        EditText nameEditText = (EditText) findViewById(R.id.nameText);
        EditText descEditText = (EditText) findViewById(R.id.descriptionText);
        EditText locationEditText = (EditText) findViewById(R.id.locationText);

        nameEditText.setText("");
        descEditText.setText("");
        locationEditText.setText("");

        if(marker != null){
            marker.remove();
        }
    }

    /**
     * clear the data and return to previous screen
     * @param view
     */
    public void onCancel(View view){
        onClear(view);
        finish();
    }

    /**
     * Validate and add data
     * @param view
     */
    public void onAdd(View view){
        EditText nameEditText = (EditText) findViewById(R.id.nameText);
        EditText descEditText = (EditText) findViewById(R.id.descriptionText);
        EditText locationEditText = (EditText) findViewById(R.id.locationText);
        List<Address> addressList = null;

        String name = nameEditText.getText().toString();
        String desc = descEditText.getText().toString();
        String location = locationEditText.getText().toString();

        if((name != null && !name.isEmpty())
                && (desc != null && !desc.isEmpty())
                && (location != null && !location.isEmpty())){
            if(marker == null ){
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(location,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = addressList.get(0);
                LatLng latLng =  new LatLng(address.getLatitude(), address.getLongitude());
                marker = mMap.addMarker(new MarkerOptions().position(latLng).title(name).snippet(desc));
            }
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("description", desc);
            resultIntent.putExtra("location", location);
            resultIntent.putExtra("latitude", marker.getPosition().latitude);
            resultIntent.putExtra("longitude", marker.getPosition().longitude);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }else{
            Toast.makeText(this, "Name, Description and Location are required to add task",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }
}
