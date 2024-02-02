package com.example.basicsample;

import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.RestrictionsManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity<FusedLocationProviderClient> extends AppCompatActivity
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback  {

    private GoogleMap mMap;
    private boolean permissionDenied = false;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // googleMapの読み込み
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
        //郡山駅の座標
        LatLng kooriyama = new LatLng(37.3981955798298, 140.3882685728106);

        //pointは事故が起きやすい場所の座標
        ArrayList<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(37.394676302628895, 140.39018213617967));//交通事故発生箇所１
        points.add(new LatLng(37.39552227168641, 140.3822693731586));//交通事故発生箇所２
        points.add(new LatLng(37.39453044492982, 140.3863267296033));//交通事故発生箇所３
        //郡山駅周辺の座標を指定　場所は問わず　上記のように記入
        points.add(new LatLng(37.39799, 140.38342));//交通事故発生箇所４
        points.add(new LatLng(37.395917494000045, 140.38503403427447));//交通事故発生箇所５
        points.add(new LatLng(37.39665886162812, 140.38491630512533));//交通事故発生箇所６
        points.add(new LatLng(37.39555815727834, 140.38574068332304));//交通事故発生箇所７
        points.add(new LatLng(37.4000595563287, 140.38430703724444));//交通事故発生箇所８
        points.add(new LatLng(37.4025576199441, 140.38351312517594));//交通事故発生箇所９
        points.add(new LatLng(37.401000224073385, 140.38121124229178));//交通事故発生箇所１０
        points.add(new LatLng(37.40105047375064, 140.37939493214995));//交通事故発生箇所１１
        points.add(new LatLng(37.40421656636788, 140.38052097218858));//交通事故発生箇所１２
        points.add(new LatLng(37.406513986480576, 140.38410681018502));//交通事故発生箇所１３
        points.add(new LatLng(37.40599900864894, 140.38315073556743));//交通事故発生箇所１４
        points.add(new LatLng(37.40859068675891, 140.38237892771429));//交通事故発生箇所１５
        points.add(new LatLng(37.39602969538616, 140.39676145516037));//交通事故発生箇所１６
        points.add(new LatLng(37.406708641420884, 140.39018537191578));//交通事故発生箇所１７

        ArrayList<String> pinTexts = new ArrayList<>();
        pinTexts.add("恐喝事件発生");
        pinTexts.add("未成年への客引き発生");
        pinTexts.add("恐喝事件発生");
        //上記のようにポイントを追加した順にピンテキストを追加していく
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");
        pinTexts.add("交通事故発生");


        for(int i = 0;i<points.size();i++) {
            if(i < 3){
                mMap.addMarker(new MarkerOptions()
                        .position(points.get(i))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .title(pinTexts.get(i)));
            }
            else{
                mMap.addMarker(new MarkerOptions()
                        .position(points.get(i))
                        .title(pinTexts.get(i)));
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kooriyama,15));
    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

}
