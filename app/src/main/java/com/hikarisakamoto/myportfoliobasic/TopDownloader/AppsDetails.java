package com.hikarisakamoto.myportfoliobasic.TopDownloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.hikarisakamoto.myportfoliobasic.R;
import com.koushikdutta.ion.Ion;


public class AppsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_downloader_list_apps);

        FeedEntry bundleEntry = (FeedEntry) getIntent().getSerializableExtra(TopDownaloderMain.FEED_ENTRY_OBJECT);

        TextView txtName = (TextView) findViewById(R.id.txtName);
        TextView txtArtist = (TextView) findViewById(R.id.txtArtist);
        TextView txtSummary = (TextView) findViewById(R.id.txtSummary);
        ImageView imageApp = (ImageView) findViewById(R.id.imageApp);

        txtName.setText(bundleEntry.getName());
        txtArtist.setText(String.format(getResources().getString(R.string.byString), bundleEntry.getArtist()));
        txtSummary.setText(bundleEntry.getSummary());
        txtSummary.setMovementMethod(new ScrollingMovementMethod());
        Ion.with(imageApp).placeholder(R.drawable.placeholder_image).error(R.drawable.error_image).load(bundleEntry.getImageURL());
    }
}
