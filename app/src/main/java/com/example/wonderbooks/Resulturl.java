package com.example.wonderbooks;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Resulturl extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<itemslist>> {

    public static final String LOG_TAG=MainActivity.class.getSimpleName();
    private static final int Books_loader_id=1;
    private BooksAdapter madapter;
    private TextView mempty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulturl);

                Log.e(LOG_TAG, "Executed till here");

                ListView list = (ListView) findViewById(R.id.list);
                mempty=(TextView)findViewById(R.id.mempty);
                list.setEmptyView(mempty);
                madapter = new BooksAdapter(Resulturl.this, new ArrayList<itemslist>());
                list.setAdapter(madapter);
               /* list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.e(LOG_TAG,"Working in log");
                        Toast.makeText(Resulturl.this, "Great Choice...Hope you love the book..\nDont forget to preview it before downloading..", Toast.LENGTH_SHORT).show();
                    }
                });*/

        ConnectivityManager con=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo=con.getActiveNetworkInfo();
        if(networkinfo!=null && networkinfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(Books_loader_id,null,this);
        }else{
            View loading=findViewById(R.id.loading);
            loading.setVisibility(View.GONE);
            mempty.setText("no Internet Connection...");
        }

    }

    @Override
    public Loader<List<itemslist>> onCreateLoader(int i, Bundle bundle) {
        return new Asynctas(this, MainActivity.s);
    }

    @Override
    public void onLoadFinished(Loader<List<itemslist>> loader, List<itemslist> itemslists) {
        View loading=findViewById(R.id.loading);
        loading.setVisibility(View.GONE);
        mempty.setText("No Books Found...");


        if(madapter!=null) {
            madapter.clear();
        }
        if(itemslists!=null && !itemslists.isEmpty()){
            madapter.addAll(itemslists);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<itemslist>> loader) {
        if(madapter!=null) {
            madapter.clear();
        }
        Log.e(LOG_TAG,"onPostExecution in progress");
    }
}