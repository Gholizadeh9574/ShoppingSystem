package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.adaptor.CartForCusAdaptor;
import amirhosssein.shoppingsystem.database.CartsDB;
import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.models.Carts;
import amirhosssein.shoppingsystem.models.Customer;
import amirhosssein.shoppingsystem.models.Orders;
import amirhosssein.shoppingsystem.models.Ware;

public class CartForCus extends AppCompatActivity {

    Context context=this;
    TextView finalOrderNumtv,finalSumtv;
    Button confirmCart;
    int finalOrderNum=0,finalSum=0;
    RecyclerView recyclerView;
    ArrayList<Orders> listOfWareInOrder;
    OrdersDB ordersDB=new OrdersDB(context);
    CartsDB cartDB=new CartsDB(context);
    WareDB wareDB=new WareDB(context);
    CustomersDB customersDB=new CustomersDB(context);
    Customer customer;
    int cartID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartfor_cus);
        int enterCusID=getIntent().getIntExtra("customerID",0);
        customer=customersDB.getCustomerByID(enterCusID);
        cartID = cartDB.getOpenCartIDByCustomerID(enterCusID);
        listOfWareInOrder=ordersDB.getOrderByCartID(cartID);

        CartForCusAdaptor adaptor=new CartForCusAdaptor(listOfWareInOrder,context);
        finalOrderNumtv=findViewById(R.id.tottalityorderquntityshowtext);
        finalSumtv=findViewById(R.id.summorderpriceshowtext);
        confirmCart=findViewById(R.id.confirmCusCartButton);

        for (int i = 0; i <listOfWareInOrder.size() ; i++) {
            Orders orderforquantity=listOfWareInOrder.get(i);
            finalOrderNum+=orderforquantity.getQuantity();
            Ware wareforsum=wareDB.getWareByID(orderforquantity.getWareID());
            int price=wareforsum.getPrice();
            finalSum+=price*orderforquantity.getQuantity();
        }

        recyclerView=findViewById(R.id.recy_cuscart);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adaptor);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                finalOrderNum=0;
                finalSum=0;

                for (int i = 0; i <listOfWareInOrder.size() ; i++) {
                    Orders orderforquantity=listOfWareInOrder.get(i);
                    finalOrderNum+=orderforquantity.getQuantity();
                    Ware wareforsum=wareDB.getWareByID(orderforquantity.getWareID());
                    int price=wareforsum.getPrice();
                    finalSum+=price*orderforquantity.getQuantity();
                }

                finalOrderNumtv.setText("تعداد کل اجناس : "+finalOrderNum);
                finalSumtv.setText("مبلغ کل سفارش : "+finalSum+" تومان");
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        finalOrderNumtv.setText("تعداد کل اجناس : "+finalOrderNum);
        finalSumtv.setText("مبلغ کل سفارش : "+finalSum+" تومان");

        confirmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listOfWareInOrder.size()==0){
                    Toast.makeText(context, "سبد خرید شما خالی است", Toast.LENGTH_SHORT).show();
                }
                else {
                    Carts oldCart = cartDB.getOpenCartByCustomerID(customer.getID());
                    oldCart.setConfirmStatus(true);
                    cartDB.update(oldCart, oldCart.getID());
                    Carts newCart=new Carts();
                    int newCartID=cartDB.getNewCartID();
                    newCart.setID(newCartID);
                    newCart.setCustomerID(customer.getID());
                    newCart.setConfirmStatus(false);
                    cartDB.insert(newCart);
                    dialog();
                }

            }
        });
    }

    private void dialog(){
        AlertDialog.Builder myAlertDialog=new AlertDialog.Builder(context);
        myAlertDialog.setTitle(getResources().getString(R.string.cartisconfirmed));
        myAlertDialog.setMessage("شماره پیگیری سبد خرید : "+cartID);
        myAlertDialog.setCancelable(true);
        myAlertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        final AlertDialog alertDialog=myAlertDialog.create();
        alertDialog.show();
    }
}
