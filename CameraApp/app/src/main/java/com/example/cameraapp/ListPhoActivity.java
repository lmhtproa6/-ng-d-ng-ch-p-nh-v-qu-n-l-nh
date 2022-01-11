package com.example.cameraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListPhoActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Hinh> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pho);
        lv = (ListView) findViewById(R.id.listViewHinh);

        arrayList = new ArrayList<Hinh>();

        //DATABASE
        SQLite db = new SQLite(getApplicationContext(),"Hinh.sqlite",null,1);
        Cursor hinh = db.GetData("SELECT * FROM Hinh");
        while (hinh.moveToNext()){
            arrayList.add(new Hinh(
                    hinh.getBlob(1),
                    hinh.getString(2)

            ));
        }

        ListAdapter adapter = new ListAdapter(getApplicationContext(),R.layout.activity_detail_pho,arrayList);
        lv.setAdapter(adapter);


    }
}