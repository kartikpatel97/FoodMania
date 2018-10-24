package com.dit.thecampusculinaries;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class OrderItemListAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderItems> mItemList;
    private String activity;

    public OrderItemListAdapter(Context mContext, List<OrderItems> mItemList, String activity) {
        this.mContext = mContext;
        this.mItemList = mItemList;
        this.activity = activity;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.order_item_list, null);
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!activity.equalsIgnoreCase("admin")) {
                    if (activity.equalsIgnoreCase("danny")) {
                        new Danny().removeOrderItem(mItemList.get(i).getItem_name());
                        OrderDialog.recalculateTotal(Integer.parseInt(mItemList.get(i).getPrice()));
                        mItemList.remove(i);
                        notifyDataSetChanged();
                    } else if (activity.equalsIgnoreCase("SweetSpot")) {
                        new SweetSpot().removeOrderItem(mItemList.get(i).getItem_name());
                        OrderDialog.recalculateTotal(Integer.parseInt(mItemList.get(i).getPrice()));
                        mItemList.remove(i);
                        notifyDataSetChanged();
                    } else if (activity.equalsIgnoreCase("iceberg")) {
                        new IceBerg().removeOrderItem(mItemList.get(i).getItem_name());
                        OrderDialog.recalculateTotal(Integer.parseInt(mItemList.get(i).getPrice()));
                        mItemList.remove(i);
                        notifyDataSetChanged();
                    }
                }
                    return false;

        }
    });
    TextView tvQuantity = v.findViewById(R.id.tvQuantity);
    TextView tvItemName = v.findViewById(R.id.tvItemName);
    TextView tvItemPrice = v.findViewById(R.id.tvItemPrice);

    //set text for TextView
        tvQuantity.setText(mItemList.get(i).

    getQuantity() +" x ");
        tvItemPrice.setText("\u20B9 "+String.valueOf(mItemList.get(i).

    getPrice()));
        tvItemName.setText(mItemList.get(i).

    getItem_name());

    // Save Item id to tag
        v.setTag(i);
        return v;
}
}
