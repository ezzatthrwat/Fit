package com.example.zizoj.fit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zizoj.fit.Network.ApiClient;
import com.example.zizoj.fit.Network.ApiService;
import com.example.zizoj.fit.Network.Model.Responses;
import com.example.zizoj.fit.Network.Model.Result;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GymMapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient ;
    public LocationRequest locationRequest;
    public Location lastLocation;
    public Marker CurrentUserLocationMarker;
    private static final int Request_user_Location = 99 ;

    private Double Latitude = 0.00;
    private Double Longitude = 0.00;

    ArrayList<HashMap<String, String>> locations = new ArrayList<>();
    HashMap<String, String> map;



    EditText AnyplaceNameEditTxt ;


    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_maps);

        AnyplaceNameEditTxt = findViewById(R.id.AnyPlaceNameEditTxt);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            checkUserLocationPermission();
        }


        initRetrofit();
        nearbyPlace("gym");



    }


    private void initRetrofit(){
        apiService = ApiClient.getClient(getApplication()).create(ApiService.class);

    }

    private void nearbyPlace(final String placeType){


        String url = getUrl(Latitude,Latitude,placeType);
        apiService.getNearbyPlaces(url)
                .enqueue(new Callback<Responses>() {
                    @Override
                    public void onResponse(Call<Responses> call, Response<Responses> response) {

                        if(response.isSuccessful()){

                            for (int i=0 ; i<response.body().getResults().size() ; i++){


                                Result googleplace = response.body().getResults().get(i);
                                double lat = googleplace.getGeometry().getLocation().getLat();
                                double lng = googleplace.getGeometry().getLocation().getLng();
                                String placeName = googleplace.getName();
                                String Vicinity  = googleplace.getVicinity();
                                LatLng latLng = new LatLng(lat,lng);

                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.title(placeName);
                                if (placeType.equals("gym")){

                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                                }else {
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                                }

                                mMap.addMarker(markerOptions);

                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Responses> call, Throwable t) {

                    }
                });
    }



    private String getUrl(double latitude , double longitude , String PlaceType){

        StringBuilder googleplaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleplaceUrl.append("location="+latitude+","+longitude);
        googleplaceUrl.append("&radius="+1500);
        googleplaceUrl.append("&Type="+PlaceType);
        googleplaceUrl.append("&keyword=cruise");
        googleplaceUrl.append("&key=AIzaSyBzJGPB6ktmxKdcEOtaQE4kl-UDhR-B0o0");
        Log.d("getUrl",googleplaceUrl.toString());
        return googleplaceUrl.toString();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient(){

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient , locationRequest,  this);
        }

    }

    @Override
    public void onConnectionSuspended(int i)
    {

        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onLocationChanged(Location location) {


        lastLocation = location;
        if (CurrentUserLocationMarker != null) {
            CurrentUserLocationMarker.remove();
        }
        map = new HashMap<String, String>();
        map.put("LocationID", "0");
        map.put("Latitude", Double.toString(location.getLatitude()));
        map.put("Longitude", Double.toString(location.getLongitude()));
        map.put("LocationName", "Current Position");

        locations.add(map);

        Latitude = Double.parseDouble(locations.get(0).get("Latitude"));
        Longitude = Double.parseDouble(locations.get(0).get("Longitude"));
        LatLng coordinate = new LatLng(Latitude, Longitude);
        mMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 12));
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }


    }



    public boolean checkUserLocationPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

                ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_user_Location);
            }else {
                ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_user_Location);
            }
            return false;
        }else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case Request_user_Location :
                if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED ){

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                        if (googleApiClient == null){
                            buildGoogleApiClient();
                            mMap.setMyLocationEnabled(true);

                        }

                    }
                }else {

                    Toast.makeText(this, "PERMISSION denied", Toast.LENGTH_SHORT).show();
                    
                }
                return;
        }
    }

    //find any place name
    public void searchBtn(View view) {

        String address = AnyplaceNameEditTxt.getText().toString();

        List<Address> addressList = null ;

        MarkerOptions anyplacemarkerOptions = new MarkerOptions();

        if (!TextUtils.isEmpty(address)){

            Geocoder geocoder = new Geocoder(this);

            try {
                addressList = geocoder.getFromLocationName(address,5);

                if (addressList != null){

                    for (int i = 0 ; i<addressList.size() ; i++){

                        Address userAddress = addressList.get(i);

                        LatLng latLng = new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                        anyplacemarkerOptions.position(latLng);
                        anyplacemarkerOptions.title(address);
                        anyplacemarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mMap.addMarker(anyplacemarkerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                    }
                }else{
                    Toast.makeText(this, "Location Not Found ...", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{

            Toast.makeText(this, "Enter your place ...", Toast.LENGTH_SHORT).show();
        }

        return;
    }
}
