package com.codepath.apps.restclienttemplate.models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


@Parcel
@Entity

public class User {




    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public  String name;

    @ColumnInfo
    public  String screenName;

    @ColumnInfo
    public  String profileImageURL;
    //Empty constructor needed for the parceler library
    public User(){}



    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.id = jsonObject.getLong("id");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageURL = jsonObject.getString("profile_image_url_https");

        return user;



    }

}
