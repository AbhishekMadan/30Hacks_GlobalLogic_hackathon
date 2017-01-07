package com.example.abhishekmadan.mysmartkitchen.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishekmadan.mysmartkitchen.R;
import com.example.abhishekmadan.mysmartkitchen.database.DatabaseProperties;
import com.example.abhishekmadan.mysmartkitchen.modal.Item;

/**
 * Created by praveen.mulchandani on 2/12/2016.
 */
public class BuyItemFragment extends Fragment {

    private Button mBuyButton;
    private TextView mItemName;
    private TextView mLastPurchaseDate;
    private EditText mQuantityTextView;
    private Bundle bundle;
    private Item obj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.buy_item_fragment,null,false);
        mBuyButton = (Button) view.findViewById(R.id.Buy_Button);
        mItemName = (TextView) view.findViewById(R.id.Item_Name);
        mLastPurchaseDate = (TextView) view.findViewById(R.id.last_purchase_date_tv);
        mQuantityTextView = (EditText) view.findViewById(R.id.order_quantity_tv);
        bundle = getArguments();
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempItemName = mItemName.getText().toString();
                if(mQuantityTextView.getText().toString().length()>0)
                {
                    InputMethodManager inputManager = (InputMethodManager)
                            getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    long tempIitemWeight =(long) Double.parseDouble(mQuantityTextView.getText().toString().trim());
                    if(tempIitemWeight>0) {
                        RetailersFragment retailersFragment = new RetailersFragment();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        Bundle bundle = new Bundle();

                        Item item = new Item();
                        item.itemName = tempItemName;
                        item.itemWeight = tempIitemWeight;
                        bundle.putSerializable("item",item);
                        retailersFragment.setArguments(bundle);

                        transaction.replace(R.id.Container,retailersFragment).addToBackStack(null);
                        transaction.commit();
                    }
                }

            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBuyItemFragment();

    }

    public void getBuyItemFragment(){
        int mItemId;
        String itemName = null;
        String itemType = null;
        String itemPurchaseDate = null;
        double containerDia = 0;
        double containerHeight = 0;
        double itemWeight = 0;
        double quantity = 0;
        Cursor cursor = null;
        if(bundle!=null){
            obj = (Item) bundle.getSerializable("item");
            mItemId = obj.itemId;
            String selArgs[] = new String[]{String.valueOf(mItemId) };
            cursor = getActivity().getContentResolver()
                    .query(DatabaseProperties.CONTENT_URI,null,DatabaseProperties.ITEM_ID+"=?",selArgs,null);
        }
        if(cursor!=null){
            cursor.moveToFirst();
            itemName = cursor.getString(cursor.getColumnIndex(DatabaseProperties.ITEM_NAME));
            itemType = cursor.getString(cursor.getColumnIndex(DatabaseProperties.ITEM_TYPE));
            if(itemType.equalsIgnoreCase("Solid")){
                itemWeight = cursor.getDouble(cursor.getColumnIndex(DatabaseProperties.ITEM_WEIGHT));
                quantity = itemWeight;

            }else if(itemType.equalsIgnoreCase("Liquid")){
                containerDia = cursor.getDouble(cursor.getColumnIndex(DatabaseProperties.CONTAINER_DIAMETER));
                containerHeight = cursor.getDouble(cursor.getColumnIndex(DatabaseProperties.CONTAINER_HEIGHT));
                quantity = 3.14*Math.pow((containerDia/2),2)*containerHeight;
            }
            itemPurchaseDate = cursor.getString(cursor.getColumnIndex(DatabaseProperties.LAST_REFILL_DATE));


            //inserting data in the form
            mItemName.setText(String.valueOf(itemName));
            mLastPurchaseDate.setText(String.valueOf(itemPurchaseDate));
           // mQuantityTextView.setText(String.valueOf(quantity));
        }
    }
}
