package com.example.chenx.sharebook.util;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chenx.sharebook.MainActivity;
import com.example.chenx.sharebook.MovieAddActivity;
import com.example.chenx.sharebook.MyApplication;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.net.sip.SipErrorCode.TIME_OUT;
import static android.provider.Telephony.Mms.Part.CHARSET;

public class UploadUtil {
    private static ProgressDialog progressBar;

    public static void uploadPic(File file, String RequestURL, String PicName, String movieName, String Uploader, final String Type, String Summary, final Activity activity) {

        MultipartBody.Builder builder=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name",movieName)
                .addFormDataPart("summary",Summary)
                .addFormDataPart("uploader",Uploader)
                .addFormDataPart("type",Type)
                .addFormDataPart("files",PicName+".jpg",
                    RequestBody.create(MediaType.parse("image/*"),file));
        progressBar=new ProgressDialog(activity);
        progressBar.setTitle("发布");
        progressBar.setMessage("Loading...");
        progressBar.setCancelable(false);
        progressBar.show();


        HttpUtil.sendMultipart(RequestURL, builder.build(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("eeee", "onFailure: uploadfail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("eeee", "onResponse: uploadsuccess");
                Log.d("eeee", response.body().string());
                progressBar.dismiss();

                android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(activity,"发布成功",Toast.LENGTH_SHORT).show();

                    }
                });
                activity.finish();
            }
        });



    }


}
