package com.hikarisakamoto.myportfoliobasic.appslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hikarisakamoto.myportfoliobasic.R;

import java.util.List;

/**
 * Created by hikarisakamoto on 2017-05-08.
 */

public class AppsAdapter extends ArrayAdapter {
    private static final String TAG = "AppsAdapter";

    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<AppsEntry> appsEntries;


    public AppsAdapter(Context context, int resource, List<AppsEntry> appsEntries) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.appsEntries = appsEntries;
    }

    @Override
    public int getCount() {
        return appsEntries.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AppsEntry currentApp = appsEntries.get(position);

        viewHolder.appName.setText(currentApp.getName());
        viewHolder.appImage.setImageDrawable(currentApp.getImage());


        return convertView;
    }

    private class ViewHolder {
        final ImageView appImage;
        final TextView appName;

        ViewHolder(View v) {
            this.appImage = (ImageView) v.findViewById(R.id.appImage);
            this.appName = (TextView) v.findViewById(R.id.appName);
        }
    }
}
