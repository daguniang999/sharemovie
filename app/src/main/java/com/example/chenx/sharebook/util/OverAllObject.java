package com.example.chenx.sharebook.util;

import android.provider.ContactsContract;

public class OverAllObject {

    private static String HttpAddress="http://106.15.190.83:6767/service.aspx";
    private static String ImageUrl="http://106.15.190.83:6767/";

    public static String getAddress(){

        return HttpAddress;

    }


    public static String getImageUrl(String Name){

        return ImageUrl+Name;
    }

}
