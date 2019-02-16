package amirhosssein.shoppingsystem.models;

import android.content.ContentValues;
import android.util.Log;

import java.io.Serializable;

import amirhosssein.shoppingsystem.database.CustomersDB;

public class Customer implements Serializable{

    private int ID;
    private String account_name;
    private String name;
    private String lastname;
    private String address;
    private String phone;
    private boolean isactive;
    private String password;

    public Customer() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContentValues insertValuesToContent(){
        ContentValues contentValues=new ContentValues();

        contentValues.put(CustomersDB.COLUMN_ACCUONT_NAME,getAccount_name());
        contentValues.put(CustomersDB.COLUMN_NAME,getName());
        contentValues.put(CustomersDB.COLUMN_LASTNAME,getLastname());
        contentValues.put(CustomersDB.COLUMN_ADDRESS,getAddress());
        contentValues.put(CustomersDB.COLUMN_PHONE,getPhone());
        contentValues.put(CustomersDB.COLUMN_ISACTIVE,isIsactive() ? 1:0);
        contentValues.put(CustomersDB.COLUMN_PASSWORD,getPassword());

        return contentValues;
    }

}
