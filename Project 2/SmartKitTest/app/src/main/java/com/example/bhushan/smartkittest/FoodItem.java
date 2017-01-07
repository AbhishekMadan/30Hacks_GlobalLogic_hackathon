package com.example.bhushan.smartkittest;

/**
 * Created by bhushan.raut on 2/12/2016.
 */
public class FoodItem {
    String mItemId;

    public FoodItem(String mItemId, String mItemName, String mItemType) {
        this.mItemId = mItemId;
        this.mItemName = mItemName;
        this.mItemType = mItemType;
    }

    String mItemName;

    public String getmItemId() {
        return mItemId;
    }

    public void setmItemId(String mItemId) {
        this.mItemId = mItemId;
    }

    public String getmItemName() {
        return mItemName;
    }

    public void setmItemName(String mItemName) {
        this.mItemName = mItemName;
    }

    public String getmItemType() {
        return mItemType;
    }

    public void setmItemType(String mItemType) {
        this.mItemType = mItemType;
    }

    String mItemType;


}
