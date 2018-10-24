package com.dit.thecampusculinaries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Smenu extends AppCompatActivity {
    private ListView lnitems;
    private ItemListAdapter adapter;
    private List<items> mitemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smenu);
        lnitems=findViewById(R.id.imenu);

        mitemlist=new ArrayList<>();
        // Add Sample data
        mitemlist.add(new items(1,"Cold Coffee",100));
    }

}
