package com.dit.thecampusculinaries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class IMenu extends AppCompatActivity {
    private ListView lnitems;
    private ItemListAdapter iadapter;
    private List<items> mitemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        lnitems=findViewById(R.id.imenu);


        mitemlist=new ArrayList<>();
        // Add Sample data
        mitemlist.add(new items(1,"Espress",15));
        mitemlist.add(new items(2,"Cafe Americano",20));
        mitemlist.add(new items(3,"Espresso Macchiato",20));
        mitemlist.add(new items(4,"Cappuciano",25));
        mitemlist.add(new items(5,"Cafe Latte",25));
        mitemlist.add(new items(6,"Choco Latte",30));
        mitemlist.add(new items(7,"Espresso con Panna",35));
        mitemlist.add(new items(1,"Cafe Mocha Cappucciano",25));
        mitemlist.add(new items(2,"Coffee Queen",40));
        mitemlist.add(new items(3,"Cool Espresso",40));
        mitemlist.add(new items(4,"Creamy Coffe",40));
        mitemlist.add(new items(5,"Frosted Black Coffee",45));
        mitemlist.add(new items(6,"Iced Latte",30));
        mitemlist.add(new items(7,"Cafe Amaretto",35));
        mitemlist.add(new items(1,"Nutty Delight",40));
        mitemlist.add(new items(2,"Veggie Delight Wrap",40));
        mitemlist.add(new items(3,"Cheese Veggie Delight Wrap",45));
        mitemlist.add(new items(4,"Paneer N Cheese Wrap",50));
        mitemlist.add(new items(5,"Aloo Tikki Burger",30));
        mitemlist.add(new items(6,"Aloo Tikki With Cheese Burger",40));
        mitemlist.add(new items(7,"Veggie Delight Sandwich",40));
        iadapter=new ItemListAdapter(IMenu.this,mitemlist);
        lnitems.setAdapter(iadapter);
    }
}
