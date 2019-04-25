package com.example.chenx.sharebook.util;

import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;

public class OverAllObject {

    private static String HttpAddress="http://106.15.190.83:6767/service.aspx";
    private static String ImageUrl="http://106.15.190.83:6767/";
    private static String Name;
    public static String getAddress(){

        return HttpAddress;

    }

    public static String getName(){
        return Name;
    }

    public static void setName(String name){
        Name=name;
    }

    public static String getImageUrl(String Name){

        return ImageUrl+Name;
    }


}
