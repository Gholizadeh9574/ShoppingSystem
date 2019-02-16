package amirhosssein.shoppingsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.models.WareGroup;

public class WareGroupDB {

    public static final String WAREGROUP_TABLE="WareGroup";
    public static final String COLUMN_ID="ID";
    public static final String COLUMN_NAME="GroupName";

    private SQLiteDatabase db;
    private MyDatabaseHelper myDatabaseHelper;
    private Context mycontext;

    private void openWritebaleDatabase(){
        db=myDatabaseHelper.getWritableDatabase();
    }
    private void openReadableDatabase(){
        db=myDatabaseHelper.getReadableDatabase();
    }
    private void closeDatabase(){
        if (db!=null)
            db.close();
    }

    public WareGroupDB (Context context){
        this.mycontext=context;
        myDatabaseHelper=new MyDatabaseHelper(mycontext);
    }

    public ArrayList<String> getAllWareGroupName(){
        ArrayList<String> nameList=new ArrayList<>();
        nameList.add("همه کالا ها");
        openReadableDatabase();

        Cursor cursor=db.query(WareGroupDB.WAREGROUP_TABLE,null,null,null,null,null,WareGroupDB.COLUMN_ID);

        while (cursor.moveToNext()){
            nameList.add(cursor.getString(cursor.getColumnIndex(WareGroupDB.COLUMN_NAME)));

        }

        cursor.close();
        closeDatabase();
        return nameList;
    }


    public String getGroupNameByGroupID(int groupID){
        openReadableDatabase();
        String[] selectionArgs={String.valueOf(groupID)};
        String[] columns={WareGroupDB.COLUMN_NAME};
        Cursor cursor=db.query(WareGroupDB.WAREGROUP_TABLE,columns,WareGroupDB.COLUMN_ID+" =?",selectionArgs,null,null,null);
        cursor.moveToFirst();
        String result=cursor.getString(cursor.getColumnIndex(WareGroupDB.COLUMN_NAME));
        cursor.close();
        closeDatabase();
        return result;
    }

    public void insert(WareGroup wareGroup){
        openWritebaleDatabase();
        ContentValues contentValues=wareGroup.insertValuesToContent();
        db.insert(WareGroupDB.WAREGROUP_TABLE,null,contentValues);
        closeDatabase();
    }

    public void update(WareGroup wareGroup , int id){
        openWritebaleDatabase();
        ContentValues contentValues=wareGroup.insertValuesToContent();
        String[] whereArgs={String.valueOf(id)};
        db.update(WareGroupDB.WAREGROUP_TABLE,contentValues,WareGroupDB.COLUMN_ID,whereArgs);
        closeDatabase();
    }

    public void delete(int id){
        openWritebaleDatabase();
        String[] whereArgs={String.valueOf(id)};
        db.delete(WareGroupDB.WAREGROUP_TABLE,WareGroupDB.COLUMN_ID,whereArgs);
        closeDatabase();
    }

    public WareGroup getWareGroupByCursor(Cursor cursor){

        WareGroup wareGroup=new WareGroup();
        wareGroup.setID(cursor.getInt(cursor.getColumnIndex(WareGroupDB.COLUMN_ID)));
        wareGroup.setGroupName(cursor.getString(cursor.getColumnIndex(WareGroupDB.COLUMN_NAME)));
        return wareGroup;
    }

}
