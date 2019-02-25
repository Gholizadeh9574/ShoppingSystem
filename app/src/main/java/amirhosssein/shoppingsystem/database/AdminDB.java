package amirhosssein.shoppingsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import amirhosssein.shoppingsystem.models.Admin;

public class AdminDB {



    private SQLiteDatabase db;
    private MyDatabaseHelper myDatabaseHelper;
    private Context mycontext;


    public static final String TABLE_ADMIN="Admins";
    public static final String COLUMN_ID="AdminID";
    public static final String COLUMN_NAME="Name";
    public static final String COLUMN_LASTNAME="LastName";
    public static final String COLUMN_ISACTIVE="Active";
    public static final String COLUMN_MAINADMIN="MainAdmin";
    public static final String COLUMN_PASSWORD="Password";



    public AdminDB (Context context){
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
    }

    public void insert(Admin admin){
        openWritebaleDatabase();
        ContentValues contentValues=admin.insertValuesToContent();
        db.insert(AdminDB.TABLE_ADMIN,null,contentValues);
        closeDatabase();
    }

    public void update(Admin admin , int id){
        openWritebaleDatabase();
        ContentValues contentValues=admin.insertValuesToContent();
        String[] whereArgs={String.valueOf(id)};
        db.update(AdminDB.TABLE_ADMIN,contentValues,AdminDB.COLUMN_ID+"=?",whereArgs);
        closeDatabase();
    }

    public void delet(int id){
        openWritebaleDatabase();
        String[] whereArgs={String.valueOf(id)};
        db.delete(AdminDB.TABLE_ADMIN,AdminDB.COLUMN_ID+"=?",whereArgs);
        closeDatabase();
    }

    public int getNewAdminID(){
        openReadableDatabase();

        String[] columns={AdminDB.COLUMN_ID};
        Cursor cursor=db.query(AdminDB.TABLE_ADMIN,columns,null,null,null,null,AdminDB.COLUMN_ID+" desc");
        cursor.moveToFirst();
        int result=cursor.getInt(cursor.getColumnIndex(AdminDB.COLUMN_ID));

        cursor.close();
        closeDatabase();
        return result+1;
    }

    public ArrayList<Admin> getAllAdminsButCurectByAdminID(int adminID){
        ArrayList<Admin> list =new ArrayList<>();
        openReadableDatabase();


        String sqlstrg="select * from "+AdminDB.TABLE_ADMIN+" where "+
                AdminDB.COLUMN_ID+" !=?";
        String[] selectionArgs={String.valueOf(adminID)};

        Cursor cursor=db.rawQuery(sqlstrg,selectionArgs);

        while (cursor.moveToNext()){
            Admin admin=getAdminByCursor(cursor);
            list.add(admin);
        }
        cursor.close();
        closeDatabase();
        return list;
    }

    public ArrayList<Admin> getAllAdmins(){
        ArrayList<Admin> list =new ArrayList<>();
        openReadableDatabase();
        Cursor cursor=db.query(AdminDB.TABLE_ADMIN,null,null,null,null,null,AdminDB.COLUMN_ID);
        while (cursor.moveToNext()){
            Admin admin=getAdminByCursor(cursor);
            list.add(admin);
        }
        cursor.close();
        closeDatabase();
        return list;
    }

    public boolean login(String adminID , String password){
        boolean isExists=false;

        openReadableDatabase();

        String sqlstrg="select *  from "+AdminDB.TABLE_ADMIN+" where "+
                AdminDB.COLUMN_ID+" =? and "+
                AdminDB.COLUMN_PASSWORD+" =? ";
        String[] whereArgs={adminID , password};
        Cursor  cursor=db.rawQuery(sqlstrg,whereArgs);

        if (cursor.moveToFirst()) {
            isExists = true;
        }

        cursor.close();
        closeDatabase();
        return isExists;
    }

    public Admin getAdminByID(int adminID){
        Admin admin =new Admin();

        openReadableDatabase();

        String sqlstrg="select * from "+AdminDB.TABLE_ADMIN+" where "+
                AdminDB.COLUMN_ID+" =?";
        String[] whereArgs={String.valueOf(adminID)};
        Cursor cursor=db.rawQuery(sqlstrg,whereArgs);
        cursor.moveToFirst();
        admin=getAdminByCursor(cursor);
        cursor.close();
        closeDatabase();

        return admin;
    }

    private Admin getAdminByCursor(Cursor cursor) {
        Admin admin =new Admin();

        admin.setID(cursor.getInt(cursor.getColumnIndex(AdminDB.COLUMN_ID)));
        admin.setName(cursor.getString(cursor.getColumnIndex(AdminDB.COLUMN_NAME)));
        admin.setLastname(cursor.getString(cursor.getColumnIndex(AdminDB.COLUMN_LASTNAME)));
        admin.setAcvive(cursor.getInt(cursor.getColumnIndex(AdminDB.COLUMN_ISACTIVE))==1 ? true:false);
        admin.setMainAdmin(cursor.getInt(cursor.getColumnIndex(AdminDB.COLUMN_MAINADMIN))==1 ? true : false);
        admin.setPassword(cursor.getString(cursor.getColumnIndex(AdminDB.COLUMN_PASSWORD)));

        return admin;
    }


}
