package com.example.chenx.sharebook;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.load.model.ModelCache;
import com.example.chenx.sharebook.util.HttpUtil;
import com.example.chenx.sharebook.util.LitePalUtil;
import com.example.chenx.sharebook.util.UploadUtil;

import java.io.File;
import java.sql.BatchUpdateException;

public class MovieAddActivity extends AppCompatActivity implements View.OnClickListener {


    private Button uploadImage;
    private ImageView imageView;
    private String picPath;
    private ImageView addPic;
    private EditText moviename;
    private EditText moviesummary;
    private Spinner spinner;
    public static final int UPLOAD_PHOTO=2;
    public static final int CHOOSE_PHOTO=1;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"MUST PASS",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.imageView:
                if(ContextCompat.checkSelfPermission(MovieAddActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MovieAddActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
                break;

            case R.id.add_pic:
                if(ContextCompat.checkSelfPermission(MovieAddActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MovieAddActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }

                break;
            case  R.id.uploadImage:
                if(picPath==null||moviename.getText().equals("")||moviesummary.getText().equals("")){
                    Toast.makeText(MovieAddActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();

                }else {
                    Bitmap bitmap=BitmapFactory.decodeFile(picPath);
                    File file=new File(picPath);
                    String URL="http://192.168.0.123:6767/service.aspx";
                    String PicName="img_"+System.currentTimeMillis();
                    String name=moviename.getText().toString();
                    String summary=moviesummary.getText().toString();
                    String uploader=LitePalUtil.getPerson().getName();
                    String type=spinner.getSelectedItem().toString();
                    UploadUtil.uploadPic(file,URL,PicName,name,uploader,type,summary,MovieAddActivity.this);


                }

                break;
            default:
                break;

        }


    }

    public void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        Log.d("eeee", uri.toString());
        if(DocumentsContract.isDocumentUri(MovieAddActivity.this,uri)) {

            String str=uri.toString();
            str=str.split("%3A")[1];
            str="content://media/external/images/media/"+str;
            uri=Uri.parse(str);

            imagePath=getImagePath(uri,null);
        }

        else if("content".equalsIgnoreCase(uri.getScheme())){

            imagePath=getImagePath(uri,null);

        }else if("file".equalsIgnoreCase(uri.getScheme())){

            imagePath=uri.getPath();
        }
        addPic.setVisibility(View.INVISIBLE);
        //addPic.
        displayImage(imagePath);


    }

    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        picPath=path;
        return path;

    }

    private void displayImage(String imagePath){

        if(imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        }else {
            Toast.makeText(MovieAddActivity.this,"faild",Toast.LENGTH_SHORT).show();
        }

    }
    public void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case CHOOSE_PHOTO:

            if (resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    handleImageOnKitKat(data);

                } else {
                    handleImageBeforeKitKat(data);
                }

            }
            break;

            default:
                break;


        }


    }



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_add);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.comment_toolbar);
            setSupportActionBar(toolbar);
        }

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        uploadImage=(Button)findViewById(R.id.uploadImage);
        imageView=(ImageView)findViewById(R.id.imageView);
        addPic=(ImageView)findViewById(R.id.add_pic);
        moviename=(EditText)findViewById(R.id.movie_name);
        moviesummary=(EditText)findViewById(R.id.movie_summary);
        spinner=(Spinner)findViewById(R.id.spinner_type);
        uploadImage.setOnClickListener(this);
        addPic.setOnClickListener(this);
        imageView.setOnClickListener(this);





    }





}