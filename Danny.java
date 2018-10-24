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

public class Danny extends AppCompatActivity {
    private static final String TAG_ACTIVITY = "danny";
    private ListView lnitems;
    private ItemListAdapter iadapter;
    private static List<items> mitemlist;
    private static DefaultHashMap<String,Integer> hmOrderPrices,hmOrderQuantities;
    List<String> itemName,itemPrice,itemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danny);
        inflateActionBar();
        addMenuItems();
    }

    private void addMenuItems() {
        lnitems = findViewById(R.id.dmenu);
        hmOrderPrices=new DefaultHashMap<>(0);
        hmOrderQuantities=new DefaultHashMap<>(0);
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
        iadapter = new ItemListAdapter(Danny.this, mitemlist);
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
        startActivity(new Intent(Danny.this, Dmenu.class));
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
        textviewTitle.setText("Danny's Coffee Bar");
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
