package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.adaptor.AdminListAdaptor;
import amirhosssein.shoppingsystem.database.AdminDB;
import amirhosssein.shoppingsystem.models.Admin;
import amirhosssein.shoppingsystem.models.OnItemClickListener;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminsList extends AppCompatActivity {

    Context context = this;
    RecyclerView recyclerView;
    AdminListAdaptor adaptor;
    ArrayList<Admin> list;

    @OnClick(R.id.adminlist_newadminbtn)
    void newAdmin() {
        Intent intent = new Intent(context, NewAdmin.class);
        startActivity(intent);
    }

    AdminDB adminDB = new AdminDB(context);

    Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_list);

        ButterKnife.bind(this);
        admin = adminDB.getAdminByID(getIntent().getIntExtra("adminID", 0));

        recyclerView = findViewById(R.id.adminlist_recy);

    }


    @Override
    protected void onResume() {
        super.onResume();
        list = adminDB.getAllAdminsButCurectByAdminID(admin.getID());
        adaptor = new AdminListAdaptor(context, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adaptor);
        adaptor.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                Intent intent = new Intent(context, AdminDetails.class);
                intent.putExtra("adminID", list.get(position).getID());
                startActivity(intent);
            }
        });
    }
}
