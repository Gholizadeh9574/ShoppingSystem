package amirhosssein.shoppingsystem.models;

import android.content.ContentValues;

import java.io.Serializable;

import amirhosssein.shoppingsystem.database.AdminDB;

public class Admin implements Serializable {

    private int ID;
    private String name;
    private String lastname;
    private boolean acvive;
    private boolean mainAdmin;
    private String password;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public boolean isAcvive() {
        return acvive;
    }

    public void setAcvive(boolean acvive) {
        this.acvive = acvive;
    }

    public boolean isMainAdmin() {
        return mainAdmin;
    }

    public void setMainAdmin(boolean mainAdmin) {
        this.mainAdmin = mainAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContentValues insertValuesToContent(){
        ContentValues contentValues=new ContentValues();

        contentValues.put(AdminDB.COLUMN_ID,getID());
        contentValues.put(AdminDB.COLUMN_NAME,getName());
        contentValues.put(AdminDB.COLUMN_LASTNAME,getLastname());
        contentValues.put(AdminDB.COLUMN_ISACTIVE,isAcvive() ? 1 : 0);
        contentValues.put(AdminDB.COLUMN_MAINADMIN,isMainAdmin() ? 1:0);
        contentValues.put(AdminDB.COLUMN_PASSWORD,getPassword());

        return contentValues;
    }

}
