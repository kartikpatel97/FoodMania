package com.dit.thecampusculinaries;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import common.Globals;
import utility.PrefManager;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ListView lvOrders;
    AdminOrderListAdapter adapter;
    private DatabaseReference mDatabase;
    private List<AdminOrderItems> mOrderList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateActionBar();
        setContentView(R.layout.activity_admin);
        lvOrders = (ListView) findViewById(R.id.lvOrders);
        firebaseAuth = FirebaseAuth.getInstance();
        mOrderList = new ArrayList<>();
        adapter = new AdminOrderListAdapter(AdminActivity.this, mOrderList);
        lvOrders.setAdapter(adapter);
        lvOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(AdminActivity.this, AdminOrderDetailActivity.class);
                ArrayList<String> itemName=new ArrayList<>();
                ArrayList<String> itemPrice=new ArrayList<>();
                ArrayList<String> itemQuantity=new ArrayList<>();

                for(int j=0;j<mOrderList.get(position).getOrderItems().size();j++){
                    itemName.add(mOrderList.get(position).getOrderItems().get(j).getItem_name());
                    itemPrice.add(mOrderList.get(position).getOrderItems().get(j).getPrice());
                    itemQuantity.add(mOrderList.get(position).getOrderItems().get(j).getQuantity());
                }
                i.putStringArrayListExtra("itemName",itemName);
                i.putStringArrayListExtra("itemPrice",itemPrice);
                i.putStringArrayListExtra("itemQuantity",itemQuantity);
                i.putExtra("orderFor", mOrderList.get(position).getOrderFor());
                i.putExtra("orderID", mOrderList.get(position).getOrderID());
                i.putExtra("name", mOrderList.get(position).getName());
                i.putExtra("email", mOrderList.get(position).getEmail());
                i.putExtra("date", mOrderList.get(position).getDate());
                i.putExtra("total", mOrderList.get(position).getTotal());
                startActivity(i);
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference(Globals.DB_ORDERS);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mOrderList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    //Log.e("KEY", key);
                    String name = ds.child(Globals.NODE_NAME).getValue().toString();
                    String email = ds.child(Globals.NODE_EMAIL).getValue().toString();
                    String orderFor = ds.child(Globals.NODE_ORDER_FOR).getValue().toString();
                    String orderTotal = ds.child(Globals.NODE_TOTAL).getValue().toString();
                    String date = ds.child(Globals.NODE_DATE).getValue().toString();


                    List<OrderItems> listOrderItems=new ArrayList<>();
                    DataSnapshot itemSnapshot = ds.child(Globals.NODE_ORDER_ITEMS);
                    Iterable<DataSnapshot> itemChildren = itemSnapshot.getChildren();
                    for (DataSnapshot item : itemChildren) {

                        String itemName = item.getKey();
                        String itemPrice = item.child(Globals.NODE_PRICE).getValue().toString();
                        String itemQuantity = item.child(Globals.NODE_QUANTITY).getValue().toString();
                        listOrderItems.add(new OrderItems(itemQuantity,itemName,itemPrice));
                        //Log.e("ORDER ITEMS", itemName + "-" + itemPrice + "-" + itemQuantity);

                    }
                    mOrderList.add(0,new AdminOrderItems(listOrderItems,orderFor,name,email,orderTotal,key,date));

                    //Log.e("ORDERS", key + "-" + name + "-" + email + "-" + orderFor + "-" + orderTotal);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void inflateActionBar() {
        final ActionBar abar = getSupportActionBar();
        abar.show();
        abar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B71C1C")));
        ;//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar_titletext_layout_back, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Orders");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        Toolbar parent = (Toolbar) viewActionBar.getParent();
        parent.setContentInsetsAbsolute(0, 0);

    }

    private void Logout() {
        new PrefManager(AdminActivity.this).removeSharedPreferences();
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(AdminActivity.this, MainActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
