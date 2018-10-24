package com.dit.thecampusculinaries;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdminOrderListAdapter extends BaseAdapter {
    private Context mContext;
    private List<AdminOrderItems> mItemList;

    public AdminOrderListAdapter(Context mContext, List<AdminOrderItems> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;

    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return mItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.admin_order_item_list, null);
        TextView tvCulName = (TextView) v.findViewById(R.id.tvCulName);

        if (mItemList.get(i).getOrderFor().equalsIgnoreCase("danny")) {
            tvCulName.setText("Danny's Coffee Bar");
        } else if (mItemList.get(i).getOrderFor().equalsIgnoreCase("iceberg")) {
            tvCulName.setText("Ice Berg");
        } else if (mItemList.get(i).getOrderFor().equalsIgnoreCase("SweetSpot")) {
            tvCulName.setText("The Sweet Spot");
        }
        TextView tvOrderID = (TextView) v.findViewById(R.id.tvOrderId);
        tvOrderID.setText("Order ID: " + mItemList.get(i).getOrderID());
        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        tvName.setText("Name: " + mItemList.get(i).getName());
        TextView tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        tvEmail.setText("Email: " + mItemList.get(i).getEmail());
        TextView tvOrderTotal = (TextView) v.findViewById(R.id.tvTotal);
        tvOrderTotal.setText("Order Total: \u20B9" + mItemList.get(i).getTotal());
        TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText("Received On: " + mItemList.get(i).getDate());
        return v;
    }
}
