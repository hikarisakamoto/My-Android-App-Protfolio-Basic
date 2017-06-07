package com.hikarisakamoto.myportfoliobasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hikarisakamoto.myportfoliobasic.TopDownloader.TopDownaloderMain;
import com.hikarisakamoto.myportfoliobasic.Video.VideoStarter;
import com.hikarisakamoto.myportfoliobasic.appslist.AppsAdapter;
import com.hikarisakamoto.myportfoliobasic.appslist.AppsList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ListView listApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getResources().getString(R.string.app_name));

        listApps = (ListView) findViewById(R.id.appList);
        AppsList apps = new AppsList(getBaseContext());
        apps.appsListCreator();
        AppsAdapter appsAdapter = new AppsAdapter(MainActivity.this, R.layout.apps_list, apps.getApps());
        listApps.setAdapter(appsAdapter);

        listApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(getBaseContext(), Calculator.class);
                        break;

                    case 1:
                        intent = new Intent(getBaseContext(), TopDownaloderMain.class);
                        break;

                    case 2:
                        intent = new Intent(getBaseContext(), VideoStarter.class);

                        break;

                    default:
                        break;

                }

                if (intent != null) {
                    startActivity(intent);
                }


            }
        });
    }
}
