package com.example.httppost;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements UploadFileAsyncTaskCallback<UploadFileAsyncTask.Result> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            try {
                InputStream is = getAssets().open("people.jpeg");
                new UploadFileAsyncTask(this).execute(is);
                FileUploader.run(is);
            } catch (Exception e) {
                System.out.println("xx");
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(UploadFileAsyncTask.Result result) {
        if( !result.isErrorResponse && !result.hasException()) {
            String path = Environment.getExternalStorageDirectory() + "/" + UUID.randomUUID().toString() +  ".mp3";
            File file = new File(path);
            try {
                FileOutputStream fOut = new FileOutputStream(file);
                byte [] data = result.body.bytes();
                fOut.write(data);
                fOut.close();
                result.filename = path;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void finishDownloading(List<UploadFileAsyncTask.Result> results) {
        System.out.println("finished");

        for(UploadFileAsyncTask.Result result: results) {
            System.out.println(result.filename);
            Uri uri = Uri.fromFile(new File(result.filename));

            MediaPlayer player = MediaPlayer.create(this, uri);
            player.setLooping(false);
            player.setVolume(100, 100);
            player.start();
        }
    }
}
