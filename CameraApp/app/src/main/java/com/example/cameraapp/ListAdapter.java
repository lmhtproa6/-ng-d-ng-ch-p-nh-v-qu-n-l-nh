package com.example.cameraapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Hinh> {

    public ListAdapter(Context context, int textViewResourceId){
        super(context,textViewResourceId);
    }

    public ListAdapter( Context context, int resource, List<Hinh> items) {
        super(context, resource,items);
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View v = convertView;

        if ( v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_detail_pho,null);

        }

        Hinh h = getItem(position);

        if(h!=null){
            ImageView imageView = (ImageView) v.findViewById(R.id.imageViewHinhDetail);
            Bitmap bitmap = BitmapFactory.decodeByteArray(h.hinh,0,h.hinh.length);
            imageView.setImageBitmap(bitmap);

            TextView tv = (TextView) v.findViewById(R.id.textViewAlbumDetail);
            tv.setText(h.album);
        }
        return v;

    }
}
