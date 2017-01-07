package com.example.abhishekmadan.mysmartkitchen.modal;

import java.io.Serializable;

/**
 * Created by abhishek.madan on 2/12/2016.
 */
public class Item implements Serializable{

    public int dbId;
    public int itemId;
    public String itemName;
    public String itemType;
    public double containerDiameter;
    public double containerLevel;
    public double itemWeight;
    public String lastRefillDate;

    @Override
    public String toString() {
        return "Item{" +
                "dbId=" + dbId +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", containerDiameter=" + containerDiameter +
                ", containerLevel=" + containerLevel +
                ", itemWeight=" + itemWeight +
                ", lastRefillDate='" + lastRefillDate + '\'' +
                '}';
    }

}
