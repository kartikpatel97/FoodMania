package com.dit.thecampusculinaries;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Globals;
import utility.PrefManager;

@SuppressLint("ValidFragment")
public class OrderDialog extends DialogFragment {

    List<String> itemName, itemPrice, itemQuantity;
    ListView lvOrderList;
    List<OrderItems> orderItems;
    OrderItemListAdapter adapter;
    static TextView tvTotalAmount;
    String activity;
    private static int amount = 0;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pDialog;
    private String orderKey;

    @SuppressLint("ValidFragment")
    public OrderDialog(List<String> itemName, List<String> itemPrices, List<String> itemQuantity, String activity) {
        this.itemName = itemName;
        this.itemPrice = itemPrices;
        this.itemQuantity = itemQuantity;
        this.activity = activity;
        amount = 0;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = View.inflate(getActivity(), R.layout.activity_order_list_dialog, container);


        orderItems = new ArrayList<>();
        for (int i = 0; i < itemName.size(); i++) {
            orderItems.add(new OrderItems(itemQuantity.get(i), itemName.get(i), itemPrice.get(i)));
            amount += Integer.parseInt(itemPrice.get(i));
        }
        Button btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Button btnAdd = (Button) v.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount > 0) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    mDatabase = FirebaseDatabase.getInstance().getReference(Globals.DB_ORDERS);
                    orderKey = mDatabase.push().getKey();
                    Log.e("KEY", orderKey);
                    Map<String, Object> orderMeta = new HashMap<>();
                    //orderMap.put("id",orderKey);
                    orderMeta.put(Globals.NODE_NAME, new PrefManager(getActivity()).getName());
                    orderMeta.put(Globals.NODE_EMAIL, new PrefManager(getActivity()).getEmailID());
                    orderMeta.put(Globals.NODE_ORDER_FOR, activity);
                    orderMeta.put(Globals.NODE_TOTAL, amount);

                    orderMeta.put(Globals.NODE_DATE, String.valueOf(DateFormat.format("dd-MM-yyyy hh:mm aa", new java.util.Date())));
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(orderKey, orderMeta);
                    mDatabase.updateChildren(childUpdates);

                    Map<String, Object> orderItems = new HashMap<>();
                    for (int i = 0; i < itemName.size(); i++) {

                        orderItems.put(Globals.NODE_PRICE, itemPrice.get(i));
                        orderItems.put(Globals.NODE_QUANTITY, itemQuantity.get(i));
                        Map<String, Object> orderUpdate = new HashMap<>();
                        orderUpdate.put("/" + orderKey + "/" + Globals.NODE_ORDER_ITEMS + "/" + itemName.get(i) + "/", orderItems);
                        mDatabase.updateChildren(orderUpdate);
                    }

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Order Placed!");
                    alertDialog.setMessage("Your order has been successfully placed. Your Order ID is:\n" + orderKey);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    dismiss();
                                    getActivity().finish();
                                }
                            });
                    alertDialog.show();

                } else {
                    Toast.makeText(getActivity(), "Please add some items first!", Toast.LENGTH_LONG).show();
                }
            }
        });
        lvOrderList = (ListView) v.findViewById(R.id.lvOrderItems);
        tvTotalAmount = (TextView) v.findViewById(R.id.tvTotalAmount);
        tvTotalAmount.setText("Order Total: \u20B9" + amount);
        adapter = new OrderItemListAdapter(getActivity(), orderItems, activity);
        lvOrderList.setAdapter(adapter);
        return v;
    }

    public static void recalculateTotal(int price) {
        amount -= price;
        tvTotalAmount.setText("Order Total: \u20B9" + amount);
    }
}
