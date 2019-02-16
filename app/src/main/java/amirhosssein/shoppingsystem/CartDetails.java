package amirhosssein.shoppingsystem;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.adaptor.CartDetailsAdaptor;
import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.models.Carts;
import amirhosssein.shoppingsystem.models.Customer;
import amirhosssein.shoppingsystem.models.Orders;

public class CartDetails extends AppCompatActivity {

    Context context=this;
    TextView customerNametv,customerIDtv,customerAddresstv,customerPhonetv;
    RecyclerView recyclerView;
    CartDetailsAdaptor adaptor;
    ArrayList<Orders> list;
    CustomersDB customersDB=new CustomersDB(context);
    OrdersDB ordersDB=new OrdersDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        Carts cart= (Carts) getIntent().getSerializableExtra("cart");
        int customerID=cart.getCustomerID();
        customerNametv=findViewById(R.id.cartdetails_customernametv);
        customerIDtv=findViewById(R.id.cartdetails_customerID);
        customerAddresstv=findViewById(R.id.cartdetails_customeraddresstv);
        customerPhonetv=findViewById(R.id.cartdetails_customerphonetv);
        Customer customer=customersDB.getCustomerByID(customerID);
        customerNametv.setText(customer.getName()+" "+customer.getLastname());
        customerIDtv.setText("شناسه کاربری : "+customer.getID());
        customerAddresstv.setText("آدرس : "+customer.getAddress());
        customerPhonetv.setText("تلفن تماس : "+customer.getPhone());

        list=ordersDB.getOrderByCartID(cart.getID());

        recyclerView=findViewById(R.id.cartdetails_recyclerview);
        adaptor=new CartDetailsAdaptor(list,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adaptor);



    }
}
