package com.example.abhishekmadan.mysmartkitchen.modal;

/**
 * Created by praveen.mulchandani on 2/12/2016.
 */
public class Grocery_shop
{
    private String contact_number;

    private String shop_address;

    private String shop_name;

    public String getContact_number ()
    {
        return contact_number;
    }

    public void setContact_number (String contact_number)
    {
        this.contact_number = contact_number;
    }

    public String getShop_address ()
    {
        return shop_address;
    }

    public void setShop_address (String shop_address)
    {
        this.shop_address = shop_address;
    }

    public String getShop_name ()
    {
        return shop_name;
    }

    public void setShop_name (String shop_name)
    {
        this.shop_name = shop_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [contact_number = "+contact_number+", shop_address = "+shop_address+", shop_name = "+shop_name+"]";
    }
}

