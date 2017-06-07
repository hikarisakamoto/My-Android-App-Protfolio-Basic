package com.hikarisakamoto.myportfoliobasic.TopDownloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.hikarisakamoto.myportfoliobasic.R;
import com.koushikdutta.ion.Ion;

public class SongDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        FeedEntry bundleEntry = (FeedEntry) getIntent().getSerializableExtra(TopDownaloderMain.FEED_ENTRY_OBJECT);

        TextView txtSongName = (TextView) findViewById(R.id.txtSongName);
        TextView txtSongArtist = (TextView) findViewById(R.id.txtSongArtist);
        ImageView imgAlbumCover = (ImageView)findViewById(R.id.imgAlbumCover);

        txtSongName.setText(bundleEntry.getName());
        txtSongArtist.setText(String.format(getResources().getString(R.string.byString), bundleEntry.getArtist()));
        Ion.with(imgAlbumCover).placeholder(R.drawable.placeholder_image).error(R.drawable.error_image).load(bundleEntry.getImageURL());
    }
}
