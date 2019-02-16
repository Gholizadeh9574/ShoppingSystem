package amirhosssein.shoppingsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import amirhosssein.shoppingsystem.models.Customer;

public class CustomersDB {

    String TAG="MyApp";

    private SQLiteDatabase db;
    private MyDatabaseHelper myDatabaseHelper;
    private Context mycontext;

    public static final String TABLE_CUSTOMERS="Customers";
    public static final String COLUMN_ID="CustomersID";
    public static final String COLUMN_ACCUONT_NAME="Account_Name";
    public static final String COLUMN_NAME="Name";
    public static final String COLUMN_LASTNAME="LastName";
    public static final String COLUMN_ADDRESS="Address";
    public static final String COLUMN_PHONE="Phone";
    public static final String COLUMN_ISACTIVE="IsActive";
    public static final String COLUMN_PASSWORD="Password";

    public CustomersDB (Context context){
        this.mycontext=context;
        myDatabaseHelper=new MyDatabaseHelper(mycontext);
    }

    private void openWritebaleDatabase(){
        db=myDatabaseHelper.getWritableDatabase();
    }
    private void openReadableDatabase(){
        db=myDatabaseHelper.getReadableDatabase();
    }
    private void closeDatabase(){
        if (db!=null)
            db.close();
        Log.d(TAG, "closeDatabase: finishh");
    }

    public void insert(Customer customer){
        openWritebaleDatabase();
        ContentValues cv= customer.insertValuesToContent();
        db.insert(CustomersDB.TABLE_CUSTOMERS,null,cv);
        closeDatabase();
    }

    public void update(Customer customer, int id){
        openWritebaleDatabase();
        ContentValues cv= customer.insertValuesToContent();
        String[] whereArgs={String.valueOf(id)};
        db.update(CustomersDB.TABLE_CUSTOMERS,cv,CustomersDB.COLUMN_ID+" =?",whereArgs);
        closeDatabase();
    }

    public void delet(int id){
        openWritebaleDatabase();
        String[] whereArgs={String.valueOf(id)};
        db.delete(CustomersDB.TABLE_CUSTOMERS,CustomersDB.COLUMN_ID+"=?",whereArgs);
        closeDatabase();
    }

    public List<Customer> getAllCustomers(){
        ArrayList<Customer> list=new ArrayList<>();
        openReadableDatabase();
        Cursor cursor=db.query(CustomersDB.TABLE_CUSTOMERS,null,null,null,null,null ,CustomersDB.COLUMN_ID);
        while (cursor.moveToNext()) {
            Customer customer = getCustomerByCursor(cursor);
            list.add(customer);

        }
        cursor.close();
        closeDatabase();
        return list;
    }

    public boolean isAccountNameUsed(String accountname){
        boolean isAccountExists=false;


        openWritebaleDatabase();
        String sqlstr="select * from "+CustomersDB.TABLE_CUSTOMERS+" where "+
                CustomersDB.COLUMN_ACCUONT_NAME+" = ?" ;
        Cursor cursor=db.rawQuery(sqlstr,new String[] {accountname});

        if (cursor.getCount()==1) {
            isAccountExists = true;
        }
        cursor.close();
        closeDatabase();
        return isAccountExists;
    }

    public boolean logIn(String accountname , String password){

        Log.d(TAG, "logIn: start");
        boolean isAccountExists=true;


        openWritebaleDatabase();
        String sqlstr="select * from "+CustomersDB.TABLE_CUSTOMERS+" where "+
                CustomersDB.COLUMN_ACCUONT_NAME+" = ? and "+CustomersDB.COLUMN_PASSWORD+" =?" ;
        Cursor cursor=db.rawQuery(sqlstr,new String[] {accountname , password});

        if (cursor.getCount()==0) {
            isAccountExists = false;
            Log.d(TAG, "logIn: cursor =0");
        }
        cursor.close();
        closeDatabase();
        Log.d(TAG, "logIn: finish");
        return isAccountExists;
    }

    public Customer getCustomerByID(int id){
        openReadableDatabase();

        String[] whereArgs={String.valueOf(id)};
        Cursor cursor=db.query(CustomersDB.TABLE_CUSTOMERS,null,CustomersDB.COLUMN_ID+" =?",whereArgs,null,null,null);
        cursor.moveToFirst();
        Customer customer=this.getCustomerByCursor(cursor);
        closeDatabase();
        return customer;
    }
    
    public Customer getCustomerByAccountName(String accountName ){
        openWritebaleDatabase();
        Customer customer=new Customer();
        String sqlsrg="select * from "+CustomersDB.TABLE_CUSTOMERS+
                " where "+CustomersDB.COLUMN_ACCUONT_NAME+" =? ";
        String[] whereArgs={accountName };
        Cursor cursor=db.rawQuery(sqlsrg,whereArgs);
        if (cursor.moveToNext()) {
            customer = getCustomerByCursor(cursor);
        }
        else
            return null;
        cursor.close();
        closeDatabase();
        return customer;
    }

    public Customer getCustomerByCursor(Cursor cursor){

        Customer customer=new Customer();
        customer.setID(cursor.getInt(cursor.getColumnIndex(CustomersDB.COLUMN_ID)));
        customer.setAccount_name(cursor.getString(cursor.getColumnIndex(CustomersDB.COLUMN_ACCUONT_NAME)));
        customer.setName(cursor.getString(cursor.getColumnIndex(CustomersDB.COLUMN_NAME)));
        customer.setLastname(cursor.getString(cursor.getColumnIndex(CustomersDB.COLUMN_LASTNAME)));
        customer.setAddress(cursor.getString(cursor.getColumnIndex(CustomersDB.COLUMN_ADDRESS)));
        customer.setPhone(cursor.getString(cursor.getColumnIndex(CustomersDB.COLUMN_PHONE)));
        customer.setIsactive(cursor.getInt(cursor.getColumnIndex(CustomersDB.COLUMN_ISACTIVE))==1 ? true: false);
        customer.setPassword(cursor.getString(cursor.getColumnIndex(CustomersDB.COLUMN_PASSWORD)));

        return customer;
    }

}
