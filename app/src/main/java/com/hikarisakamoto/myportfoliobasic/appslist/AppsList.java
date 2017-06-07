package com.hikarisakamoto.myportfoliobasic.appslist;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.hikarisakamoto.myportfoliobasic.R;

import java.util.ArrayList;

public class AppsList {
    //    private static final String TAG = "AppsList";
    private static final int TOTAL_APPS = 3;

    private Context context;
    private ArrayList<AppsEntry> apps;


    public AppsList(Context context) {
        this.apps = new ArrayList<>();
        this.context = context;
    }

    public ArrayList<AppsEntry> getApps() {
        return apps;
    }

    public void appsListCreator() {
        AppsEntry currentApp;
        // Retrieve the list of apps names in the array string in the resource file
        String[] appName = context.getResources().getStringArray(R.array.appNames);

        // Goes through a for loop to create the list of apps to show

        for (int i = 1; i <= TOTAL_APPS; i++) {
            currentApp = new AppsEntry();
            // Goes through the array to get the apps names
            currentApp.setName(appName[i - 1]);
            // Gets the id of the drawable to set the image base on its id
            int id = context.getResources().getIdentifier("app" + i, "drawable", context.getPackageName());
            Drawable image = context.getResources().getDrawable(id, context.getTheme());
            currentApp.setImage(image);

            apps.add(currentApp);

        }

    }
}
