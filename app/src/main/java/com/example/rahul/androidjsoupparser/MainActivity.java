package com.example.rahul.androidjsoupparser;

/**
 * Created by Rahul on 06-05-2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    ListView list;
    LatLngBounds.Builder bounds;
    List<String> b=new ArrayList<String>();
    List<String> a=new ArrayList<String>();
    List<String> imageId;
    List<String>  web;
    List<String> address1;
    int i, j;
    List<String> list4;
    String img;
    String name;
    String add;
    String loc;
    String userlo="";
    String newstr1="";
    long no;
    CustomList adapter;
    static int count;
   /* String[] web ={
            "Google Plus",
            "Twitter",
            "Windows",
            "Bing",
            "Itunes",
            "Wordpress",
            "Drupal"
    };
    String[] imageId = {
            "https://kart.la/wp-content/uploads/2016/02/wp-1454652291937-150x150.jpeg",
            "https://kart.la/wp-content/uploads/2016/02/wp-1454654643473-150x150.jpeg",
            "https://kart.la/wp-content/uploads/2016/02/img_4145-150x150.jpeg",
            "https://kart.la/wp-content/uploads/2016/02/wp-1454411643533-150x150.jpeg",
            "https://kart.la/wp-content/uploads/2016/02/wp-1454584133681-150x150.jpeg",
            "https://kart.la/wp-content/uploads/2016/02/wp-1454668754219-150x150.jpeg",
            "https://kart.la/wp-content/uploads/2016/02/wp-1454671985410-150x150.jpeg"


    };
    String[] address1 = {
            "378, Medavakkam Main Rd, Vigneshwar Nagar, Nanganallur, Chennai, Tamil Nadu 600091, India",
            "49, Medavakkam Main Rd, Balaji Nagar, Puzhuthivakkam, Nangainallur, Chennai, Tamil Nadu 600091, India",
            "20, 4th Main Rd, Nanganallur, Chennai, Tamil Nadu 600061, India",
            "24, Lakshmi Nagar 4th Main Rd, Macmillan Colony, Nanganallur, Chennai, Tamil Nadu 600061, India",
            "D202, M.G.R. Rd, Hindu Colony, Nanganallur, Chennai, Tamil Nadu 600061, India",
            "1/2/42, Ullagaram Puludivakkam Main Rd, Vanuvampet, Madipakkam, Chennai, Tamil Nadu 600091, India",
            "177, Medavakkam Main Rd, Ullagaram, Nangainallur, Chennai, Tamil Nadu 600091, India",
            "2, 4th Main Rd, NCBS Colony, Nangainallur, Chennai, Tamil Nadu 600061, India"


    };*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userlo=(String) getIntent().getStringExtra("userloc");
         imageId = (List<String>) getIntent().getSerializableExtra("LIST1");
         web= (List<String>) getIntent().getSerializableExtra("LIST2");
        address1 = (List<String>) getIntent().getSerializableExtra("LIST3");
        list4 = (List<String>) getIntent().getSerializableExtra("LIST4");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


         adapter = new CustomList(MainActivity.this, web, imageId, address1);
         count= adapter.getCount();




        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(MainActivity.this, "You Clicked at " + web.get(+position), Toast.LENGTH_SHORT).show();
                img=imageId.get(+position);
                name=web.get(+position);
                add=address1.get(+position);
                loc=list4.get(+position);
                StringBuffer sBuffer1 = new StringBuffer();
                Pattern latLngPattern1 = Pattern.compile("(\\d+(?:\\.\\d+)?)");
                Matcher matcher1 = latLngPattern1.matcher(name);
                while (matcher1.find()) {
                    //System.out.println(matcher1.group(1));
                    no= Long.parseLong(matcher1.group(1));
                    Log.d("long no", String.valueOf(no));
                }
                Intent intent = new Intent(MainActivity.this, Show.class);
                intent.putExtra("image",img);
                intent.putExtra("name",name);
                intent.putExtra("address",add);
                intent.putExtra("location",loc);
                intent.putExtra("phone",no);
                intent.putExtra("userloc",userlo);
                startActivity(intent);




            }
        });

    }





    public void onMapReady(GoogleMap googleMap) {

        bounds = new LatLngBounds.Builder();
        StringBuffer sBuffer1 = new StringBuffer();
        Pattern latLngPattern1 = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher matcher1 = latLngPattern1.matcher(userlo);
        while (matcher1.find()) {
           // System.out.println(matcher1.group(1));
            b.add((matcher1.group(1)));
            Log.d("map1",userlo);
            Log.d("map",matcher1.group(1));

        }
        for (i=0,j = 0; j < b.size(); j=j+2,i=i+1) {
            //   Loca.add(new LatLng(Double.parseDouble(a.get(j)),Double.parseDouble(a.get(j + 1))));
            //System.out.println(a.get(j+1));
            LatLng Loca1 = new LatLng(Double.parseDouble(b.get(j)), Double.parseDouble(b.get(j+1)));
            //newstr1 = name.replaceAll("[^A-Za-z]+", "");
            googleMap.addMarker(new MarkerOptions().position(Loca1).title("marker in user location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            Log.d("myapp", String.valueOf(j));
            bounds.include(new LatLng(Double.parseDouble(b.get(j)), Double.parseDouble(b.get(j+1))));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(Loca1));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(Loca));
        }
        /*
        LatLng location = new LatLng(12.9753604, 80.1901219);
        googleMap.addMarker(new MarkerOptions().position(location)
                .title("Marker in nanganallur").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));*/
        for (i = 0; i <list4.size(); i++) {

            StringBuffer sBuffer = new StringBuffer();
            Pattern latLngPattern = Pattern.compile("(\\d+(?:\\.\\d+)?)");
            Matcher matcher = latLngPattern.matcher(list4.get(i));
            while (matcher.find()) {
                System.out.println(matcher.group(1));
                a.add((matcher.group(1)));
                //Log.d("map",matcher.group(1));

            }
        }
        System.out.println(a.size());
        // ArrayList<LatLng> Loca = new ArrayList();
        for (i=0,j = 0; j < a.size(); j=j+2,i=i+1) {
            //   Loca.add(new LatLng(Double.parseDouble(a.get(j)),Double.parseDouble(a.get(j + 1))));
            //System.out.println(a.get(j+1));
            LatLng Loca = new LatLng(Double.parseDouble(a.get(j)), Double.parseDouble(a.get(j+1)));
            bounds.include(new LatLng(Double.parseDouble(a.get(j)), Double.parseDouble(a.get(j+1))));
            String newstr = web.get(i).replaceAll("[^A-Za-z]+", "");
            googleMap.addMarker(new MarkerOptions().position(Loca).title("marker in location" + " " + newstr));
            Log.d("myapp", String.valueOf(j));
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







        //googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50));
        //CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
        //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //

            /*for(LatLng locations : Loca){
                googleMap.addMarker(new MarkerOptions().position(locations).title("markers"));
            }*/

    }

}