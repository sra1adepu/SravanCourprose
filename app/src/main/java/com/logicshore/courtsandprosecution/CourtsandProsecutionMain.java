package com.logicshore.courtsandprosecution;

/**
 * Created by admin on 23-11-2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;


public class CourtsandProsecutionMain extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courtsandprosecutionmain);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        ArrayList names = new ArrayList<>(Arrays.asList("Charge Sheet Status", "Court Commital", "Court Transfer", "Court Case Dairy"));
        ArrayList images = new ArrayList<>(Arrays.asList(R.mipmap.chargesheetstatus, R.mipmap.courtcommital, R.mipmap.courttransfer, R.mipmap.courtcasediary));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CourtsandProsecutionMain.this,3);
        recyclerView.setLayoutManager(gridLayoutManager);

        CustomRecyclierAdapter customRecyclierAdapter= new CustomRecyclierAdapter(CourtsandProsecutionMain.this,names,images);
        recyclerView.setAdapter(customRecyclierAdapter);

    }

}