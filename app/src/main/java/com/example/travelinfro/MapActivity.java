package com.example.travelinfro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
        private GoogleMap mMap;
        double mapX=0,mapY=0;
        String title="";
        TextView mTvTitle;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map);

            mTvTitle = findViewById(R.id.map_tv_title);

            Intent intent = getIntent();
            mapX =  intent.getDoubleExtra("mapx",0);
            mapY = intent.getDoubleExtra("mapy",0);
            title = intent.getStringExtra("title");
            Log.e("mapX",""+mapX);
            Log.e("mapY",""+mapY);
            Log.e("title",""+title);

            mTvTitle.setText(title);

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
                mapFragment.getMapAsync(this);
        }
        @Override
        public void onMapReady ( final GoogleMap googleMap){
        mMap = googleMap;
        LatLng SEOUL = new LatLng(mapY,mapX);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title(title);
//        markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);
        // 기존에 사용하던 다음 2줄은 문제가 있습니다.
        // CameraUpdateFactory.zoomTo가 오동작하네요.
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
    }
}
