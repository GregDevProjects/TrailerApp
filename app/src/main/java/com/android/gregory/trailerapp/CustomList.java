package com.android.gregory.trailerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends  ArrayAdapter<String> {

    private final Activity context;
    private final String[] values;
    private final Integer[] imageId;
    private final String[] movieID;

    public CustomList(Activity context,
                      String[] values, Integer[] imageId, String movieID[]) {
        super(context, R.layout.list_single, values);
        this.context = context;
        this.values = values;

        this.imageId = imageId;
        this.movieID = movieID;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(values[position]);
        imageView.setImageResource(imageId[position]);



        return rowView;
    }

    public String getMovieID(int position) {

        return movieID[position];
    }


}
