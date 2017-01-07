package com.example.abhishekmadan.mysmartkitchen.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.abhishekmadan.mysmartkitchen.R;
import com.example.abhishekmadan.mysmartkitchen.adapter.RetailersAdapter;
import com.example.abhishekmadan.mysmartkitchen.modal.Grocery_shop;
import com.example.abhishekmadan.mysmartkitchen.modal.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by praveen.mulchandani on 2/12/2016.
 */
public class RetailersFragment extends Fragment {


    private ListView mReatailerListView;
    private ArrayList<Grocery_shop> mGrocery_shopArrayList;
    private RetailersAdapter mRetailersAdapter;
    String Message;

    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Item item = (Item) getArguments().getSerializable("item");
        String iName = item.itemName;
        double quantity = item.itemWeight;
        SharedPreferences preferences = getActivity().getSharedPreferences("address", getActivity().MODE_PRIVATE);

        Message = "Order by:" + preferences.getString("name", "") + ", Item name :" + iName + ", Qty:" + quantity +
                ", Address: " + preferences.getString("street", "") + ", " + preferences.getString("city", "") + ", " + preferences.getString("pincode", "") + ".";

        View view = inflater.inflate(R.layout.retailers_fragment, container, false);
        mReatailerListView = (ListView) view.findViewById(R.id.retailers_ListView);
        mReatailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do yo want to Send Msg to the Retailer to Buy")
                        .setTitle("Confirm Buy?")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        SmsManager sm = SmsManager.getDefault();
                                        sm.sendTextMessage("8421241811", null, Message, null, null);
                                        Toast.makeText(getActivity(), "Message Sent SuccessFully", Toast.LENGTH_LONG).show();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

      /*  getArguments().getSerializable("Item");*/
        mGrocery_shopArrayList = new ArrayList<>();
        try {
            String jsonLocation = AssetJSONFile("retailers.json", getActivity());
            JSONObject retailers = new JSONObject(jsonLocation);
            JSONArray retailersArray = retailers.getJSONArray("grocery_shop");
            for (int i = 0; i < retailersArray.length(); i++) {
                JSONObject jsonObject = retailersArray.getJSONObject(i);
                Log.d("Hell", jsonObject.toString());

                Grocery_shop grocery_shop = new Grocery_shop();
                grocery_shop.setShop_name(jsonObject.getString("shop_name"));
                grocery_shop.setShop_address(jsonObject.getString("shop_address"));
                grocery_shop.setContact_number(jsonObject.getString("contact_number"));
                mGrocery_shopArrayList.add(grocery_shop);
            }
            mRetailersAdapter = new RetailersAdapter(mGrocery_shopArrayList, getActivity());
            mReatailerListView.setAdapter(mRetailersAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


}



