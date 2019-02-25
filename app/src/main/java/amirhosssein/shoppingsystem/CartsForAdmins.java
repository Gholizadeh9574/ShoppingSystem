package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.adaptor.CartsForAdminsAdaptor;
import amirhosssein.shoppingsystem.database.AdminDB;
import amirhosssein.shoppingsystem.database.CartsDB;
import amirhosssein.shoppingsystem.models.Admin;
import amirhosssein.shoppingsystem.models.Carts;
import amirhosssein.shoppingsystem.models.OnItemClickListener;

public class CartsForAdmins extends AppCompatActivity {

    RecyclerView recyclerView;
    CartsForAdminsAdaptor adaptor;
    ArrayList<Carts> cartsList;
    Context context=this;
    CartsDB cartsDB=new CartsDB(context);
    AdminDB adminDB=new AdminDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Admin admin=adminDB.getAdminByID(getIntent().getIntExtra("adminID",0));
        setContentView(R.layout.activity_carts_for_admins);
        cartsList=cartsDB.getAllOpenCarts();
        recyclerView=findViewById(R.id.recy_cartsforadmin);
        adaptor=new CartsForAdminsAdaptor(cartsList,context,admin.getID());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adaptor);

        adaptor.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                Carts cart=cartsList.get(position);
                Intent intent=new Intent(context,CartDetails.class);
                intent.putExtra("cart",cart);
                startActivity(intent);
            }
        });

    }
}
