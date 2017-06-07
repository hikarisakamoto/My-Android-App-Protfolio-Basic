package com.hikarisakamoto.myportfoliobasic.TopDownloader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hikarisakamoto.myportfoliobasic.R;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";

    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<FeedEntry> entries;

    public FeedAdapter(Context context, int resource, List<FeedEntry> entries) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.entries = entries;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            Log.d(TAG, "getView: Called with NULL convertView");
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            Log.d(TAG, "getView: Provided a convertView");
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        TextView tvNumber = (TextView) convertView.findViewById(tvNumber);
//        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
//        TextView tvArtist = (TextView) convertView.findViewById(R.id.tvArtist);
//        TextView tvSummary = (TextView) view.findViewById(R.id.tvSummary);
//        ImageView imageApps = (ImageView) view.findViewById(R.id.imageApps);

        FeedEntry currentEntry = entries.get(position);

        viewHolder.tvName.setText(currentEntry.getName());
        viewHolder.tvArtist.setText(String.format(getContext().getResources().getString(R.string.byString), currentEntry.getArtist()));
        viewHolder.tvNumber.setText(String.format(getContext().getResources().getString(R.string.numberList), (position + 1)));
//        tvSummary.setText(currentEntry.getSummary());
//        Ion.with(imageApps).placeholder(R.drawable.placeholder_image)
//                .error(R.drawable.error_image).load(currentEntry.getImageURL());

        return convertView;
    }

    private class ViewHolder {

        final TextView tvName;
        final TextView tvArtist;
        final TextView tvNumber;

        ViewHolder(View view) {
            this.tvName = (TextView) view.findViewById(R.id.tvName);
            this.tvArtist = (TextView) view.findViewById(R.id.tvArtist);
            this.tvNumber = (TextView) view.findViewById(R.id.tvNumber);
        }

    }
}
