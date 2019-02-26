package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.adaptor.CusWareRecyclerAdaptor;
import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.database.WareGroupDB;
import amirhosssein.shoppingsystem.models.Customer;
import amirhosssein.shoppingsystem.models.OnItemClickListener;
import amirhosssein.shoppingsystem.models.Ware;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerFirstPage extends AppCompatActivity {

    Context context=this;
    @BindView(R.id.cuswarerecycler)
    RecyclerView recyclerView;
    ArrayList<Ware> wareList;
    CusWareRecyclerAdaptor recAdapter;
    ArrayList<String> wareGroupNames;
    Customer customer;
    WareGroupDB wareGroupDB=new WareGroupDB(context);
    WareDB wareDB =new WareDB(context);
    CustomersDB customersDB=new CustomersDB(context);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()){
            case R.id.omnu_cart:
                intent=new Intent(context,CartForCus.class);
                intent.putExtra("customerID",customer.getID());
                startActivity(intent);
                break;
            case R.id.omnu_accountpanel:
                intent=new Intent(context,Account_Panel.class);
                intent.putExtra("customerID",customer.getID());
                startActivityForResult(intent,2);
                break;
            case R.id.omnu_customercarts:
                intent=new Intent(context , CustomerCarts.class);
                intent.putExtra("customerID",customer.getID());
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        customer=customersDB.getCustomerByID(customer.getID());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_first_page);
        setTitle(getResources().getString(R.string.search));
        int enterCusID=getIntent().getIntExtra("customerID",0);
        customer=customersDB.getCustomerByID(enterCusID);
        wareGroupNames=wareGroupDB.getAllWareGroupName();
        wareList=wareDB.getAllWaresforCus();
        ButterKnife.bind(this);
        recAdapter =new CusWareRecyclerAdaptor(wareList,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recAdapter);
        Spinner warespinner=findViewById(R.id.warespinnerforcustomer);
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, wareGroupNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        warespinner.setAdapter(adapter);
        warespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position!=0)
                    wareList=wareDB.showWareListByGroupID(position);

                else
                    wareList = wareDB.getAllWaresforCus();


                    recAdapter = new CusWareRecyclerAdaptor(wareList, CustomerFirstPage.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CustomerFirstPage.this));
                    recyclerView.setAdapter(recAdapter);
                    recAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override public void onItemClick(View view, int position, Object data) {
                        Ware ware =wareList.get(position);
                        Intent intent=new Intent(CustomerFirstPage.this , SingleOrder.class);
                        intent.putExtra("customerID",customer.getID());
                        intent.putExtra("wareID",ware.getID());
                        startActivity(intent);
                    }
                });
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        recAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                Ware ware =wareList.get(position);
                Toast.makeText(context, "Click : "+position, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context , SingleOrder.class);
                intent.putExtra("customerID",customer.getID());
                intent.putExtra("ware",ware);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
            finish();
    }
}
