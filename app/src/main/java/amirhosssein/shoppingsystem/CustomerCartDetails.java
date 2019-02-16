package amirhosssein.shoppingsystem;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.adaptor.CustomerCartDetailsAdaptor;
import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.models.Carts;
import amirhosssein.shoppingsystem.models.Orders;

public class CustomerCartDetails extends AppCompatActivity {


    Context context=this;
    TextView cartIDtv,totalOrderPricetv;
    RecyclerView recyclerView;
    CustomerCartDetailsAdaptor adaptor;
    ArrayList<Orders> list;
    OrdersDB ordersDB=new OrdersDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart_details);
        Carts cart= (Carts) getIntent().getSerializableExtra("cart");

        cartIDtv=findViewById(R.id.customercartdetails_cartIDtv);
        totalOrderPricetv=findViewById(R.id.customercartdetails_totalorderpricetv);
        recyclerView=findViewById(R.id.customercartdetails_recy);

        cartIDtv.setText("شماره ثبت این سفارش : "+cart.getID());

        list=ordersDB.getOrderByCartID(cart.getID());
        adaptor=new CustomerCartDetailsAdaptor(context,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adaptor);

        int totalPrice=ordersDB.getTotalOrderValueByCartID(cart.getID());
        String stringTotalPrice="مبلغ کل این سفارش "+totalPrice+" تومان";
        totalOrderPricetv.setText(stringTotalPrice);



    }
}
