package com.example.httppost;

import android.content.res.AssetManager;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileUploader {

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.get("image/png");

    public static void run(InputStream stream) throws Exception {
        final OkHttpClient client = new OkHttpClient();
        byte[] bytes = IOUtils.toByteArray(stream);

        RequestBody fileData = RequestBody.create(bytes);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "people.png", fileData)
                .build();
        final Request request = new Request.Builder()
                .url("http://34.70.111.229:5000/api")
                .post(requestBody)
                .build();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    System.out.println(response.body().string());
                } catch (Exception e) {

                }
            }
        };

        thread.start();

    }
}