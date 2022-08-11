package com.example.wonderbooks;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG=MainActivity.class.getSimpleName();
    private Button b;
    private  EditText text;
    public static String s;
    private static final String BASE_URI="https://www.googleapis.com/books/v1/volumes?q=";
    private static final String CONDITIONS="&filter=free-ebooks&printType=books&maxResults=20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = (Button) findViewById(R.id.button);
        text = (EditText) findViewById(R.id.textView);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder book = new StringBuilder();
                String book_name = text.getText().toString();
                for (int i = 0; i < book_name.length(); i++) {
                    if (book_name.charAt(i) == ' ') {
                        book.append('+');
                    } else {
                        book.append(book_name.charAt(i));
                    }
                }
                Log.e(LOG_TAG, book.toString());
                s = BASE_URI + book.toString() + CONDITIONS;
                Log.e(LOG_TAG, s);

                Intent i=new Intent(MainActivity.this,Resulturl.class);
                startActivity(i);
            }
        });

    }
}
