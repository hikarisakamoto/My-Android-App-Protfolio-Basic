package com.hikarisakamoto.myportfoliobasic.appslist;

import android.graphics.drawable.Drawable;

/**
 * Created by hikarisakamoto on 2017-05-08.
 */

public class AppsEntry {

    private String name;
    private Drawable image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
