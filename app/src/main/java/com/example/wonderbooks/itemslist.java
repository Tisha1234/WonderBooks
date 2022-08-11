package com.example.wonderbooks;


import android.graphics.Bitmap;
import android.util.Log;

public class itemslist {
    public String book_name;
    private String book_author;
    private String publish;
    private String page;
    private String links;
    private String image;
    private String infoLink;


    public String getBook_name() {
        return book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public String getPublish() {
        return publish;
    }

    public String getLink() {
        return links;
    }

    public String getInfoLink() {
        return infoLink;
    }


    public String getPage() {
        return page;
    }

    public String getImage() {
        return image;
    }

    public itemslist(String book_name, String book_author, String publishDate, String pages, String link,String image,String infoLink) {
        this.book_name = book_name;
        this.book_author = book_author;
        this.publish=publishDate;
        this.page=pages;
        this.links=link;
        this.image=image;
        this.infoLink=infoLink;
    }

}