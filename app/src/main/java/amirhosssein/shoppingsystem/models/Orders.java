package amirhosssein.shoppingsystem.models;

import android.content.ContentValues;

import amirhosssein.shoppingsystem.database.CartsDB;
import amirhosssein.shoppingsystem.database.OrdersDB;

public class Orders {

    public int ID;
    private int CartID;
    private int WareID;
    private int Quantity;

    public Orders (){}

    public Orders(int cartID, int wareID, int quantity) {
        CartID = cartID;
        WareID = wareID;
        Quantity = quantity;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCartID() {
        return CartID;
    }

    public void setCartID(int cartID) {
        CartID = cartID;
    }

    public int getWareID() {
        return WareID;
    }

    public void setWareID(int wareID) {
        WareID = wareID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public ContentValues insertValuesToContent(){
        ContentValues contentValues=new ContentValues();

        contentValues.put(OrdersDB.COLUMN_CARTSID,getCartID());
        contentValues.put(OrdersDB.COLUMN_WAREID,getWareID());
        contentValues.put(OrdersDB.COLUMN_QUANTITY,getQuantity());

        return contentValues;
    }




}
