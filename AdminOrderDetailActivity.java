package com.dit.thecampusculinaries;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderDetailActivity extends AppCompatActivity {
    List<OrderItems> orderItems;
    List<String> itemName, itemPrice, itemQuantity;
    ListView lvOrderList;
    OrderItemListAdapter adapter;
    private String orderFor,orderID,name,email,date,total;
    TextView tvOrderID,tvName,tvEmail,tvDate,tvTotal,tvCulName;
    Button btnDone;
    private static int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateActionBar();
        setContentView(R.layout.admin_order_detail_activity);
        amount=0;
        itemName=new ArrayList<>();
        itemPrice=new ArrayList<>();
        itemQuantity=new ArrayList<>();

        itemName=getIntent().getStringArrayListExtra("itemName");
        itemPrice=getIntent().getStringArrayListExtra("itemPrice");
        itemQuantity=getIntent().getStringArrayListExtra("itemQuantity");

        orderFor=getIntent().getStringExtra("orderFor");
        orderID=getIntent().getStringExtra("orderID");
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        date=getIntent().getStringExtra("date");
        total=getIntent().getStringExtra("total");

        tvCulName=(TextView)findViewById(R.id.tvCulName);
        if (orderFor.equalsIgnoreCase("danny")) {
            tvCulName.setText("Danny's Coffee Bar");
        } else if (orderFor.equalsIgnoreCase("iceberg")) {
            tvCulName.setText("Ice Berg");
        } else if (orderFor.equalsIgnoreCase("SweetSpot")) {
            tvCulName.setText("The Sweet Spot");
        }

        tvOrderID=(TextView)findViewById(R.id.tvOrderId);
        tvOrderID.setText("Order ID: "+orderID);

        tvName=(TextView)findViewById(R.id.tvName);
        tvName.setText("Name: "+name);

        tvEmail=(TextView)findViewById(R.id.tvEmail);
        tvEmail.setText("Email: "+email);

        tvDate=(TextView)findViewById(R.id.tvDate);
        tvDate.setText("Received On: "+date);

        tvTotal=(TextView)findViewById(R.id.tvTotal);
        tvTotal.setText("Order Total: \u20B9"+total);

        btnDone=(Button)findViewById(R.id.btnAdd);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        orderItems = new ArrayList<>();
        for (int i = 0; i < itemName.size(); i++) {
            orderItems.add(new OrderItems(itemQuantity.get(i), itemName.get(i), itemPrice.get(i)));
            amount += Integer.parseInt(itemPrice.get(i));
        }

        lvOrderList = (ListView) findViewById(R.id.lvOrderItems);

        adapter = new OrderItemListAdapter(AdminOrderDetailActivity.this, orderItems, "admin");
        lvOrderList.setAdapter(adapter);
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
        textviewTitle.setText("Order Detail");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        Toolbar parent = (Toolbar) viewActionBar.getParent();
        parent.setContentInsetsAbsolute(0, 0);

    }

}
