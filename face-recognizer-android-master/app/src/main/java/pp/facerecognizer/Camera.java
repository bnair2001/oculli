package pp.facerecognizer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

public class Camera extends AppCompatActivity {

    private static final int CONTENT_REQUEST=1337;
    private File output=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);


        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir=
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        output=new File(dir, "CameraContentDemo.jpeg");
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));

        startActivityForResult(i, CONTENT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == CONTENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.e("onActivityResult", output.getPath());
                try {
                    FileInputStream fileInputStream = new FileInputStream(output);
                    new UploadFileAsyncTask((UploadFileAsyncTaskCallback<UploadFileAsyncTask.Result>) this).execute(fileInputStream);
                } catch (Exception e) {
                    Log.e("onActivityResult", output.getPath(),e);
                }

                Intent i=new Intent(Intent.ACTION_VIEW);

                 i.setDataAndType(Uri.fromFile(output), "image/jpeg");

                startActivity(i);

                finish();
            }
        }
    }
}

