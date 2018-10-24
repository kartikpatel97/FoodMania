package com.dit.thecampusculinaries;


import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;

public class Dmenu extends AppCompatActivity {
    private ListView lnitems;
    private ItemListAdapter iadapter;
    private List<items> mitemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmenu);
        lnitems = findViewById(R.id.dmenu);

        mitemlist = new ArrayList<>();
        // Add Sample data
        mitemlist.add(new items(1, "Cold Coffee", 40));
        mitemlist.add(new items(2, "Cold Coffee with Topping", 40));
        mitemlist.add(new items(3, "Bournvita", 40));
        mitemlist.add(new items(4, "Bournvita with Topping", 40));
        mitemlist.add(new items(5, "Espresso Coffee", 40));
        mitemlist.add(new items(6, "Hot Bournvita", 40));
        mitemlist.add(new items(7, "Masala Maggi", 30));
        mitemlist.add(new items(8, "Veg. Masala Maggi", 40));
        mitemlist.add(new items(9, "Butter Maggi", 50));
        mitemlist.add(new items(10, "Cheese Maggi", 50));
        mitemlist.add(new items(11, "Veg. Cheese Maggi", 60));
        mitemlist.add(new items(12, "Bread Butter Toast", 30));
        mitemlist.add(new items(13, "Bread Toast", 60));
        mitemlist.add(new items(14, "Cheese Chilly Toast", 60));
        mitemlist.add(new items(15, "Cheese Chilly Garlic Toast", 60));
        mitemlist.add(new items(16, "Supreme Cheese Garlic Bread", 60));
        mitemlist.add(new items(17, "Masala Bread", 70));
        mitemlist.add(new items(18, "Premium Bread", 70));
        mitemlist.add(new items(19, "Italian Pizza", 60));
        mitemlist.add(new items(20, "Double Cheese Pizza", 70));
        mitemlist.add(new items(21, "Chilly Garlic Pizza", 70));
        iadapter = new ItemListAdapter(Dmenu.this, mitemlist);
        lnitems.setAdapter(iadapter);
        lnitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder mBuilder= new AlertDialog.Builder(Dmenu.this);
                View mView=getLayoutInflater().inflate(R.layout.activity_order_dialog,null);
                final ElegantNumberButton btn_quantity =mView.findViewById(R.id.quantity);
                final Button btn_ok=mView.findViewById(R.id.btnAdd);
                btn_quantity.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String num=btn_quantity.getNumber();
                        Log.e("Num",num);
                    }
                });
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(new Intent(Dmenu.this,Order.class ));
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog=mBuilder.create();
                dialog.show();
            }
        });
    }
}