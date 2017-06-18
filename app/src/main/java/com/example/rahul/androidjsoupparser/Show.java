package com.example.rahul.androidjsoupparser;

/**
 * Created by Rahul on 12-05-2017.
 */
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Show extends AppCompatActivity implements OnMapReadyCallback {
    String img;
    LatLngBounds.Builder bounds;
    String name;
    String add;
    String loc;
    String ulo;
    long no;
    List<String> a=new ArrayList<String>();
    List<String> b=new ArrayList<String>();
    int i,j;
    String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        img=(String) getIntent().getStringExtra("image");
        name=(String) getIntent().getStringExtra("name");
        add=(String) getIntent().getStringExtra("address");
        loc=(String) getIntent().getStringExtra("location");
        no=(Long) getIntent().getLongExtra("phone",0);
        ulo=(String) getIntent().getStringExtra("userloc");
        TextView txtTitle = (TextView)findViewById(R.id.txt);
        TextView address = (TextView)findViewById(R.id.address);
        ImageView imageView = (ImageView)findViewById(R.id.img);
        Button call1=(Button)findViewById(R.id.call);
        address.setText(add);
        txtTitle.setText(name);
        Log.d("long no", String.valueOf(no));



        //Log.d("show",add);
        //address.setText(add);
        //Log.d("myapp",imageId[position]);
        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=String.valueOf(no);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", num, null));
                startActivity(intent);
            }
        });
        Picasso.with(this).load(img).into(imageView);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        bounds = new LatLngBounds.Builder();
        StringBuffer sBuffer = new StringBuffer();
        Pattern latLngPattern = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher matcher = latLngPattern.matcher(loc);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            a.add((matcher.group(1)));
            //Log.d("map",matcher.group(1));

        }
        for (i=0,j = 0; j < a.size(); j=j+2,i=i+1) {
            //   Loca.add(new LatLng(Double.parseDouble(a.get(j)),Double.parseDouble(a.get(j + 1))));
            //System.out.println(a.get(j+1));
            LatLng Loca = new LatLng(Double.parseDouble(a.get(j)), Double.parseDouble(a.get(j+1)));
            bounds.include(new LatLng(Double.parseDouble(a.get(j)), Double.parseDouble(a.get(j+1))));
            String newstr = name.replaceAll("[^A-Za-z]+", "");
            googleMap.addMarker(new MarkerOptions().position(Loca).title("marker in location" + " " + newstr));
            Log.d("myapp", String.valueOf(j));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(Loca));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(Loca));
        }
        StringBuffer sBuffer1 = new StringBuffer();
        Pattern latLngPattern1 = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher matcher1 = latLngPattern1.matcher(ulo);
        while (matcher1.find()) {
            System.out.println(matcher1.group(1));
            b.add((matcher1.group(1)));
            //Log.d("map",matcher.group(1));

        }
        for (i=0,j = 0; j < b.size(); j=j+2,i=i+1) {
            //   Loca.add(new LatLng(Double.parseDouble(a.get(j)),Double.parseDouble(a.get(j + 1))));
            //System.out.println(a.get(j+1));
            LatLng Loca = new LatLng(Double.parseDouble(b.get(j)), Double.parseDouble(b.get(j+1)));
            bounds.include(new LatLng(Double.parseDouble(b.get(j)), Double.parseDouble(b.get(j+1))));
            String newstr = name.replaceAll("[^A-Za-z]+", "");
            googleMap.addMarker(new MarkerOptions().position(Loca).title("User location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            Log.d("myapp", String.valueOf(j));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(Loca));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(Loca));
        }
        float[] results = new float[1];
        android.location.Location.distanceBetween(bounds.build().northeast.latitude, bounds.build().northeast.longitude,
                bounds.build().southwest.latitude, bounds.build().southwest.longitude, results);

        CameraUpdate cu = null;
        if (results[0] < 1000) { // distance is less than 1 km -> set to zoom level 15
            cu = CameraUpdateFactory.newLatLngZoom(bounds.build().getCenter(), 15);
        } else {
            int padding = 50; // offset from edges of the map in pixels
            cu = CameraUpdateFactory.newLatLngBounds(bounds.build(), padding);
        }
        if (cu != null) {
            googleMap.moveCamera(cu);
        }


    }
}
