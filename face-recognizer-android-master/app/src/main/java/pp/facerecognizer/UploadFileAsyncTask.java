package pp.facerecognizer;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UploadFileAsyncTask extends AsyncTask<InputStream, Integer, List<UploadFileAsyncTask.Result>> {

    static String apiURL = "http://34.70.111.229:5000/api";

    private UploadFileAsyncTaskCallback<Result> callback;

    UploadFileAsyncTask(UploadFileAsyncTaskCallback<UploadFileAsyncTask.Result> callback) {
        setCallback(callback);
    }

    void setCallback(UploadFileAsyncTaskCallback<UploadFileAsyncTask.Result> callback) {
        this.callback = callback;
    }

    @Override
    protected List<UploadFileAsyncTask.Result> doInBackground(InputStream... streams) {
        final OkHttpClient client = new OkHttpClient();
        ArrayList<UploadFileAsyncTask.Result> results = new ArrayList<UploadFileAsyncTask.Result>();
        byte[] bytes;
        UploadFileAsyncTask.Result result;
        for (InputStream stream : streams) {
            try {
                bytes = IOUtils.toByteArray(stream);
            } catch (Exception e) {
                result = new UploadFileAsyncTask.Result(e);
                results.add(result);
                continue;
            }
            RequestBody fileData = RequestBody.create(bytes);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", "people.png", fileData)
                    .build();
            final Request request = new Request.Builder()
                    .url(apiURL)
                    .post(requestBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    result = new UploadFileAsyncTask.Result(response.body());
                    this.callback.onResponse(result);
                } else {
                    result = new UploadFileAsyncTask.Result(response.message(), response.code());
                }
                results.add(result);
            } catch (Exception e) {
                result = new UploadFileAsyncTask.Result(e);
                results.add(result);
            }
        }
        return results;
    }

    @Override
    protected void onPostExecute(List<Result> results) {
        this.callback.finishDownloading(results);
    }

    static class Result {
        public Integer statusCode;
        public String message;
        public ResponseBody body;
        public Exception exception = null;
        public Boolean isErrorResponse = false;
        public String filename;
        public Result(String message, Integer statusCode) {
            this.message = message;
            this.statusCode = statusCode;
            this.isErrorResponse = true;
        }

        public Result(ResponseBody body) {
            this.body = body;
        }

        public Result(Exception exception) {
            this.exception = exception;
        }

        public Boolean hasException() {
            return exception != null;
        }

        public void setFilename() {

        }

    }
}
