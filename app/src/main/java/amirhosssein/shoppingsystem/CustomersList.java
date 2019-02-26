package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import amirhosssein.shoppingsystem.adaptor.CustomerListAdaptor;
import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.models.Customer;
import amirhosssein.shoppingsystem.models.OnItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomersList extends AppCompatActivity {

    Context context=this;
    @BindView(R.id.customerlistrecy)
    RecyclerView recyclerView;
    CustomerListAdaptor adaptor;
    List<Customer> list;

    CustomersDB customersDB=new CustomersDB(context);

    @Override
    protected void onResume() {
        super.onResume();
        list=customersDB.getAllCustomers();
        ButterKnife.bind(this);
        adaptor=new CustomerListAdaptor(list,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adaptor);

        adaptor.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                Customer customer=list.get(position);
                Intent intent =new Intent(context,CustomerDetails.class);
                intent.putExtra("customerID",customer.getID());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_list);
    }
}
