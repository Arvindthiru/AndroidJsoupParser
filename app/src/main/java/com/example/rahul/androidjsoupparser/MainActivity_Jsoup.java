package com.example.rahul.androidjsoupparser;

import android.*;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity_Jsoup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Document htmlDocument;
    GPSTracker gps;
    private TextView address;
    private String places="";
    private AutoCompleteTextView text;
    private Button htmlTitleButton;
    private Button userButton;
    private String shop = "";
    private String html = "";
    private String lat = "";
    private String place1="";
    private String lng = "";
    private String latlng1 = "";
    private String addre = " ";
    ProgressDialog dialog;
    private String distance = "";
    List<String> a = new ArrayList<String>();
    List<String> list1 = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    List<String> list4 = new ArrayList<String>();
    List<String> list3 = new ArrayList<String>();
    int count = 0;
    int count1 = 0;
    int i = 8;
    int GPSoff = 0;
    private Spinner spinner;
    LocationManager mlocManager;
    Location location = null;
    Geocoder geocoder;
    List<android.location.Address> addresses;

    HashMap<String, String> hashMap = new HashMap<String, String>();
    //LocationManager mlocManager;
    //Location location = null;
    private static final String[] dist = {
            "1km", "2km", "3km", "5km", "10km", "20km", "100km"
    };
    private static final String[] shops = {"Advocate", "Agency & A-to-Z Services", "Apparels, Readymades &Textiles", "Architects", "Auto Stand", "Automobile Shop", "Baby Shop",
            "Bags", "Bakery, Sweets and Snacks", "Battery", "Beauty Parlour and Spa", "Bedding", "Biryani Center", "Book Shop", "Browsing Center", "Builders", "Building Materials",
            "Butcher Shop", "Call Taxi & Travels", "Car Wash", "Carpenter", "Catering", "Coconut Shop", "Coffee & Tea Shop", "Coffee Powder", "Computer Sales & Service", "Corporate Gifts",
            "Country Drugs", "Courier", "Cycle Sales & Repair", "Dental Clinic", "Diagnostic Lab", "Driving School", "Electricals & Electronics", "Electrician & Plumber", "Engineering Works",
            "Event Items Rentals", "Fancy Store", "Finance", "Flour Mill & Rice Mill", "Flower Shop", "Footwear", "Fruit Shop", "Furniture Rental", "Furnitures", "General Repair Shop",
            "Glass and Plywoods", "Gold Covering", "Grocery, Provisions & General Store", "Guest House", "Gym", "Helmets & Seat Covers", "Home Appliances", "Hospitals and Clinics",
            "Hotel, Restaurant & Fast Food", "Idly Dosa Batter Shop", "Insect Screens", "Interior Decoration", "Jewelry Shop", "Juice, Cool Drinks & Ice cream", "Laundry, Ironing & Dry Cleaners",
            "Library", "LIC & Insurance", "Lock Smith", "Magazine & Newspaper Mart", "Mandapam", "Medical & Drugs Shop", "Milk Center", "Mobile Recharge & Accessories", "Mobile Showroom",
            "Modular Kitchen", "Musical Instruments", "Nursery", "Office", "Oil Store", "Optical Shop", "Organic Shop", "Others", "Pet Shop", "Photo Studio, Videography & Colour Lab",
            "Photos & Frames Shop", "Plastics Store", "Play School", "Pooja Materials", "Printing, Offsetting and Binding", "Puncture Shop", "Real Estate", "Rice Shop", "Roofings", "Saloon",
            "Shoe, Bag and Tailoring Materials", "Sound Service", "Sports", "Stationery Shop", "Sticker Shop", "Super Market", "Tailor", "Tax Consultant", "Temples", "test1", "Tiles Shop",
            "Timber Shop", "Tinkering", "Two Wheeler Dealers", "Two Wheeler Service", "Tyre", "UPS & Inverters", "Vegetable Shop", "Vessels & Utensils", "Waste Paper Mart", "Watch and Clocks",
            "Wedding Cards", "Welding Shop", "Wood Works", "Xerox", "Yoga and Meditation"
    };

    public MainActivity_Jsoup() {
        hashMap.put("Advocate", "245");
        hashMap.put("Agency & A-to-Z Services", "112");
        hashMap.put("Apparels, Readymades &Textiles", "31");
        hashMap.put("Architects", "66");
        hashMap.put("Auto Stand", "162");
        hashMap.put("Automobile Shop", "38");
        hashMap.put("Baby Shop", "36");
        hashMap.put("Bags", "107");
        hashMap.put("Bakery, Sweets and Snacks", "15");
        hashMap.put("Battery", "182");
        hashMap.put("Beauty Parlour and Spa", "232");
        hashMap.put("Bedding", "189");
        hashMap.put("Biryani Center", "235");
        hashMap.put("Book Shop", "27");
        hashMap.put("Browsing Center", "99");
        hashMap.put("Builders", "67");
        hashMap.put("Building Materials", "123");
        hashMap.put("Butcher Shop", "102");
        hashMap.put("Call Taxi & Travels", "113");
        hashMap.put("Car Wash", "246");
        hashMap.put("Carpenter", "70");
        hashMap.put("Catering", "100");
        hashMap.put("Coconut Shop", "153");
        hashMap.put("Coffee & Tea Shop", "17");
        hashMap.put("Coffee Powder", "250");
        hashMap.put("Computer Sales & Service", "224");
        hashMap.put("Corporate Gifts", "257");
        hashMap.put("Country Drugs", "228");
        hashMap.put("Courier", "199");
        hashMap.put("Cycle Sales & Repair", "209");
        hashMap.put("Dental Clinic", "229");
        hashMap.put("Diagnostic Lab", "240");
        hashMap.put("Driving School", "111");
        hashMap.put("Electricals & Electronics", "8");
        hashMap.put("Electrician & Plumber", "68");
        hashMap.put("Engineering Works", "143");
        hashMap.put("Event Items Rentals", "172");
        hashMap.put("Fancy Store", "16");
        hashMap.put("Finance", "279");
        hashMap.put("Flour Mill & Rice Mill", "72");
        hashMap.put("Flower Shop", "58");
        hashMap.put("Footwear", "33");
        hashMap.put("Fruit Shop", "23");
        hashMap.put("Furniture Rental", "171");
        hashMap.put("Furnitures", "45");
        hashMap.put("General Repair Shop", "134");
        hashMap.put("Glass and Plywoods", "138");
        hashMap.put("Gold Covering", "251");
        hashMap.put("Grocery, Provisions & General Store", "124");
        hashMap.put("Guest House", "248");
        hashMap.put("Gym", "51");
        hashMap.put("Helmets & Seat Covers", "7");
        hashMap.put("Home Appliances", "212");
        hashMap.put("Hospitals and Clinics", "230");
        hashMap.put("Hotel, Restaurant & Fast Food", "101");
        hashMap.put("Idly Dosa Batter Shop", "155");
        hashMap.put("Insect Screens", "121");
        hashMap.put("Interior Decoration", "115");
        hashMap.put("Jewelry Shop", "35");
        hashMap.put("Juice, Cool Drinks & Ice cream", "105");
        hashMap.put("Laundry, Ironing & Dry Cleaners", "262");
        hashMap.put("Library", "268");
        hashMap.put("LIC & Insurance", "157");
        hashMap.put("Lock Smith", "108");
        hashMap.put("Magazine & Newspaper Mart", "28");
        hashMap.put("Mandapam", "60");
        hashMap.put("Medical & Drugs Shop", "24");
        hashMap.put("Milk Center", "152");
        hashMap.put("Mobile Recharge & Accessories", "163");
        hashMap.put("Mobile Showroom", "130");
        hashMap.put("Modular Kitchen", "201");
        hashMap.put("Musical Instruments", "59");
        hashMap.put("Nursery", "135");
        hashMap.put("Office", "219");
        hashMap.put("Oil Store", "103");
        hashMap.put("Optical Shop", "34");
        hashMap.put("Organic Shop", "46");
        hashMap.put("Others", "65");
        hashMap.put("Pet Shop", "43");
        hashMap.put("Photo Studio, Videography & Colour Lab", "125");
        hashMap.put("Photos & Frames Shop", "173");
        hashMap.put("Plastics Store", "181");
        hashMap.put("Play School", "63");
        hashMap.put("Pooja Materials", "227");
        hashMap.put("Printing, Offsetting and Binding", "98");
        hashMap.put("Puncture Shop", "244");
        hashMap.put("Real Estate", "178");
        hashMap.put("Rice Shop", "203");
        hashMap.put("Roofings", "185");
        hashMap.put("Saloon", "19");
        hashMap.put("Shoe, Bag and Tailoring Materials", "247");
        hashMap.put("Sound Service", "170");
        hashMap.put("Sports", "160");
        hashMap.put("Stationery Shop", "26");
        hashMap.put("Sticker Shop", "151");
        hashMap.put("Super Market", "37");
        hashMap.put("Tailor", "42");
        hashMap.put("Tax Consultant", "249");
        hashMap.put("Temples", "239");
        hashMap.put("test1", "282");
        hashMap.put("Tiles Shop", "49");
        hashMap.put("Timber Shop", "57");
        hashMap.put("Tinkering", "191");
        hashMap.put("Two Wheeler Dealers", "140");
        hashMap.put("Two Wheeler Service", "39");
        hashMap.put("Tyre", "243");
        hashMap.put("UPS & Inverters", "258");
        hashMap.put("Vegetable Shop", "20");
        hashMap.put("Vessels & Utensils", "176");
        hashMap.put("Waste Paper Mart", "21");
        hashMap.put("Watch and Clocks", "48");
        hashMap.put("Wedding Cards", "197");
        hashMap.put("Welding Shop", "74");
        hashMap.put("Wood Works", "116");
        hashMap.put("Xerox", "22");
        hashMap.put("Yoga and Meditation", "52");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__jsoup);
        dialog=new ProgressDialog(MainActivity_Jsoup.this);
        //AutoCompleteTextView text;

        text = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        address = (TextView) findViewById(R.id.add);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shops);
        htmlTitleButton = (Button) findViewById(R.id.button);
        userButton = (Button) findViewById(R.id.user);
        ImageView imageView1 = (ImageView) findViewById(R.id.logo);
        Picasso.with(this).load(R.drawable.kartla_logo).into(imageView1);
       /* @SuppressLint("WifiManagerLeak") WifiManager wifi = (WifiManager) getSystemService(MainActivity_Jsoup.WIFI_SERVICE);
        boolean b=wifi.isWifiEnabled();
        if(b==false) {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MainActivity_Jsoup.this);
            // Setting Dialog Title
            alertDialog.setTitle("Internet settings");
            // Setting Dialog Message
            alertDialog.setMessage("Internet is not enabled. Do you want to go to settings menu?");
            // On pressing Settings button
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                    startActivityForResult(gpsOptionsIntent, 0);
                }
            });
            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            // Showing Alert Message
            alertDialog.show();
        }
        else
        {

        }*/
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(MainActivity_Jsoup.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected()))
        {
        }
        else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Connect to Internet or quit")
                    .setCancelable(false)
                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }







        //wifi.setWifiEnabled(true); // true or false to activate/deactivate wifi
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        text.setAdapter(adapter);
        text.setThreshold(1);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity_Jsoup.this,
                android.R.layout.simple_spinner_item, dist);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);

        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);


        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String) parent.getItemAtPosition(position);
                //TODO Do something with the selected text
                System.out.println(hashMap.get(selection));
                shop = hashMap.get(selection);

            }
        });



        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //Log.i("TAG", "Place: " + place.getName());
                places = String.valueOf(place.getName());
                latlng1 = String.valueOf(place.getLatLng());
                addre= String.valueOf(place.getAddress());

                places = places.replace(" ","%20");
                Toast.makeText(getApplicationContext(),places, Toast.LENGTH_LONG).show();

                Log.d("Tag", latlng1);
                //System.out.println(addre);
                autocompleteFragment.setText(places);
                //autocompleteFragment.setText(addre);




                //Toast.makeText(MainActivity_auto.this, "Your latlng " +latlng , Toast.LENGTH_SHORT).show();

                // Log.i("TAG", "Place: " + place.getLatLng());
                // Log.i("TAG", "Place: " + place.getAddress());


            }


            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });

        // parsedHtmlNode = (TextView) findViewById(R.id.textView);

        /*Ion.with(getApplicationContext()).load("https://kart.la/search-results/?gmw_keywords&gmw_address%5B0%5D=Nanganallur%2C%20Chennai%2C%20Tamil%20Nadu&gmw_post=post&tax_category%5B0%5D=132&gmw_distance=100&gmw_units=metric&gmw_form=2&gmw_per_page=10&gmw_lat=12.9753604&gmw_lng=80.19012190000001&gmw_px=pt&action=gmw_post").asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {

                html = result;
                //Log.d("myapp1s", String.valueOf(html));

            }
        });*/
        userButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(MainActivity_Jsoup.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    latlng1="latlng"+latitude+","+longitude;
                    geocoder=new Geocoder(MainActivity_Jsoup.this, Locale.getDefault());
                    try {
                        addresses=geocoder.getFromLocation(latitude,longitude,1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //for(int)
                    String knownname=addresses.get(0).getAddressLine(1);
                    int n=addresses.get(0).getMaxAddressLineIndex();
                    System.out.println(n);
                    for(int k=0;k<n;k++)
                    {
                        //System.out.println(addresses.get(0).getAddressLine(k));
                        addre+=" "+addresses.get(0).getAddressLine(k);
                    }


                    Pattern p = Pattern.compile(".*,\\s*(.*)");
                    Matcher m = p.matcher(knownname);

                    if (m.find())
                    {//System.out.println(m.group(1));
                        places=m.group(1) ;
                        places = places.replace(" ","%20");
                        Toast.makeText(getApplicationContext(),places, Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(),places, Toast.LENGTH_LONG).show();

                    }
                    //address.setText(addre);
                    autocompleteFragment.setText(addre);

                    // \n is for new line
                    // Toast.makeText(getApplicationContext(), "User Location Obtained", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), places, Toast.LENGTH_LONG).show();

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
               /* if(latlng2.length()>0)
                {
                    latlng1=latlng2;
                    places=place1;
                    //System.out.println(latlng1);
                    //System.out.println(shop);
                   // System.out.println(distance);

                }*/




                if (shop == "" || distance == "" || latlng1 == "") {

                    Toast.makeText(MainActivity_Jsoup.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();

                } else {

                    dialog.setMessage("Loading");
                    dialog.setCancelable(false);
                    dialog.setInverseBackgroundForced(false);
                    dialog.show();
                    JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                    jsoupAsyncTask.execute();
                }
            }
        });
        StringBuffer sBuffer = new StringBuffer();
        Pattern latLngPattern = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher matcher = latLngPattern.matcher(latlng1);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            a.add((matcher.group(1)));
            lat = a.get(0);
            lng = a.get(1);

        }
        /*Ion.with(this).load("https://kart.la/search-results/?gmw_post=post&tax_category%5B0%5D=" + shop + "&gmw_distance=" + distance + "&gmw_units=metric&gmw_form=2&gmw_per_page=10&gmw_lat=" + lat + "&gmw_lng=" + lng + "&gmw_px=pt&action=gmw_post").asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {

                html = result;
                //Log.d("myapp1s", String.valueOf(html));

            }
        });*/
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



   /* public void onRestart() {
        super.onRestart();
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }*/

