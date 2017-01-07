package com.example.abhishekmadan.mysmartkitchen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.abhishekmadan.mysmartkitchen.R;
import com.example.abhishekmadan.mysmartkitchen.modal.ClassConstant;
import com.example.abhishekmadan.mysmartkitchen.modal.Item;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by abhishek.madan on 2/12/2016.
 */
public class ItemListAdapter extends BaseAdapter
{
    private ArrayList mItemList;
    private Context mContext;
    private LayoutInflater inflater;

    public ItemListAdapter( Context context,ArrayList<Item> itemList){
         mContext = context;
         mItemList = itemList;
         inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder myHolder;
        double vol;
        double mass;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.activity_list_view_single_row,parent,false);
            myHolder = new Holder();
            myHolder.itemCode = (TextView) convertView.findViewById(R.id.item_code_textview);
            myHolder.itemName = (TextView) convertView.findViewById(R.id.item_name_textview);
            myHolder.quantity = (TextView) convertView.findViewById(R.id.current_qty_textview);
            myHolder.lastPurchaseDate = (TextView) convertView.findViewById(R.id.last_purchase_date_textview);
            convertView.setTag(myHolder);
        }
        myHolder = (Holder) convertView.getTag();

        Item obj = (Item) mItemList.get(position);
        myHolder.itemCode.setText(String.valueOf(obj.itemId));
        myHolder.itemName.setText(String.valueOf(obj.itemName));

       //logic for checking the threshold : liquid
        if(obj.itemType.equalsIgnoreCase("Liquid")){
             //vol = 3.14*Math.pow((obj.containerDiameter/2),2)*obj.containerLevel;
               vol = Math.floor(3.14*Math.pow((obj.containerDiameter/2),2)*obj.containerLevel);
               if(vol>1000)
                 myHolder.quantity.setText(String.valueOf(vol/1000)+" L");
               else
                 myHolder.quantity.setText(String.valueOf(vol)+" ml");
             if(vol< ClassConstant.VOLUME_THRESHOLD){
                 convertView.setBackgroundResource(R.drawable.defaulter);
             }else{
                 convertView.setBackgroundResource(R.drawable.white_back);
             }
        }else if(obj.itemType.equalsIgnoreCase("Solid")){
            //mass = obj.itemWeight;
              mass = Math.floor(obj.itemWeight);
              if(mass>1000) {
                  myHolder.quantity.setText(String.valueOf(mass/1000)+" Kg");
              }else{
                  myHolder.quantity.setText(String.valueOf(mass)+" g");
              }
            if(mass<ClassConstant.MASS_THRESHOLD){
                convertView.setBackgroundResource(R.drawable.defaulter);
            }else{
                convertView.setBackgroundResource(R.drawable.white_back);
            }
        }
        myHolder.lastPurchaseDate.setText(obj.lastRefillDate.replace("GMT+05:30",""));

        return convertView;
    }

    static class Holder{

        public TextView itemCode;
        public TextView itemName;
        public TextView quantity;
        public TextView lastPurchaseDate;


    }

  /*  public String convertToDate(String date){

        DateFormat format = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
        try {
            Date convDate = format.parse(date);
            return convDate.getDay()+" "+convDate.getMonth();
        }catch (ParseException e){
            return date.substring(0,5);
        }
    }*/

}
