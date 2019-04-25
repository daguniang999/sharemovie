package com.example.chenx.sharebook.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chenx.sharebook.ActivityCollector;
import com.example.chenx.sharebook.R;

import java.io.File;
import java.io.FileOutputStream;

public class SaveImageUtils extends AsyncTask<Bitmap,Void,String> {

   // Activity mActivity;
    Context context;
    ImageView imageView;

    public SaveImageUtils() {
        super();
    }

    public SaveImageUtils(Context content, ImageView imageView){
        this.context=content;
        this.imageView=imageView;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
        imageView.setDrawingCacheEnabled(false);

        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(Bitmap... bitmaps) {

        String result=context.getResources().getString(R.string.savefail);
        try{
            String sdcard=Environment.getExternalStorageDirectory().toString();
            File file=new File(sdcard+"/sharemovie");
            if(!file.exists()){
                file.mkdirs();
                Log.d("tyty", file.getAbsolutePath());

            }

            File imageFile=new File(file.getAbsolutePath(),System.currentTimeMillis()+".jpg");
            FileOutputStream outputStream=null;
            outputStream=new FileOutputStream(imageFile);
            Bitmap image=bitmaps[0];
            image.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();

            result=context.getResources().getString(R.string.savesuccess,file.getAbsolutePath());

        }catch (Exception e){
            e.printStackTrace();
            Log.d("erer", e.toString());
        }


        return result;
    }
}