@Override
public void onResume(){
    super.onResume();
    // put your code here..
    if(dialog.isShowing()) {
        dialog.dismiss();
         list1 = new ArrayList<String>();
         list2 = new ArrayList<String>();
         list4 = new ArrayList<String>();
        list3 = new ArrayList<String>();
    }

}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                distance = "1km";
                break;
            case 1:
                distance = "2km";
                break;
            case 2:
                distance = "3km";
                break;
            case 3:
                distance = "5km";
                break;
            case 4:
                distance = "10km";
                break;
            case 5:
                distance = "20km";
                break;
            case 6:
                distance = "100km";
                break;

        }
        System.out.println(distance);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Ion.with(getApplicationContext()).load("https://kart.la/search-results/?gmw_keywords&gmw_address%5B0%5D="+places+"&gmw_post=post&tax_category%5B0%5D="+shop+"&gmw_distance="+distance+"&gmw_units=metric&gmw_form=2&gmw_per_page=10&gmw_lat="+lat+"&gmw_lng="+lng+"&gmw_px=pt&action=gmw_post").asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        html = result;
                        //Log.d("myapp1s", String.valueOf(html));

                    }
                }).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            try {
                // htmlContentInStringFormat = Jsoup.connect("https://kart.la/search-results/?gmw_keywords&gmw_address%5B0%5D=Nanganallur%2C%20Chennai%2C%20Tamil%20Nadu&gmw_post=post&tax_category%5B0%5D=31&gmw_distance=2&gmw_units=metric&gmw_form=2&gmw_per_page=10&gmw_lat=12.9753604&gmw_lng=80.19012190000001&gmw_px=pt&action=gmw_post").get().html();
                htmlDocument = Jsoup.parse(html);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Log.d("myapp1s", String.valueOf(htmlDocument));
            Elements links = htmlDocument.select("img[src$=.jpeg]");
            Elements texts = htmlDocument.select("h2 > a[href]");
            Elements els = htmlDocument.getElementsByClass("wppl-address");
            Elements latlng = htmlDocument.getElementsByTag("script");
            //doc.select("script[type=text/javascript]:not([src~=[a-zA-Z0-9./\s]+)");
            for (Element link : links) {
                Log.d("myapp", link.attr("src"));
                list1.add(link.attr("src"));
                //count++;
                //Log.d("myapp1", String.valueOf(count));

                //parsedHtmlNode.setText((CharSequence) link);
                //
            }
            for (Element text : texts) {
                Log.d("myapp", text.ownText());
                list2.add(text.ownText());
                //count++;
                // Log.d("myapp1", String.valueOf(count));
                //Log.d("myapp",text.attr("href"));
            }
            for (Element el : els) {
                Log.d("myapp", el.ownText());
                list3.add(el.ownText());
            }
            for (Element ll : latlng) {
                StringBuffer sBuffer = new StringBuffer();
                StringBuffer latLong = new StringBuffer();
                Pattern latLngPattern = Pattern.compile(".[0-9]+.[0-9]+.*[0-9]+[0-9]+.");

                Pattern pattern = Pattern.compile("(var end.*)([0-9]+.[0-9]+).*([0-9]+.[0-9]+).*");
                Matcher matcher = pattern.matcher(ll.data());

                while (matcher.find()) {
                    //System.out.println(matcher.group());
                    sBuffer.append(matcher.group());
                    Matcher latLngMatcher = latLngPattern.matcher(sBuffer);
                    while (latLngMatcher.find()) {
                        list4.add(latLngMatcher.group());
                        Log.d("myapp", latLngMatcher.group());
                    }
                }
            }
            Intent intent = new Intent(MainActivity_Jsoup.this, MainActivity.class);
            intent.putExtra("userloc",latlng1);
            intent.putExtra("LIST1", (Serializable) list1);
            intent.putExtra("LIST2", (Serializable) list2);
            intent.putExtra("LIST3", (Serializable) list3);
            intent.putExtra("LIST4", (Serializable) list4);
            startActivityForResult(intent,0);
        }
    }
}