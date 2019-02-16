package amirhosssein.shoppingsystem.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import amirhosssein.shoppingsystem.database.AdminDB;
import amirhosssein.shoppingsystem.database.CartsDB;
import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.database.WareGroupDB;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private String TAG="MyApp";
    private static final int VERSION=36;

    public MyDatabaseHelper(Context context) {
        super(context, "SSystem.db", null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

            Log.d(TAG, "onCreate: start create");
            String create_customer_table = "create table if not exists " +
                    CustomersDB.TABLE_CUSTOMERS + " ( " +
                    CustomersDB.COLUMN_ID + " integer  primary key autoincrement ," +
                    CustomersDB.COLUMN_ACCUONT_NAME + " text unique , " +
                    CustomersDB.COLUMN_NAME + " text ," +
                    CustomersDB.COLUMN_LASTNAME + " text ," +
                    CustomersDB.COLUMN_ADDRESS + " text , " +
                    CustomersDB.COLUMN_PHONE + " text ," +
                    CustomersDB.COLUMN_ISACTIVE + " integer , " +
                    CustomersDB.COLUMN_PASSWORD + " text) ";
            db.execSQL(create_customer_table);
        fillingCustomersTable(db);

        String create_admin_table="create table if not exists "+
                AdminDB.TABLE_ADMIN+" ( "+
                AdminDB.COLUMN_ID+" integer primary key autoincrement , "+
                AdminDB.COLUMN_NAME+" text , "+
                AdminDB.COLUMN_LASTNAME+" text , "+
                AdminDB.COLUMN_ISACTIVE+" integer , "+
                AdminDB.COLUMN_MAINADMIN+" integer , "+
                AdminDB.COLUMN_PASSWORD+" text ) ";
        db.execSQL(create_admin_table);

        fillingAdminTable(db);

        String create_ware_table="create table if not exists "+
                WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" integer primary key autoincrement , "+
                WareDB.COLUMN_NAME+" text , "+
                WareDB.COLUMN_PRICE+" integer , "+
                WareDB.COLUMN_STOCK+" integer , "+
                WareDB.COLUMN_INHOLDING+" integer , "+
                WareDB.COLUMN_WAREGROUPid+" integer ) ";
        db.execSQL(create_ware_table);

        fillingWareTable(db);

        String create_waregroup_table="create table if not exists "+
                WareGroupDB.WAREGROUP_TABLE+" ( "+
                WareGroupDB.COLUMN_ID+" integer primary key autoincrement ,"+
                WareGroupDB.COLUMN_NAME+" text ) ;";
        db.execSQL(create_waregroup_table);

        fillingWareGroupTable(db);

        String create_carts_table="create table if not exists "+
                CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" integer primary key autoincrement , "+
                CartsDB.COLUMN_CUSTOMERID+" integer , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" integer , "+
                CartsDB.COLUMN_SENDSTATUS+" integer , "+
                CartsDB.COLUMN_ADMINID+" integer ) ;";
        db.execSQL(create_carts_table);

        fillingCartTable(db);

        String create_orders_table="create table if not exists "+
                OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" integer primary key autoincrement , "+
                OrdersDB.COLUMN_CARTSID+" integer , "+
                OrdersDB.COLUMN_WAREID+" integer , "+
                OrdersDB.COLUMN_QUANTITY+" integer ) ;";
        db.execSQL(create_orders_table);

        fillingOrderTable(db);

        Log.d(TAG, "onCreate: finish create");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: start");

        String sql_drop_ustomertable="drop table "+ CustomersDB.TABLE_CUSTOMERS;
        String sql_drop_admintable="drop table "+AdminDB.TABLE_ADMIN;
        String sql_drop_waretable="drop table "+WareDB.TABLE_WARE;
        String sql_drop_cartstable="drop table "+CartsDB.TABLE_CARTS;
        String sql_drop_orderstable="drop table "+OrdersDB.TABLE_ORDERS;
        String sql_drop_wargrouptable="drop table "+WareGroupDB.WAREGROUP_TABLE;
        db.execSQL(sql_drop_ustomertable);
        db.execSQL(sql_drop_admintable);
        db.execSQL(sql_drop_waretable);
        db.execSQL(sql_drop_cartstable);
        db.execSQL(sql_drop_orderstable);
        db.execSQL(sql_drop_wargrouptable);

        onCreate(db);

        Log.d(TAG, "onUpgrade: finish");
    }

    private void fillingCustomersTable(SQLiteDatabase db){
        Log.i("MyApp", "fillingCustomersTable: start ");

        String customer1="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 101 , 'negar' , 'نگار' , 'فرزین' , 'تهرانسر - بلوار اصلی - خ 20 - پ 47 - ط 3 واحد 12' , '09371476' , 1 , '12345678' )" ;

        String customer2="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 102 , 'asadineda' , 'ندا' , 'اسدی' , 'تهرانسر خ14 - پ114 - واحد 1' ,  '0912588443' , 1 , '13572468' ) ";

        String customer3="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 103 , 'shahrozz' , 'شهروز', 'برگشادی' , 'تهرانسر نبش خیابان 7 - پ 10 - واحد 1' , '09384413271' , 1 , '82463751' ) ";

        String customer4="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 104 , 'tania' , 'تانیا' , 'اسلامی' , 'تهرانسر - خ 23 - پ 25 - واحد 16' , '09352212921' , 1 , '00001111' )" ;
        String customer5="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 105 , 'parsagh' , 'پارسا' , 'قاسمی' , 'تهرانسر - پلوار اصی خ 18 - پ 25 واحد 16' , '09194233830' , 0 , '12121313' ) ";
        String customer6="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 106 , 'shabnam' , 'شبنم' , 'مرتضی نیا' , 'شهرک دریا - خ خزر - ک 15 - پ 3' , '09382825281' , 1 , '10000001' ) ";
        String customer7="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 107 , 'sussan.ta' , 'سوزان' , 'تاجیک' , 'خ خلیج - کوچه 82 - پ120 - واحد 5' , '09198304537' , 1 , '44445555' ) ";
        String customer8="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 108 , 'azimiraad' , 'اعظم' , 'عظیمی راد' , 'شهرک آزادی - خ مصطفی خمینی - کوچه 3 - پ 14' , '09036982541' , 1 , '36982541' ) ";
        String customer9="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 109 , 'hadi_almasi' , 'هادی' , 'الماسی' , 'شهرک استقلال - خ بنفشه شمالی - پ103' , '09345301817' , 1 , '88888888' ) ";
        String customer10="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 110 , 'alisaharian' , 'علی' , 'سحریان' , 'تهرانسر - بلوار گلها - ک 48 - پ 16 - و 10' , '09121220906' , 1 , '87654321' ) ";
        String customer11="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 111 , 'sareh' , 'ساره' , 'امیری' , 'شهرک دریا - بلوار مروارید - کوچه 4 - پ 36' , '09054417189' , 0 , '45321678' ) ";
        String customer12="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 112 , 'afsharzadeh' , 'مریم' , 'افشارزاده' , 'تهرانسر بلوار اصلی خ 22 پلاک 30 واحد 14' , '09123015196' , 1 , '12345678' )";
        String customer13="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 113 , 'dr.mehrdad' , 'مهرداد' , 'کوبک' , 'تهرانسر - خ 35 - پ16 - واحد 3' , '09331457418' , 0 , '23456781' )  ";
        String customer14="insert into "+CustomersDB.TABLE_CUSTOMERS+" ( "+
                CustomersDB.COLUMN_ID+" , "+
                CustomersDB.COLUMN_ACCUONT_NAME+" , "+
                CustomersDB.COLUMN_NAME+" , "+
                CustomersDB.COLUMN_LASTNAME+" , "+
                CustomersDB.COLUMN_ADDRESS+" , "+
                CustomersDB.COLUMN_PHONE+" , "+
                CustomersDB.COLUMN_ISACTIVE+" , "+
                CustomersDB.COLUMN_PASSWORD+" ) values ( 114 , 'mehdiahmadi' , 'مهدی' , 'احمدی' , 'شهرک آزادی - خیابان شیشه مینا - کوچه سوم - پلاک 13' , '09352490551' , 1 , '00000000' ) ";
        db.execSQL(customer1);
        db.execSQL(customer2);
        db.execSQL(customer3);
        db.execSQL(customer4);
        db.execSQL(customer5);
        db.execSQL(customer6);
        db.execSQL(customer7);
        db.execSQL(customer8);
        db.execSQL(customer9);
        db.execSQL(customer10);
        db.execSQL(customer11);
        db.execSQL(customer12);
        db.execSQL(customer13);
        db.execSQL(customer14);

        Log.i("MyApp", "fillingCustomersTable: fin ");
    }

    private void fillingAdminTable(SQLiteDatabase db){
        String admin1="insert into "+ AdminDB.TABLE_ADMIN+" ( "+
                AdminDB.COLUMN_ID+" , "+
                AdminDB.COLUMN_NAME+" , "+
                AdminDB.COLUMN_LASTNAME+" , "+
                AdminDB.COLUMN_ISACTIVE+" , "+
                AdminDB.COLUMN_MAINADMIN+" , "+
                AdminDB.COLUMN_PASSWORD+" ) values ( 101 , 'علی' , 'امیری' , 1 , 1 , '45678911' )";
        String admin2="insert into "+ AdminDB.TABLE_ADMIN+" ( "+
                AdminDB.COLUMN_ID+" , "+
                AdminDB.COLUMN_NAME+" , "+
                AdminDB.COLUMN_LASTNAME+" , "+
                AdminDB.COLUMN_ISACTIVE+" , "+
                AdminDB.COLUMN_MAINADMIN+" , "+
                AdminDB.COLUMN_PASSWORD+" ) values ( 102 , 'رضا' , 'خالصی' , 1 , 0 ,'23674518' )";
        String admin3="insert into "+ AdminDB.TABLE_ADMIN+" ( "+
                AdminDB.COLUMN_ID+" , "+
                AdminDB.COLUMN_NAME+" , "+
                AdminDB.COLUMN_LASTNAME+" , "+
                AdminDB.COLUMN_ISACTIVE+" , "+
                AdminDB.COLUMN_MAINADMIN+" , "+
                AdminDB.COLUMN_PASSWORD+" ) values (103 , 'جهان' , 'فرخنده' , 0 , 0 , '99887766' )";
        String admin4="insert into "+ AdminDB.TABLE_ADMIN+" ( "+
                AdminDB.COLUMN_ID+" , "+
                AdminDB.COLUMN_NAME+" , "+
                AdminDB.COLUMN_LASTNAME+" , "+
                AdminDB.COLUMN_ISACTIVE+" , "+
                AdminDB.COLUMN_MAINADMIN+" , "+
                AdminDB.COLUMN_PASSWORD+" ) values ( 104 , 'روناک' , 'سیدی' , 1 , 0 , '35791113' )";
        String admin5="insert into "+ AdminDB.TABLE_ADMIN+" ( "+
                AdminDB.COLUMN_ID+" , "+
                AdminDB.COLUMN_NAME+" , "+
                AdminDB.COLUMN_LASTNAME+" , "+
                AdminDB.COLUMN_ISACTIVE+" , "+
                AdminDB.COLUMN_MAINADMIN+" , "+
                AdminDB.COLUMN_PASSWORD+" ) values ( 105 , 'یوسف' , 'جلالی' , 1 , 0 , '23674518' )";

        db.execSQL(admin1);
        db.execSQL(admin2);
        db.execSQL(admin3);
        db.execSQL(admin4);
        db.execSQL(admin5);
    }

    private void fillingWareTable(SQLiteDatabase db){
        String ware1="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1001 , 'شکر' , 3500 , 200 , 5 , 5 )";
        String ware2="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1002 , 'مرغ منجمد' , 6800 , 150 , 3 , 2 )";
        String ware3="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1003 , 'مایع ظرفشویی پریل' , 6450 , 50 , 0 , 3 )";
        String ware4="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1004 , 'صابون گلنار' , 8340 , 250 , 0 , 3 )";
        String ware5="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1005 , 'چیپس مزمز' , 1820 , 300 , 6 , 4 )";
        String ware6="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1006 , 'پنیر آمل' , 5500 , 20 , 5 , 1 )";
        String ware7="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1007 , 'چسب نواری' , 1000 , 250 , 0 , 2 )";
        String ware8="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1008 , 'ماهی' , 12000 , 180 , 0 , 2 )";
        String ware9="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1009 , 'شامپو بچه صحت' , 4500 , 30 , 2 , 3 )";
        String ware10="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1010 , 'چای گلستان' , 25000 , 75 , 10 , 5 )";
        String ware11="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1011 , 'کیک کاکائویی' , 2200 , 270 , 0 , 4 )";
        String ware12="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1012 , 'کره میهن' , 1300 , 60 , 4 , 1 )";
        String ware13="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1013 , 'عدس' , 8500 , 165 , 10 , 5 )";
        String ware14="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1014 , 'برنج گلستان' , 65000 , 90 , 0 , 5 )";
        String ware15="insert into "+WareDB.TABLE_WARE+" ( "+
                WareDB.COLUMN_ID+" , "+
                WareDB.COLUMN_NAME+" , "+
                WareDB.COLUMN_PRICE+" , "+
                WareDB.COLUMN_STOCK+" , "+
                WareDB.COLUMN_INHOLDING+" , "+
                WareDB.COLUMN_WAREGROUPid+" ) values ( 1015 , 'گوشت گوسفندی' , 51000 , 30 , 0 , 2 )";

        db.execSQL(ware1);
        db.execSQL(ware2);
        db.execSQL(ware3);
        db.execSQL(ware4);
        db.execSQL(ware5);
        db.execSQL(ware6);
        db.execSQL(ware7);
        db.execSQL(ware8);
        db.execSQL(ware9);
        db.execSQL(ware10);
        db.execSQL(ware11);
        db.execSQL(ware12);
        db.execSQL(ware13);
        db.execSQL(ware14);
        db.execSQL(ware15);
    }

    private void fillingWareGroupTable(SQLiteDatabase db){
        String wGroup1="insert into "+WareGroupDB.WAREGROUP_TABLE+" ( "+
                WareGroupDB.COLUMN_ID+" , "+
                WareGroupDB.COLUMN_NAME+" ) values ( 1 , 'لبنیات' )";
        String wGroup2="insert into "+WareGroupDB.WAREGROUP_TABLE+" ( "+
        WareGroupDB.COLUMN_ID+" , "+
                WareGroupDB.COLUMN_NAME+" ) values ( 2 , 'پروتئین' )";
        String wGroup3="insert into "+WareGroupDB.WAREGROUP_TABLE+" ( "+
                WareGroupDB.COLUMN_ID+" , "+
                WareGroupDB.COLUMN_NAME+" ) values ( 3 , 'مواد شوینده' )";
        String wGroup4="insert into "+WareGroupDB.WAREGROUP_TABLE+" ( "+
                WareGroupDB.COLUMN_ID+" , "+
                WareGroupDB.COLUMN_NAME+" ) values ( 4 , 'تنقلات' )";
        String wGroup5="insert into "+WareGroupDB.WAREGROUP_TABLE+" ( "+
                WareGroupDB.COLUMN_ID+" , "+
                WareGroupDB.COLUMN_NAME+" ) values ( 5 , 'ارزاق عمومی' )";
        String wGroup6="insert into "+WareGroupDB.WAREGROUP_TABLE+" ( "+
                WareGroupDB.COLUMN_ID+" , "+
                WareGroupDB.COLUMN_NAME+" ) values ( 6 , 'سایر' )";

        db.execSQL(wGroup1);
        db.execSQL(wGroup2);
        db.execSQL(wGroup3);
        db.execSQL(wGroup4);
        db.execSQL(wGroup5);
        db.execSQL(wGroup6);
    }

    private void fillingCartTable(SQLiteDatabase db){
        String cart1="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" , "+
                CartsDB.COLUMN_ADMINID+" ) values ( 101 , 106 , 1 , 1 , 101 )";
        String cart2="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 102 , 110 , 1 , 0)";
        String cart3="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 103 , 101 , 0 , 0)";
        String cart4="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 104 , 110 , 0 , 0)";
        String cart5="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 105 , 102 , 0 , 0)";
        String cart6="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 106 , 103 , 0 , 0)";
        String cart7="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 107 , 104 , 0 , 0)";
        String cart8="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 108 , 105 , 0 , 0)";
        String cart9="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 109 , 106 , 0 , 0)";
        String cart10="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 110 , 107 , 0 , 0)";
        String cart11="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 111 , 108 , 0 , 0)";
        String cart12="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 112 , 109 , 0 , 0)";
        String cart13="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 113 , 111 , 0 , 0)";
        String cart14="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 114 , 112 , 0 , 0)";
        String cart15="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 115 , 113 , 0 , 0)";
        String cart16="insert into "+CartsDB.TABLE_CARTS+" ( "+
                CartsDB.COLUMN_ID+" , "+
                CartsDB.COLUMN_CUSTOMERID+" , "+
                CartsDB.COLUMN_CONFIRMSTATUS+" , "+
                CartsDB.COLUMN_SENDSTATUS+" ) values ( 116 , 114 , 0 , 0)";

        db.execSQL(cart1);
        db.execSQL(cart2);
        db.execSQL(cart3);
        db.execSQL(cart4);
        db.execSQL(cart5);
        db.execSQL(cart6);
        db.execSQL(cart7);
        db.execSQL(cart8);
        db.execSQL(cart9);
        db.execSQL(cart10);
        db.execSQL(cart11);
        db.execSQL(cart12);
        db.execSQL(cart13);
        db.execSQL(cart14);
        db.execSQL(cart15);
        db.execSQL(cart16);
    }

    private void fillingOrderTable(SQLiteDatabase db){
        String order1="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1001 , 101 ,1002, 4)";
        String order2="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1002 , 101 , 1010 , 6)";
        String order3="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1003 , 101 , 1003 , 2)";
        String order4="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1004 , 102 , 1013 , 10)";
        String order5="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1005 , 103 , 1010 , 10)";
        String order6="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1006 , 102 , 1002 , 3)";
        String order7="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1007 , 104 , 1005 , 6)";
        String order8="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1008 , 103 , 1001 , 5)";
        String order9="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1009 , 104 , 1009 , 2)";
        String order10="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1010 , 104 , 1012 , 4)";
        String order11="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1011 , 106 , 1006 , 3)";
        String order12="insert into "+OrdersDB.TABLE_ORDERS+" ( "+
                OrdersDB.COLUMN_ID+" , "+
                OrdersDB.COLUMN_CARTSID+" , "+
                OrdersDB.COLUMN_WAREID+" , "+
                OrdersDB.COLUMN_QUANTITY+" ) values (1012 , 102 , 1006 , 2)";

        db.execSQL(order1);
        db.execSQL(order2);
        db.execSQL(order3);
        db.execSQL(order4);
        db.execSQL(order5);
        db.execSQL(order6);
        db.execSQL(order7);
        db.execSQL(order8);
        db.execSQL(order9);
        db.execSQL(order10);
        db.execSQL(order11);
        db.execSQL(order12);

    }
}
