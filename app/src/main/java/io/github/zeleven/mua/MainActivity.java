package io.github.zeleven.mua;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import butterknife.BindString;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindString(R.string.app_name)
    String appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // open file list fragment
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment_container, new FileListFragment()).commit();
        // handle search box input
        handelIntent(getIntent());
        // create folder to save files
        createFolder();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handelIntent(intent);
        super.onNewIntent(intent);
    }

    private void handelIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, "关键词：" + query, Toast.LENGTH_SHORT);
            //use the query to search your data somehow
        }
    }

    /**
     * Create folder to save files. If folder doesn't exist create it and log message, otherwise,
     * just log the message.
     */
    private void createFolder() {
        File folder = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + appName);
        if(!folder.exists()) {
            folder.mkdir();
            Log.i(getClass().getName(), "create success");
        } else {
            Log.i(getClass().getName(), "folder already exists");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
