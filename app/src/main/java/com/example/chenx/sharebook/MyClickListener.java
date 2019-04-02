package com.example.chenx.sharebook;

import android.content.Intent;
import android.graphics.PointF;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.logging.Handler;

public class MyClickListener implements View.OnTouchListener {

    private MyClickCallBack clickCallBack;
    private int clickCount=0;
    private android.os.Handler handler;
    private long timeout=400;
    private static final int CLICK=0;
    private static final int MOVE=1;
    private static final int DOWN=2;
    private static final int LONG=3;
    private PointF startPoint;
    private PointF nowPoint;

    private int Count=0;

    private static int mode;

    public interface MyClickCallBack{
        void oneClick();
        void doubleClick();
        void longClick();

    }

    public MyClickListener(MyClickCallBack clickCallBack){

        this.clickCallBack=clickCallBack;
        handler=new android.os.Handler();

    }

    private float distance(PointF startPoint,PointF nowPoint){
        float x=startPoint.x-nowPoint.x;
        float y=startPoint.y-nowPoint.y;
        return  Float.valueOf(String.valueOf(Math.sqrt(x*x+y*y)));

    }



    @Override
    public boolean onTouch(View v, final MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mode=DOWN;
                startPoint =new PointF(event.getX(), event.getY());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(mode==DOWN){
                            clickCallBack.longClick();
                            Log.d("erte", "longclick ");
                            mode=LONG;

                        }else {
                            Log.d("erte", "no ok ");
                        }

                    }
                }, 700);
                Log.d("erte", "down ");
                break;
            case MotionEvent.ACTION_MOVE:
                nowPoint=new PointF(event.getX(),event.getY());
                float dis=distance(startPoint,nowPoint);
                if(dis>0){
                    mode=MOVE;
                   // Log.d("erte", "move:"+dis);
                }
                Log.d("erte", "move:"+dis);
                break;
            case MotionEvent.ACTION_UP:

                if(mode==DOWN) {
                    clickCount++;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mode==CLICK) {
                                if (clickCount == 1) {
                                    clickCallBack.oneClick();
                                } else if (clickCount == 2) {
                                    clickCallBack.doubleClick();

                                }
                            }

                            clickCount = 0;
                        }
                    }, timeout);
                    mode=CLICK;

                }else {
                    Log.d("erte", "up: ");
                    mode=CLICK;
                }
                break;
        }



        return true;
    }



}
