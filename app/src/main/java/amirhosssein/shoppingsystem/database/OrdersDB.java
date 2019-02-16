package amirhosssein.shoppingsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.models.Orders;

public class OrdersDB {

    public static final String TABLE_ORDERS="Orders";
    public static final String COLUMN_ID="ID";
    public static final String COLUMN_CARTSID="CartsID";
    public static final String COLUMN_WAREID="WareID";
    public static final String COLUMN_QUANTITY="Quantity";

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

    public OrdersDB (Context context){
        this.mycontext=context;
        myDatabaseHelper=new MyDatabaseHelper(mycontext);
    }

    public void insert(Orders order){
        openWritebaleDatabase();
        ContentValues cv= order.insertValuesToContent();
        db.insert(OrdersDB.TABLE_ORDERS,null,cv);
        closeDatabase();
    }

    public void update(Orders orders,int id){
        openWritebaleDatabase();

        ContentValues contentValues=orders.insertValuesToContent();
        String[] whrereArgs={String.valueOf(id)};
        db.update(OrdersDB.TABLE_ORDERS,contentValues,OrdersDB.COLUMN_ID+" =?",whrereArgs);

        closeDatabase();
    }

    public void delete(int id){
        openWritebaleDatabase();

        String[] whereArgs={String.valueOf(id)};
        db.delete(OrdersDB.TABLE_ORDERS,OrdersDB.COLUMN_ID+" =?",whereArgs);

        closeDatabase();
    }

    public Orders getOrder_WareInCart(int cartID , int wareID){
        Orders order=new Orders();
        order.setID(0);
        openReadableDatabase();
        String sqlsrtng="select * from "+OrdersDB.TABLE_ORDERS+" where "
                +OrdersDB.COLUMN_CARTSID+" =? and "
                +OrdersDB.COLUMN_WAREID+" =?";
        String[] selectArgs={String.valueOf(cartID),String.valueOf(wareID)};
        Cursor cursor=db.rawQuery(sqlsrtng,selectArgs);
        if (cursor.moveToFirst())
            order=getOrderByCursor(cursor);

        cursor.close();
        closeDatabase();
        return order;
    }

    public ArrayList<Orders> getOrderByCartID(int cartID){
        ArrayList<Orders> list =new ArrayList<>();
        openWritebaleDatabase();
        String[] selectArgs={String.valueOf(cartID)};
        Cursor cursor=db.query(OrdersDB.TABLE_ORDERS,null,OrdersDB.COLUMN_CARTSID+" =?",selectArgs,null,null,null);
        while (cursor.moveToNext()){
            Orders order = getOrderByCursor(cursor);
            list.add(order);
        }
        closeDatabase();
        return list;
    }

    public Orders getOrderByID(int orderID){
        Orders order=new Orders();
        openReadableDatabase();
        String[] selectArgs={String.valueOf(orderID)};
        Cursor cursor=db.query(OrdersDB.TABLE_ORDERS,null,OrdersDB.COLUMN_ID+" =?",selectArgs,null,null,null);
        order=getOrderByCursor(cursor);
        cursor.close();
        closeDatabase();
        return order;
    }

    public int getTotalQuantityOfWaresByCartID(int cartID){
        int num=0;
        openReadableDatabase();
        String[] columns={OrdersDB.COLUMN_QUANTITY};
        String[] selectionArgs={String.valueOf(cartID)};
        Cursor cursor=db.query(OrdersDB.TABLE_ORDERS,columns,OrdersDB.COLUMN_CARTSID+" =?",selectionArgs,null,null,null);
        while (cursor.moveToNext()){
            num+=cursor.getInt(cursor.getColumnIndex(OrdersDB.COLUMN_QUANTITY));
        }
        cursor.close();
        closeDatabase();
        return num;
    }

    public int getTotalOrderValueByCartID(int cartID){
        int totalPrice=0;
        openReadableDatabase();
        String sqlstrg="select (ware.Price*orders.Quantity) as totalPrice" +
                " from "+WareDB.TABLE_WARE+
                " join "+OrdersDB.TABLE_ORDERS+
                " on ware.id=orders.WareID where orders.CartsID = ?";
        String[] selectArgs={String.valueOf(cartID)};
        Cursor cursor=db.rawQuery(sqlstrg,selectArgs);
        while (cursor.moveToNext()){
            totalPrice+=cursor.getInt(0);
        }
        cursor.close();
        closeDatabase();
        return totalPrice;
    }

    public int getSalseAmountByWareID(int wareID){
        int result=0;
        openReadableDatabase();
        String sqlstrg="select sum(orders.quantity) as totalQuantity from "+OrdersDB.TABLE_ORDERS+
                " join "+CartsDB.TABLE_CARTS+
                " on Carts.ID=Orders.CartsID " +
                "where Carts.SendStatus=1 " +
                " and orders.wareid = ?" +
                " group by Orders.WareID";
        String[] selectionArgs={String.valueOf(wareID)};
        Cursor cursor=db.rawQuery(sqlstrg,selectionArgs);
        if (cursor.moveToFirst()) {
            result += cursor.getInt(0);
        }
        cursor.close();
        closeDatabase();
        return result;
    }

    public int getTotalOrdersPriceForCustomerByCustomerID(int customerID){
        int result=0;
        openReadableDatabase();
        Log.i("MyApp", "getTotalOrdersPriceForCustomerByCustomerID: start ");
        String[] selectionArgs={String.valueOf(customerID)};
        String sqlstrg="select Orders.Quantity*Ware.Price as totalOrdersPrice from " +
                OrdersDB.TABLE_ORDERS+" join " +
                CartsDB.TABLE_CARTS+" on carts.ID=Orders.CartsID join " +
                WareDB.TABLE_WARE+" on ware.ID=Orders.WareID where " +
                "Carts.SendStatus=1 and  Carts.CustomerID=?";
        Cursor cursor=db.rawQuery(sqlstrg,selectionArgs);
        while (cursor.moveToNext()){
            result+=cursor.getInt(cursor.getColumnIndex("totalOrdersPrice"));
        }
        cursor.close();
        closeDatabase();
        Log.i("MyApp", "getTotalOrdersPriceForCustomerByCustomerID: result : "+result);
        Log.i("MyApp", "getTotalOrdersPriceForCustomerByCustomerID: finish");
        return result;
    }

    private Orders getOrderByCursor(Cursor cursor) {
        Orders order=new Orders();

        order.setID(cursor.getInt(cursor.getColumnIndex(OrdersDB.COLUMN_ID)));
        order.setCartID(cursor.getInt(cursor.getColumnIndex(OrdersDB.COLUMN_CARTSID)));
        order.setWareID(cursor.getInt(cursor.getColumnIndex(OrdersDB.COLUMN_WAREID)));
        order.setQuantity(cursor.getInt(cursor.getColumnIndex(OrdersDB.COLUMN_QUANTITY)));
        return order;
    }

}
