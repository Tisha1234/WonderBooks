package com.example.wonderbooks;

import android.content.Context;
import java.util.List;

public class Asynctas extends android.content.AsyncTaskLoader<List<itemslist>> {

    private String murl;

    public Asynctas(Context context, String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<itemslist> loadInBackground() {
        if (murl == null) {
            return null;
        }

        List<itemslist> items = query.fetchdata(murl);
        return items;
    }
}

