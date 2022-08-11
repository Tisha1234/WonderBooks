package com.example.wonderbooks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class query {

    public static final String LOG_TAG= MainActivity.class.getSimpleName();
    public static Uri builduri;
    public static String link;

    private query(){
    }

    public static ArrayList<itemslist> fetchdata(String url1){
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            Log.e(LOG_TAG,"Error in fetchdata",e);
        }
        builduri = Uri.parse(url1).buildUpon().build();
        URL url=createUrl(builduri);

        String jsonRsponse="";
        try{
            jsonRsponse=makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem making http connection. ",e);
        }
        ArrayList<itemslist> item = extractInfoFromjson(jsonRsponse);
        return item;
    }

    private static ArrayList<itemslist> extractInfoFromjson(String jsonRsponse) {
        ArrayList<itemslist> items= new ArrayList<itemslist>();
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonRsponse);
            JSONArray jsonArray= baseJsonResponse.getJSONArray("items");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item=jsonArray.getJSONObject(i);
                JSONObject volume=item.getJSONObject("volumeInfo");

                String name_of_book=volume.getString("title");
                String name_of_author="";
                if(volume.has("authors")){
                    JSONArray authorsArray=volume.getJSONArray("authors");
                    for(int j=0;j< authorsArray.length();j++){
                        String author=authorsArray.getString(j);
                        if(name_of_author == ""){
                            name_of_author=author;
                            Log.e(LOG_TAG,"current array has authors : "+name_of_author);
                        }
                        else{
                            name_of_author = name_of_author+" , "+ author;
                            Log.e(LOG_TAG,"current array has: "+name_of_author);
                        }
                    }
                }
                else{
                    name_of_author="Unknown author";
                }

                String pages="";
                if(volume.has("pageCount")){
                     pages=volume.getString("pageCount");
                }
                else{
                    pages="Unknown page number";
                }

                String PublishDate="";
                if(volume.has("publishedDate")){
                     PublishDate= volume.getString("publishedDate");
                }
                else{
                    pages="Unknown publish date";
                }

                JSONObject access=item.getJSONObject("accessInfo");
                JSONObject pdf=access.getJSONObject("pdf");
                JSONObject epub=access.getJSONObject("epub");
                if(pdf.has("downloadLink")) {
                     link = pdf.getString("downloadLink");
                }
                else if(epub.has("downloadLink")){
                    link=epub.getString("downloadLink");
                    Log.e(LOG_TAG,"author test "+link);
                }
                else{
                    link="no link";
                }

               // Log.e(LOG_TAG,"author test "+link);

                JSONObject imageLink=volume.getJSONObject("imageLinks");
                String image="";
                if(imageLink.has("smallThumbnail")){
                    image= imageLink.getString("smallThumbnail");
                }else{
                    image="no image";
                }

                String infolink ="";
                if(volume.has("previewLink")) {
                    infolink = volume.getString("previewLink");
                    Log.e(LOG_TAG, "preview link test " + infolink);
                }
                else{
                    infolink="no link";
                }

                itemslist it = new itemslist(name_of_book,name_of_author,PublishDate,pages,link,image,infolink);
                items.add(it);
            }

        } catch (JSONException e ) {
            Log.e(LOG_TAG,"error in extract info",e);
        }
        return items;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "ERROR response code received " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving json data ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream input) throws IOException {
        StringBuilder output=new StringBuilder();
        if(input!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(input, Charset.forName("UTF-8"));
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line=bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static URL createUrl(Uri builduri) {
        URL url=null;
        try{
            url=new URL(builduri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Error in createurl");
        }
        return url;
    }
}
