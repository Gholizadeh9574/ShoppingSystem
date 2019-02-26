package amirhosssein.shoppingsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.models.Ware;

public class WareDB {

    private SQLiteDatabase db;
    private MyDatabaseHelper myDatabaseHelper;
    private Context mycontext;

    public WareDB(Context context) {
        this.mycontext = context;
        myDatabaseHelper = new MyDatabaseHelper(mycontext);
    }

    private void openWritebaleDatabase() {
        db = myDatabaseHelper.getWritableDatabase();
    }

    private void openReadableDatabase() {
        db = myDatabaseHelper.getReadableDatabase();
    }

    private void closeDatabase() {
        if (db != null)
            db.close();
    }

    public static final String TABLE_WARE = "Ware";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "wareName";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_STOCK = "Stock";
    public static final String COLUMN_INHOLDING = "inHolding";
    public static final String COLUMN_WAREGROUPid = "wareGroupID";

    public void insert(Ware ware) {
        openWritebaleDatabase();
        ContentValues contentValues = ware.insertValuesToContent();
        db.insert(WareDB.TABLE_WARE, null, contentValues);
        closeDatabase();
    }

    public void update(Ware ware, int id) {
        openWritebaleDatabase();
        ContentValues contentValues = ware.insertValuesToContent();
        String[] whereArgs = {String.valueOf(id)};
        db.update(WareDB.TABLE_WARE, contentValues, WareDB.COLUMN_ID + " =?", whereArgs);
        closeDatabase();
    }

    public void delet(int id) {
        openWritebaleDatabase();
        String[] whereArgs = {String.valueOf(id)};
        db.delete(WareDB.TABLE_WARE, WareDB.COLUMN_ID, whereArgs);
        closeDatabase();
    }

    public void addRequestQuantityToInHold(int wareID, int requestQuantity) {
        openWritebaleDatabase();

        String[] selectionArgs = {String.valueOf(wareID)};
        String sqlstrng = "select * from " + WareDB.TABLE_WARE + " where "
                + WareDB.COLUMN_ID + " =?";
        Cursor cursor = db.rawQuery(sqlstrng, selectionArgs);
        cursor.moveToFirst();
        Ware ware = getWareByCursor(cursor);
        int oldInHolding = ware.getInHolding();
        int newInHolding = oldInHolding + requestQuantity;
        ware.setInHolding(newInHolding);
        update(ware, wareID);
        cursor.close();
        closeDatabase();
    }

    public ArrayList<Ware> showWareListByGroupID(int groupID) {
        ArrayList<Ware> list = new ArrayList<>();
        openReadableDatabase();

        String[] selectionArgs = {String.valueOf(groupID)};
        String sqlsrng = "select * from " + WareDB.TABLE_WARE + " where " + WareDB.COLUMN_WAREGROUPid + " =? ";
        Cursor cursor = db.rawQuery(sqlsrng, selectionArgs);
        while (cursor.moveToNext()) {
            Ware ware = getWareByCursor(cursor);
            list.add(ware);
        }


        cursor.close();
        closeDatabase();
        return list;
    }

    public boolean isEnough(int num, int id) {
        openReadableDatabase();
        Log.i("MyApp", "isEnough: 1111");
        boolean isEnough = false;
        int stock;
        String[] selectionArgs = {String.valueOf(id)};
        String[] columns = {WareDB.COLUMN_STOCK};
        Cursor cursor = db.query(WareDB.TABLE_WARE, columns, WareDB.COLUMN_ID, selectionArgs, null, null, null);
        Log.i("MyApp", "isEnough: 2222");
        cursor.moveToFirst();
        stock = cursor.getInt(cursor.getColumnIndex(WareDB.COLUMN_STOCK));
        if (num - stock > 0) {
            isEnough = true;
            Log.i("MyApp", "isEnough: 333");
        }
        cursor.close();
        closeDatabase();
        return isEnough;
    }

    public String getWareNameByWareID(int wareID) {
        openReadableDatabase();
        String[] columns = {WareDB.COLUMN_NAME};
        String[] selectArgs = {String.valueOf(wareID)};
        Cursor cursor = db.query(WareDB.TABLE_WARE, columns, WareDB.COLUMN_ID + " =?", selectArgs, null, null, null);
        cursor.moveToFirst();
        String result = cursor.getString(0);
        cursor.close();
        closeDatabase();
        return result;
    }

    public int getTotalPriceByOrderID(int orderID) {
        int totalPrice = 0;
        openReadableDatabase();
        String[] selectArgs = {String.valueOf(orderID)};
        Cursor cursor = db.rawQuery("select ware.Price*Orders.Quantity as totalPrice from " + WareDB.TABLE_WARE +
                " join " + OrdersDB.TABLE_ORDERS + " on ware.ID=Orders.WareID where Orders.ID =?", selectArgs);
        cursor.moveToFirst();
        totalPrice = cursor.getInt(cursor.getColumnIndex("totalPrice"));
        cursor.close();
        closeDatabase();
        return totalPrice;
    }

    public ArrayList<Ware> getAllWares() {
        ArrayList<Ware> list = new ArrayList<>();
        openReadableDatabase();
        Cursor cursor = db.query(WareDB.TABLE_WARE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Ware ware = getWareByCursor(cursor);
            list.add(ware);
        }
        cursor.close();
        closeDatabase();
        return list;
    }

    public Ware getWareByID(int wareID) {
        openReadableDatabase();
        String[] selectionArgs = {String.valueOf(wareID)};
        Cursor cursor = db.query(WareDB.TABLE_WARE, null, WareDB.COLUMN_ID + " =?", selectionArgs, null, null, null);
        cursor.moveToFirst();
        Ware ware = getWareByCursor(cursor);
        cursor.close();
        closeDatabase();
        return ware;
    }

    public ArrayList<Ware> getAllWaresforCus() {
        ArrayList<Ware> list = new ArrayList<>();
        openWritebaleDatabase();

        Cursor cursor = db.query(WareDB.TABLE_WARE, null, null, null, null, null, WareDB.COLUMN_ID);
        while (cursor.moveToNext()) {
            Ware ware = getWareByCursor(cursor);
            if (ware.getStock() > 0)
                list.add(ware);
        }
        cursor.close();
        closeDatabase();
        return list;
    }

    private Ware getWareByCursor(Cursor cursor) {
        Ware ware = new Ware();

        ware.setID(cursor.getInt(cursor.getColumnIndex(WareDB.COLUMN_ID)));
        ware.setName(cursor.getString(cursor.getColumnIndex(WareDB.COLUMN_NAME)));
        ware.setPrice(cursor.getInt(cursor.getColumnIndex(WareDB.COLUMN_PRICE)));
        ware.setStock(cursor.getInt(cursor.getColumnIndex(WareDB.COLUMN_STOCK)));
        ware.setInHolding(cursor.getInt(cursor.getColumnIndex(WareDB.COLUMN_INHOLDING)));
        ware.setWareGroupID(cursor.getInt(cursor.getColumnIndex(WareDB.COLUMN_WAREGROUPid)));

        return ware;
    }
}
