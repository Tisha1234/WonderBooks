package com.example.wonderbooks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.result.contract.ActivityResultContracts;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BooksAdapter extends ArrayAdapter<itemslist> {
    public static final String LOG_TAG=MainActivity.class.getSimpleName();

    public BooksAdapter(Context context, ArrayList<itemslist> items) {
        super(context, 0,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listview=convertView;
        if(listview==null){
            listview = LayoutInflater.from(getContext()).inflate(R.layout.activity_itemslist,parent,false);
        }
        itemslist item=getItem(position);

        TextView name=(TextView) listview.findViewById(R.id.bookname);
        name.setText(item.getBook_name());

        TextView author=(TextView) listview.findViewById(R.id.bookauthor);
        author.setText("by--\n" + item.getBook_author());

        TextView publish=(TextView) listview.findViewById(R.id.publish);
        publish.setText("Published On--\n"+item.getPublish());

        TextView page=(TextView) listview.findViewById(R.id.page);
        page.setText("Page Count--\n"+item.getPage());

        Button button = (Button) listview.findViewById(R.id.button);
        if(item.getInfoLink() != "no link") {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri bookUri = Uri.parse(item.getInfoLink());
                   // Log.e(LOG_TAG, "testing " + bookUri);
                    Intent i = new Intent(Intent.ACTION_VIEW,bookUri);
                    getContext().startActivity(i);
                }
            });
        }
        else{
            button.setText("No preview available");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Sorry,no preview available to read...", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Button button1 = (Button) listview.findViewById(R.id.button1);
        if(item.getLink() != "no link") {
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri bookUri = Uri.parse(item.getLink());
                    Log.e(LOG_TAG, "testing " + bookUri);
                    Intent i = new Intent(Intent.ACTION_VIEW,bookUri);
                    getContext().startActivity(i);
                }
            });
        }
        else{
            button.setText("No pdf available");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Sorry,no pdf available to download...", Toast.LENGTH_SHORT).show();
                }
            });
        }

        ImageView i = (ImageView) listview.findViewById(R.id.image);
        if(item.getImage() != "no image") {
            Uri imageUri = Uri.parse(item.getImage());
            Glide.with(getContext()).load(imageUri).into(i);
        }else{
            i.setImageResource(R.drawable.noimage);
        }

        return listview;
    }
}
