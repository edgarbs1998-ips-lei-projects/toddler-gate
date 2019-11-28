package parents_area;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toddlergate12.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class localization_history extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public Marker whereAmI;
    private final String TAG = "MAPS";
    private Location lastLocationloc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization_history);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        System.out.println(permissions[0] );
        if (permissions.length == 1 &&
                permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this,
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                    PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider callingActivityCompat#requestPermissions here to request the missing
                        //permissions, and then overriding
                        // public void onRequestPermissionsResult(intrequestCode, String[] permissions, int[] grantResults)
                        // to handle the case where the user grants thepermission. See the documentation
                        // for ActivityCompat#requestPermissions for moredetails.
                        return;
                    }
            mMap.setMyLocationEnabled(true);
        } else {
            // Permission was denied. Display an error message.
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
        if (location != null) { //update the map location
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
                    List < Address > addresses =
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
                    LatLng lastLatLng= fromLocationToLatLng(lastLocationloc);
                    LatLng thisLatLng= fromLocationToLatLng(location);
                    lastLocationloc= location;
                    mMap.addPolyline(new PolylineOptions().add(lastLatLng).add(thisLatLng).width(4).color(Color.RED));
                }
                @Override
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {}
                @Override
                public void onProviderEnabled(String provider) {}
                @Override
                public void onProviderDisabled(String provider) {}
            };

    /**
     * Manipulates the map once available.
     *…
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, 1);
        }
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
                //mMap.addMarker(newMarkerOptions().position(latlng).title("Marker!"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                LatLng latlng = fromLocationToLatLng(l);
                whereAmI = mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                // Zoom in
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,
                        21));
                updateWithNewLocation(l);
                locationManager.requestLocationUpdates(provider, 2000, 10,
                        locationListener);
            }
            else{
                String bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000,0, locationListener);
            }

        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}