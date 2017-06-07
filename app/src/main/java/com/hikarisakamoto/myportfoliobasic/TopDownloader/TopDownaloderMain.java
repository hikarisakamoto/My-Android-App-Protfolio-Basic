package com.hikarisakamoto.myportfoliobasic.TopDownloader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hikarisakamoto.myportfoliobasic.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TopDownaloderMain extends AppCompatActivity {
    private static final String TAG = "TopDownaloderMain";

    public ListView xmlListView;
    private String feedURL = null;
    private int feedLimit = 10;
    private List<FeedEntry> feedEntryList;

    //Bundles
    private String feedCachedURL = "INVALIDATED";
    public static final String STATE_URL = "feedURL";
    public static final String STATE_LIMIT = "feedLimit";
    public static final String FEED_ENTRY_OBJECT = "FeedEntry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_downaloder_main);

        if (savedInstanceState == null) {
            feedURL = getResources().getString(R.string.topFreeApps);
            setTitle(getResources().getString(R.string.app2));
        } else /* if (savedInstanceState != null) */ {
            feedURL = savedInstanceState.getString(STATE_URL);
            feedLimit = savedInstanceState.getInt(STATE_LIMIT);
            setTitle(getResources().getString(R.string.app2));
        }
        xmlListView = (ListView) findViewById(R.id.xmlListView);
//        Log.d(TAG, "onCreate: starting AsyncTask");
        downloadURL(String.format(feedURL, feedLimit));
//        Log.d(TAG, "onCreate: done");

        xmlListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FeedEntry bundleEntry;
                if (feedURL.equalsIgnoreCase(getResources().getString(R.string.topSongs))) {
                    bundleEntry = feedEntryList.get(position);
                    Intent openSongDetails = new Intent(getBaseContext(), SongDetails.class);
                    openSongDetails.putExtra(FEED_ENTRY_OBJECT, bundleEntry);
                    startActivity(openSongDetails);

                } else {
                    bundleEntry = feedEntryList.get(position);
                    Intent openAppDetails = new Intent(getBaseContext(), AppsDetails.class);
                    openAppDetails.putExtra(FEED_ENTRY_OBJECT, bundleEntry);
                    startActivity(openAppDetails);
                }

            }
        });
    }

    /*
        OPTIONS MENU
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        if (feedLimit == 10) {
            menu.findItem(R.id.mnu10).setChecked(true);
        } else {
            menu.findItem(R.id.mnu25).setChecked(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.mnuFree:
                feedURL = getString(R.string.topFreeApps);
                setTitle(String.format(getResources().getString(R.string.topfreeapps), feedLimit));
                break;

            case R.id.mnuPaid:
                feedURL = getString(R.string.topPaidApps);
                setTitle(String.format(getResources().getString(R.string.toppaidapps), feedLimit));
                break;

            case R.id.mnuSongs:
                feedURL = getString(R.string.topSongs);
                setTitle(String.format(getResources().getString(R.string.topsongs), feedLimit));
                break;

            case R.id.mnu10:
            case R.id.mnu25:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    feedLimit = 35 - feedLimit;
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + " setting feedLimit to " + feedLimit);
                } else {
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + " feedLimit unchanged ");
                }
                break;

            case R.id.mnuRefresh:
                feedCachedURL = "INVALIDATED";
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        downloadURL(String.format(feedURL, feedLimit));
        return true;

    }

    /*
        RESTORE ROTATION
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_URL, feedURL);
        outState.putInt(STATE_LIMIT, feedLimit);
        super.onSaveInstanceState(outState);
    }




    /*
        Download URL
     */

    private void downloadURL(String feedURL) {
        Log.d(TAG, "downloadURL: starting AsyncTask");
        if (!feedURL.equalsIgnoreCase(feedCachedURL)) {
            DownloadData downloadData = new DownloadData();
            downloadData.execute(feedURL);
            feedCachedURL = feedURL;
        } else {
            Log.d(TAG, "downloadURL: URL not changed");
        }
        Log.d(TAG, "downloadURL: Done");

    }

    /*
        ASYNC TASK
     */
    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            Log.d(TAG, "onPostExecute: parameter is " + result);
            ParseEntries parseEntries = new ParseEntries();
            parseEntries.parse(result);

            FeedAdapter feedAdapter = new FeedAdapter(TopDownaloderMain.this, R.layout.numbered_apps_list, parseEntries.getEntries());
            xmlListView.setAdapter(feedAdapter);
            feedEntryList = parseEntries.getEntries();

        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with " + strings[0]);

            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }


            return rssFeed;
        }

        /*
            XML DOWNLOADER
         */

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was: " + response);
//                InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);
                //Usual way to do the three lines above
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[512];
                while (true) {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break;
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();

                return xmlResult.toString();

            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data " + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception. Needs permission?" + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }
    }
}
