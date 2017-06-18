package com.example.rahul.androidjsoupparser;

/**
 * Created by Rahul on 06-05-2017.
 */
import android.util.Log;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rahul on 05-05-2017.
 */

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> web;
    private final List<String> imageId;
    private final List<String> address1;

    public CustomList(Activity context,
                      List<String> web, List<String> imageId, List<String> address) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

        this.address1 = address;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        try {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            TextView address = (TextView) rowView.findViewById(R.id.address);


            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(web.get(position));
            address.setText(address1.get(position));
            //Log.d("myapp",imageId[position]);
            Picasso.with(getContext()).load(imageId.get(position)).into(imageView);

            //imageView.setImageResource(imageId[position]);
            return rowView;
        }
        catch (Exception e) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_single, null, true);
            return rowView;

        }

    }
}
