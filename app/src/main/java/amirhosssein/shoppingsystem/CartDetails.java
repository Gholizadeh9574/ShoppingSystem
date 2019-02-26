package amirhosssein.shoppingsystem;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.adaptor.CartDetailsAdaptor;
import amirhosssein.shoppingsystem.database.CartsDB;
import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.models.Carts;
import amirhosssein.shoppingsystem.models.Customer;
import amirhosssein.shoppingsystem.models.Orders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CartDetails extends AppCompatActivity {

    Context context=this;
    @BindView(R.id.cartdetails_customernametv)
    TextView customerNametv;
    @BindView(R.id.cartdetails_customerID)
    TextView customerIDtv;
    @BindView(R.id.cartdetails_customeraddresstv)
    TextView customerAddresstv;
    @BindView(R.id.cartdetails_customerphonetv)
    TextView customerPhonetv;
    @BindView(R.id.cartdetails_recyclerview)
    RecyclerView recyclerView;
    CartDetailsAdaptor adaptor;
    ArrayList<Orders> list;
    CustomersDB customersDB=new CustomersDB(context);
    OrdersDB ordersDB=new OrdersDB(context);
    CartsDB cartsDB=new CartsDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        Carts cart=cartsDB.getCartbyCartID(getIntent().getIntExtra("cartID",0));
        int customerID=cart.getCustomerID();

        Customer customer=customersDB.getCustomerByID(customerID);
        ButterKnife.bind(this);
        customerNametv.setText(customer.getName()+" "+customer.getLastname());
        customerIDtv.setText("شناسه کاربری : "+customer.getID());
        customerAddresstv.setText("آدرس : "+customer.getAddress());
        customerPhonetv.setText("تلفن تماس : "+customer.getPhone());

        list=ordersDB.getOrderByCartID(cart.getID());

        adaptor=new CartDetailsAdaptor(list,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adaptor);



    }
}
