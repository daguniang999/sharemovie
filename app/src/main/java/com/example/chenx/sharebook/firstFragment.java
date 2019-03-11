package com.example.chenx.sharebook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

public class firstFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_1,null);
        return view;
    //    return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       // Log.d("ffff", "onViewCreated: ");
       // super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        //Log.d("ffff", "onDestroyView: ");
        super.onDestroyView();
    }
}
