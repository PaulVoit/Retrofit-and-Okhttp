package ru.jo4j.retrofitexample;

import androidx.core.app.ServiceCompat;

import com.google.gson.annotations.SerializedName;

public class Post {

    private int userId;
    private int id;
    private String title;

    @SerializedName("body")
    private String text;

    public int getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


}
