package com.example.jianchaosun.parkingspotsharer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AvaAdaptar extends ArrayAdapter<obj> {
    private Context cont;
    private List<obj> avaList;


    public AvaAdaptar(@NonNull Context context, int resource, @NonNull List<obj> objects) {
        super(context, resource, objects);
        cont = context;
        avaList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview_parking_item,parent,false);
        TextView timetv = view.findViewById(R.id.showtime);
        timetv.setText("leaving time: "+avaList.get(position).getHour()+":"+avaList.get(position).getMin());
        TextView loctv  =view.findViewById(R.id.showlocation);
        loctv.setText("Location: "+avaList.get(position).getNumberOfParking());
        ImageView img = view.findViewById(R.id.imgView);
        if(avaList.get(position).getUri()!=null||avaList.get(position).getBitmap()!=null) {
            if(avaList.get(position).getBitmap()!=null) {
                Bitmap imageBitmap = StringToBitMap(avaList.get(position).getBitmap());
                img.setImageBitmap(imageBitmap);
            }else {
                String uri = avaList.get(position).getUri();
                Picasso.get().load(uri).into(img);
            }
        }
        return view;
    }
    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
