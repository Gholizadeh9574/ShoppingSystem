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
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerCartDetails extends AppCompatActivity {


    Context context=this;
    @BindView(R.id.customercartdetails_cartIDtv)
    TextView cartIDtv;
    @BindView(R.id.customercartdetails_totalorderpricetv)
    TextView totalOrderPricetv;
    @BindView(R.id.customercartdetails_recy)
    RecyclerView recyclerView;
    CustomerCartDetailsAdaptor adaptor;
    ArrayList<Orders> list;
    OrdersDB ordersDB=new OrdersDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart_details);
        int enterCartID=getIntent().getIntExtra("cartID",0);
        ButterKnife.bind(this);

        cartIDtv.setText("شماره ثبت این سفارش : "+enterCartID);

        list=ordersDB.getOrderByCartID(enterCartID);
        adaptor=new CustomerCartDetailsAdaptor(context,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adaptor);

        int totalPrice=ordersDB.getTotalOrderValueByCartID(enterCartID);
        String stringTotalPrice="مبلغ کل این سفارش "+totalPrice+" تومان";
        totalOrderPricetv.setText(stringTotalPrice);



    }
}
