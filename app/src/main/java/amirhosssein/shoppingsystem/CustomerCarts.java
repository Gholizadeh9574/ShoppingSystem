package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.adaptor.CustomerCartsAdaptor;
import amirhosssein.shoppingsystem.database.CartsDB;
import amirhosssein.shoppingsystem.models.Carts;
import amirhosssein.shoppingsystem.models.Customer;
import amirhosssein.shoppingsystem.models.OnItemClickListener;

public class CustomerCarts extends AppCompatActivity {

    Context context=this;
    RecyclerView recyclerView;
    CustomerCartsAdaptor adaptor;
    ArrayList<Carts> list;

    CartsDB cartsDB=new CartsDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_carts);
        Customer customer= (Customer) getIntent().getSerializableExtra("customer");

        list=cartsDB.getAllCloseCartsByCustomerID(customer.getID());
        recyclerView=findViewById(R.id.customercarts_recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adaptor=new CustomerCartsAdaptor(context,list);
        recyclerView.setAdapter(adaptor);

        adaptor.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                Carts cart=list.get(position);
                Intent intent =new Intent(context,CustomerCartDetails.class);
                intent.putExtra("cart",cart);
                startActivity(intent);
            }
        });

    }
}
