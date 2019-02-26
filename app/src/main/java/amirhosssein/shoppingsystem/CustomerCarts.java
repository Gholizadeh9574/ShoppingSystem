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
import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.models.Carts;
import amirhosssein.shoppingsystem.models.Customer;
import amirhosssein.shoppingsystem.models.OnItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerCarts extends AppCompatActivity {

    Context context=this;
    @BindView(R.id.customercarts_recy)
    RecyclerView recyclerView;
    CustomerCartsAdaptor adaptor;
    ArrayList<Carts> list;
    CartsDB cartsDB=new CartsDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_carts);
        int enterCusID=getIntent().getIntExtra("customerID",0);

        list=cartsDB.getAllCloseCartsByCustomerID(enterCusID);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adaptor=new CustomerCartsAdaptor(context,list);
        recyclerView.setAdapter(adaptor);

        adaptor.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                Carts cart=list.get(position);
                Intent intent =new Intent(context,CustomerCartDetails.class);
                intent.putExtra("cartID",cart.getID());
                startActivity(intent);
            }
        });

    }
}
