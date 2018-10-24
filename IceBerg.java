package com.dit.thecampusculinaries;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utility.DefaultHashMap;

public class IceBerg extends AppCompatActivity {
    private static final String TAG_ACTIVITY = "iceberg";
    private ListView lnitems;
    private ItemListAdapter iadapter;
    private static List<items> mitemlist;
    private static DefaultHashMap<String,Integer> hmOrderPrices,hmOrderQuantities;
    List<String> itemName,itemPrice,itemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ice_berg);
        inflateActionBar();
        addMenuItems();
    }

    private void addMenuItems() {
        lnitems = findViewById(R.id.dmenu);
        hmOrderPrices=new DefaultHashMap<>(0);
        hmOrderQuantities=new DefaultHashMap<>(0);
        mitemlist = new ArrayList<>();
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
        iadapter = new ItemListAdapter(IceBerg.this, mitemlist);
        lnitems.setAdapter(iadapter);
        lnitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QuantityDialog qd = new QuantityDialog(mitemlist.get(position).getItem_name(), String.valueOf(mitemlist.get(position).getPrice()),position,TAG_ACTIVITY);
                qd.show(getFragmentManager(),"QuantityDialog");
            }
        });
    }

    public void dmenu(View v) {
        startActivity(new Intent(IceBerg.this, Dmenu.class));
    }

    private void inflateActionBar() {
        final ActionBar abar = getSupportActionBar();
        abar.show();
        abar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B71C1C")));
        ;//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar_titletext_layout_order, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Ice Berg");
        ImageView ivOrderList=(ImageView)viewActionBar.findViewById(R.id.ivOrderList);
        ivOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemName= new ArrayList<>();
                itemPrice=new ArrayList<>();
                itemQuantity=new ArrayList<>();
                for(int i=0;i<mitemlist.size();i++){
                    if(hmOrderPrices.containsKey(mitemlist.get(i).getItem_name())){
                        itemName.add(mitemlist.get(i).getItem_name());
                        itemPrice.add(String.valueOf(hmOrderPrices.get(mitemlist.get(i).getItem_name())));
                        itemQuantity.add(String.valueOf(hmOrderQuantities.get(mitemlist.get(i).getItem_name())));
                    }
                }
                OrderDialog orderDialog=new OrderDialog(itemName,itemPrice,itemQuantity,TAG_ACTIVITY);
                orderDialog.show(getFragmentManager(),"orderDialog");
            }
        });
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        Toolbar parent = (Toolbar) viewActionBar.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }

    public void setOrderItem(String itemName, int quantity ,int position){
        int oldQuantity=hmOrderQuantities.get(itemName);
        int newQuantity=oldQuantity+quantity;
        int newPrice=mitemlist.get(position).getPrice()*newQuantity;
        hmOrderQuantities.put(itemName,newQuantity);
        hmOrderPrices.put(itemName,newPrice);

    }

    public void removeOrderItem(String itemName) {
        Log.e("Remove Item",itemName);
        hmOrderQuantities.remove(itemName);
        hmOrderPrices.remove(itemName);

    }
}
