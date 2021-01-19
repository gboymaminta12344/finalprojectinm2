package com.android2.rent_a_space;

import android.net.Uri;

public class uploadPropertyImage {

    private String propertyName;
    private String mImageUri;
    private String user_id;

    public uploadPropertyImage() {
        //empty constructor needed
    }

    public uploadPropertyImage(String property_name,String imageUri,String user_id) {
        this.propertyName = property_name;
        this.mImageUri = imageUri;
        this.user_id = user_id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

