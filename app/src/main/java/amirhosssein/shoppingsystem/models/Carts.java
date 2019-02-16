package amirhosssein.shoppingsystem.models;

import android.content.ContentValues;

import java.io.Serializable;

import amirhosssein.shoppingsystem.database.CartsDB;

public class Carts implements Serializable {

    private int ID;
    private int customerID;
    private boolean confirmStatus;
    private boolean sendStatus;
    private int adminID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public boolean isConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(boolean confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public boolean isSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(boolean sendStatus) {
        this.sendStatus = sendStatus;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public ContentValues insertValuesToContent(){
        ContentValues contentValues=new ContentValues();

        contentValues.put(CartsDB.COLUMN_ID,getID());
        contentValues.put(CartsDB.COLUMN_CUSTOMERID,getCustomerID());
        contentValues.put(CartsDB.COLUMN_CONFIRMSTATUS,isConfirmStatus() ? 1: 0);
        contentValues.put(CartsDB.COLUMN_SENDSTATUS,isSendStatus() ? 1:0);
        contentValues.put(CartsDB.COLUMN_ADMINID,getAdminID());

        return contentValues;
    }

}
