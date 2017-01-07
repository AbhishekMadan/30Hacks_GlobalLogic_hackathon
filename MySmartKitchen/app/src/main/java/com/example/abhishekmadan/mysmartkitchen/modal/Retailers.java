package com.example.abhishekmadan.mysmartkitchen.modal;

/**
 * Created by praveen.mulchandani on 2/12/2016.
 */
public class Retailers
{
    private Grocery_shop[] grocery_shop;

    public Grocery_shop[] getGrocery_shop ()
    {
        return grocery_shop;
    }

    public void setGrocery_shop (Grocery_shop[] grocery_shop)
    {
        this.grocery_shop = grocery_shop;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [grocery_shop = "+grocery_shop+"]";
    }
}
