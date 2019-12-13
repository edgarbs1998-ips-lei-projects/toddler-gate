package parents_area;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.toddlergate12.MainActivity;
import com.example.toddlergate12.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class localization_history extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public Marker whereAmI;
    private final String TAG = "MAPS";
    private Location lastLocationloc = null;
    List<Polyline> polylines = new ArrayList<Polyline>();
    BottomNavigationView bottomNav;
    View mapView;

    // Permissions
    private static final int REQUEST_ACCESS_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra("BACKGROUND", false)) {
            Intent intent = new Intent(parents_area.localization_history.this, MainActivity.class);
            intent.putExtra("backgroundMap", false);
            startActivity(intent);
        }

        setContentView(R.layout.activity_localization_history);
        bottomNav =  findViewById(R.id.map_bottom_nav);
        mapView  = findViewById(R.id.map_locatization_history);
        // Obtain the SupportMapFragment and get notified when the map_locatization_history is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map_locatization_history);

        //set bottom nav "click" listener
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    //remove all lines on the map_locatization_history
                    case R.id.navigation_reset:
                        for(Polyline line : polylines)
                        {
                            line.remove();
                        }

                        polylines.clear();
                        break;
                    case R.id.navigation_screenshot:

                        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
                            Bitmap bitmap;

                            @Override
                            public void onSnapshotReady(Bitmap snapshot) {
                                bitmap = snapshot;

                                OutputStream fout = null;

                                String filePath = System.currentTimeMillis() + ".jpeg";

                                try
                                {
                                    fout = openFileOutput(filePath,
                                            MODE_PRIVATE);

                                    // Write the string to the file
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
                                    fout.flush();
                                    fout.close();
                                }
                                catch (FileNotFoundException e)
                                {
                                    // TODO Auto-generated catch block
                                    Log.d("ImageCapture", "FileNotFoundException");
                                    Log.d("ImageCapture", e.getMessage());
                                    filePath = "";
                                }
                                catch (IOException e)
                                {
                                    // TODO Auto-generated catch block
                                    Log.d("ImageCapture", "IOException");
                                    Log.d("ImageCapture", e.getMessage());
                                    filePath = "";
                                }

                                openShareImageDialog(filePath);
                            }
                        };

                        mMap.snapshot(callback);
                        break;
                }
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case ACCESS_FINE_LOCATION_REQUEST:
//                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    csurfaceHolder.addCallback(this);
//                    csurfaceHolder.setFormat(csurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//                }
//                break;
//        }
//
//        System.out.println(permissions[0]);
//        if (permissions.length == 1 && permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                    ActivityCompat.checkSelfPermission(this,
//                            android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
//                            PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider callingActivityCompat#requestPermissions here to request the missing
//                //permissions, and then overriding
//                // public void onRequestPermissionsResult(intrequestCode, String[] permissions, int[] grantResults)
//                // to handle the case where the user grants thepermission. See the documentation
//                // for ActivityCompat#requestPermissions for moredetails.
//                return;
//            }
//            mMap.setMyLocationEnabled(true);
//        } else {
//            // Permission was denied. Display an error message.
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prepareMap();
                } else {
                    Toast.makeText(getApplicationContext(), "The application has no access to device location", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static LatLng fromLocationToLatLng(Location location) {
        return new LatLng(location.getLatitude(),
                location.getLongitude());
    }

    private void updateWithNewLocation(Location location) {
        //TextView myLocationText;
        //myLocationText = (TextView) findViewById(R.id.locinfo);
        String latLongString = "No location found";
        String addressString = "No address found";
        if (location != null) { //update the map_locatization_history location
            LatLng latlng = fromLocationToLatLng(location);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 21));
            if (whereAmI != null) whereAmI.remove();
            whereAmI = mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Here I Am."));
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;
            Log.e(TAG, "Location: " + latLongString);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Geocoder gc = new Geocoder(this, Locale.getDefault());
            if (!Geocoder.isPresent()) {
                addressString = "No geocoder available";
            } else {
                try {
                    List<Address> addresses =
                            gc.getFromLocation(latitude, longitude, 1);
                    StringBuilder sb = new StringBuilder();
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        for (int i = 0; i <
                                address.getMaxAddressLineIndex(); i++)
                            if (address.getAddressLine(i) != null)

                                sb.append(address.getAddressLine(i)).append("\n");
                        if (address.getLocality() != null)

                            sb.append(address.getLocality()).append("\n");
                        if (address.getPostalCode() != null)

                            sb.append(address.getPostalCode()).append("\n");
                        if (address.getCountryName() != null)
                            sb.append(address.getCountryName());
                        addressString = sb.toString();
                        Log.e(TAG, "Address: " + addressString);
                    } else Log.e(TAG, "Invalid Address");
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        } else Log.e(TAG, "Invalid Location");
        //myLocationText.setText("Your current Position is:\n" + latLongString + "\n\n" + addressString);
    }

    private final LocationListener locationListener = new
            LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (lastLocationloc == null) {
                        lastLocationloc = location;
                    }
                    updateWithNewLocation(location);
                    LatLng lastLatLng = fromLocationToLatLng(lastLocationloc);
                    LatLng thisLatLng = fromLocationToLatLng(location);
                    lastLocationloc = location;
                    Polyline polyline = mMap.addPolyline(new PolylineOptions().add(lastLatLng).add(thisLatLng).width(4).color(Color.RED));
                    polylines.add(polyline);
                }

                @Override
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };

    /**
     * Manipulates the map_locatization_history once available.
     * …
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            prepareMap();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION);
        }

    }

    public void prepareMap() {
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)
                getSystemService(serviceName);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        String provider = locationManager.getBestProvider(criteria,
                true);
        try {
            Location l = locationManager.getLastKnownLocation(provider);


            if (l != null) {
                Log.e("TAG", "GPS is on");
                LatLng latlng = fromLocationToLatLng(l);
                whereAmI = mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                // Zoom in
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,
                        21));
                updateWithNewLocation(l);
                locationManager.requestLocationUpdates(provider, 2000, 10,
                        locationListener);
            } else {
                String bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, locationListener);
            }

        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void openShareImageDialog(String filePath)
    {
        File file = this.getFileStreamPath(filePath);

        if(!filePath.equals(""))
        {
            final ContentValues values = new ContentValues(2);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            final Uri contentUriFile = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(android.content.Intent.EXTRA_STREAM, contentUriFile);
            startActivity(Intent.createChooser(intent, "Share Image"));
        }
        else
        {
            //This is a custom class I use to show dialogs...simply replace this with whatever you want to show an error message, Toast, etc.
            Toast t = Toast.makeText(localization_history.this, "", Toast.LENGTH_SHORT);
            t.show();
        }
    }
}

