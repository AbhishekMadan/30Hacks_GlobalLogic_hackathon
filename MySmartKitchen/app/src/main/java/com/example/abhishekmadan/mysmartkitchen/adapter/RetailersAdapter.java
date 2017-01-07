package com.example.abhishekmadan.mysmartkitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.abhishekmadan.mysmartkitchen.R;
import com.example.abhishekmadan.mysmartkitchen.modal.Grocery_shop;

import java.util.ArrayList;

/**
 * Created by praveen.mulchandani on 2/12/2016.
 */
public class RetailersAdapter extends BaseAdapter {

    private ArrayList<Grocery_shop> mGrosseryShopList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;


    public RetailersAdapter(ArrayList<Grocery_shop> mGrosseryShopList, Context mContext) {
        this.mGrosseryShopList = mGrosseryShopList;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mGrosseryShopList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGrosseryShopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.retailer_row_layout, null, false);
            holder.mNameoftheRetailer = (TextView) convertView.findViewById(R.id.retailer_name);
            holder.mPhoneNumberoftheRetailer = (TextView) convertView.findViewById(R.id.retailer_phone_number);
            holder.mAdddressOftheRetailer = (TextView) convertView.findViewById(R.id.retailer_address_textView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mNameoftheRetailer.setText(mGrosseryShopList.get(position).getShop_name());
        holder.mAdddressOftheRetailer.setText(mGrosseryShopList.get(position).getShop_address());
        holder.mPhoneNumberoftheRetailer.setText(mGrosseryShopList.get(position).getContact_number());
        return convertView;
    }

    private static class ViewHolder {
        private TextView mNameoftheRetailer;
        private TextView mPhoneNumberoftheRetailer;
        private TextView mAdddressOftheRetailer;
    }
}
