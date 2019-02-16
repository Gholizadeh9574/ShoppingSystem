package amirhosssein.shoppingsystem.models;

import android.content.ContentValues;

import java.io.Serializable;

import amirhosssein.shoppingsystem.database.WareDB;

public class Ware implements Serializable{


    private int ID;
    private String Name;
    private int Price;
    private int Stock;
    private int inHolding;
    private int wareGroupID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public int getInHolding() {
        return inHolding;
    }

    public void setInHolding(int inHolding) {
        this.inHolding = inHolding;
    }

    public int getWareGroupID() {
        return wareGroupID;
    }

    public void setWareGroupID(int wareGroupID) {
        this.wareGroupID = wareGroupID;
    }

    public ContentValues insertValuesToContent(){
        ContentValues contentValues=new ContentValues();

        contentValues.put(WareDB.COLUMN_ID,getID());
        contentValues.put(WareDB.COLUMN_NAME,getName());
        contentValues.put(WareDB.COLUMN_PRICE,getPrice());
        contentValues.put(WareDB.COLUMN_STOCK,getStock());
        contentValues.put(WareDB.COLUMN_INHOLDING,getInHolding());
        contentValues.put(WareDB.COLUMN_WAREGROUPid,getWareGroupID());

        return contentValues;
    }

}
