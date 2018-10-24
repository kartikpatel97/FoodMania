package com.dit.thecampusculinaries;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

@SuppressLint("ValidFragment")
public class QuantityDialog extends DialogFragment {
    private String itemName, itemPrice,activity;
    private  int position;
    private static int quantity=1;


    @SuppressLint("ValidFragment")
    public QuantityDialog(String itemName, String itemPrice,int position,String activity) {
        quantity=1;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.position=position;
        this.activity=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = View.inflate(getActivity(), R.layout.activity_order_dialog, container);
        TextView tvItemName = (TextView) v.findViewById(R.id.tvItemName);
        final TextView tvItemPrice = (TextView) v.findViewById(R.id.tvItemPrice);
        tvItemPrice.setText("\u20B9 "+itemPrice);
        tvItemName.setText(itemName);

        Button btnCancel=(Button)v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        final ElegantNumberButton elQuantity=(ElegantNumberButton)v.findViewById(R.id.quantity);
        elQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                quantity=newValue;
                int newPrice=Integer.parseInt(itemPrice)*newValue;
                tvItemPrice.setText("\u20B9 "+newPrice);
            }
        });

        Button btnAdd=(Button)v.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity.equalsIgnoreCase("danny")) {
                    new Danny().setOrderItem(itemName, quantity, position);
                }else if(activity.equalsIgnoreCase("SweetSpot")) {
                    new SweetSpot().setOrderItem(itemName, quantity, position);
                }else if(activity.equalsIgnoreCase("iceberg")) {
                    new IceBerg().setOrderItem(itemName, quantity, position);
                }
                dismiss();
            }
        });
        return v;
    }
}
