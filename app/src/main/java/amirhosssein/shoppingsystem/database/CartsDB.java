package amirhosssein.shoppingsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.models.Carts;

public class CartsDB {

    public static final String TABLE_CARTS="Carts";
    public static final String COLUMN_ID="ID";
    public static final String COLUMN_CUSTOMERID="CustomerID";
    public static final String COLUMN_CONFIRMSTATUS="ConfirmStatus";
    public static final String COLUMN_SENDSTATUS="SendStatus";
    public static final String COLUMN_ADMINID="AdminID";

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

    public CartsDB (Context context){
        this.mycontext=context;
        myDatabaseHelper=new MyDatabaseHelper(mycontext);
    }

    public void insert(Carts carts){
        openWritebaleDatabase();
        ContentValues contentValues=carts.insertValuesToContent();
        db.insert(CartsDB.TABLE_CARTS,null,contentValues);
        closeDatabase();
    }

    public void update(Carts carts,int id){
        openWritebaleDatabase();

        ContentValues contentValues=carts.insertValuesToContent();
        String[] whereArgs={String.valueOf(id)};
        db.update(CartsDB.TABLE_CARTS,contentValues,CartsDB.COLUMN_ID+" =?",whereArgs);

        closeDatabase();
    }

    public void delete(int id){
        openWritebaleDatabase();

        String[] whereArgs={String.valueOf(id)};
        db.delete(CartsDB.TABLE_CARTS,CartsDB.COLUMN_ID+" =?",whereArgs);

        closeDatabase();
    }

    public Carts getOpenCartByCustomerID(int ID){
        openReadableDatabase();
        String[] selectArgs={String.valueOf(ID),String.valueOf(0)};
        String sqlstrg="select * from "+CartsDB.TABLE_CARTS+" where "+CartsDB.COLUMN_CUSTOMERID+" =? and "+CartsDB.COLUMN_CONFIRMSTATUS+" =?";
        Cursor cursor=db.rawQuery(sqlstrg,selectArgs);
        cursor.moveToFirst();
        Carts cart =getCartByCursor(cursor);
        closeDatabase();
        return cart;
    }

    public String getCustomerNameByCartID(int cartID){
        openReadableDatabase();
        String sqlstrg="select customers.Name as Name , customers.LastName as lastName from "+
                CustomersDB.TABLE_CUSTOMERS+" join "+CartsDB.TABLE_CARTS+
                " on customers.CustomersID = Carts.CustomerID where carts.ID =?";
        String[] selectArgs={String.valueOf(cartID)};
        Cursor cursor=db.rawQuery(sqlstrg,selectArgs);
        cursor.moveToFirst();
        String fullName="نام مشتری : "+cursor.getString(cursor.getColumnIndex("Name"))+" "+cursor.getString(cursor.getColumnIndex("lastName"));
        cursor.close();
        closeDatabase();
        return fullName;
    }

    public ArrayList<Carts> getAllCloseCartsByCustomerID(int customerID){
        ArrayList<Carts> list=new ArrayList<>();
        openReadableDatabase();
        String[] selectArgs ={String.valueOf(customerID),String.valueOf(1)};
        String sqlstrg="select * from "+CartsDB.TABLE_CARTS+
                " where "+CartsDB.COLUMN_CUSTOMERID+"=? and "+
                CartsDB.COLUMN_SENDSTATUS+" =? ";
        Cursor cursor=db.rawQuery(sqlstrg,selectArgs);
        while (cursor.moveToNext()){
            Carts cart=getCartByCursor(cursor);
            list.add(cart);
        }
        cursor.close();
        closeDatabase();
        return list;
    }

    public ArrayList<Carts> getAllOpenCarts(){
        ArrayList<Carts> list=new ArrayList<>();
        openReadableDatabase();
        String[] selectArgs={String.valueOf(1)};
        Cursor cursor=db.query(CartsDB.TABLE_CARTS,null,CartsDB.COLUMN_CONFIRMSTATUS+" =?",selectArgs,null,null,null);
        while (cursor.moveToNext()){
            Carts cart =getCartByCursor(cursor);
            list.add(cart);
        }
        cursor.close();
        closeDatabase();
        return list;
    }

    public int getNewCartID(){
        openReadableDatabase();
        Cursor cursor=db.rawQuery("select "+CartsDB.COLUMN_ID+" from "+CartsDB.TABLE_CARTS+" order by "+CartsDB.COLUMN_ID+" desc",null);
        cursor.moveToFirst();
        int cartID=cursor.getInt(0);
        cursor.close();
        closeDatabase();
        return cartID+1;
    }

    public int getOpenCartIDByCustomerID(int cusID){
        int cartID;
        openReadableDatabase();
        String sqlstrng="select * from "+CartsDB.TABLE_CARTS+" where "
                +CartsDB.COLUMN_CUSTOMERID+" =? and "
                +CartsDB.COLUMN_CONFIRMSTATUS+" =?";
        String[] selectArgs={String.valueOf(cusID),String.valueOf(0)};
        Cursor cursor=db.rawQuery(sqlstrng,selectArgs);
        if (cursor.moveToFirst()) {
            cartID = cursor.getInt(cursor.getColumnIndex(CartsDB.COLUMN_ID));
        }
        else {
            cartID = 0;
        }
        cursor.close();
        closeDatabase();
        return cartID;
    }

    private Carts getCartByCursor(Cursor cursor){
        Carts carts=new Carts();

        carts.setID(cursor.getInt(cursor.getColumnIndex(CartsDB.COLUMN_ID)));
        carts.setCustomerID(cursor.getInt(cursor.getColumnIndex(CartsDB.COLUMN_CUSTOMERID)));
        carts.setConfirmStatus(cursor.getInt(cursor.getColumnIndex(CartsDB.COLUMN_CONFIRMSTATUS))==1?true:false);
        carts.setSendStatus(cursor.getInt(cursor.getColumnIndex(CartsDB.COLUMN_SENDSTATUS))==1?true:false);
        carts.setAdminID(cursor.getInt(cursor.getColumnIndex(CartsDB.COLUMN_ADMINID)));

        return carts;
    }

}
