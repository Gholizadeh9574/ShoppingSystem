package amirhosssein.shoppingsystem.models;

import android.content.ContentValues;

import amirhosssein.shoppingsystem.database.WareGroupDB;

public class WareGroup {


    private int ID;
    private String groupName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ContentValues insertValuesToContent(){
        ContentValues contentValues=new ContentValues();

        contentValues.put(WareGroupDB.COLUMN_ID,getID());
        contentValues.put(WareGroupDB.COLUMN_NAME,getGroupName());

        return contentValues;
    }

}
