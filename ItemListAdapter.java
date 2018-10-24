package com.dit.thecampusculinaries;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ItemListAdapter extends BaseAdapter {
    private Context mContext;
    private List<items> mItemList;

    public ItemListAdapter(Context mContext, List<items> mItemList) {
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
        View v=View.inflate(mContext,R.layout.item_list,null);
        TextView items=v.findViewById(R.id.dishes);
        TextView price=v.findViewById(R.id.price);

        //set text for TextView
        price.setText(String.valueOf(mItemList.get(i).getPrice())+"/-");
        items.setText(mItemList.get(i).getItem_name());

        // Save Item id to tag
        v.setTag(mItemList.get(i).getId());
        return v;
    }
}
