package com.example.chenx.sharebook.util;

import com.example.chenx.sharebook.db.Person;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class LitePalUtil {

    public static Person getPerson(){
        List<Person> list=new ArrayList<>();
        list=LitePal.findAll(Person.class);
        if(list.size()!=0) {

            return list.get(0);
        }

        return null;

    }



}
