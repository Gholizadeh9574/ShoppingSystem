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

import amirhosssein.shoppingsystem.adaptor.WareInfoAdaptor;
import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.models.OnItemClickListener;
import amirhosssein.shoppingsystem.models.Ware;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WaresInfo extends AppCompatActivity {

    Context context = this;
    @BindView(R.id.recy_wareinfo)
    RecyclerView recyclerView;
    WareInfoAdaptor adaptor;
    ArrayList<Ware> wareList;
    WareDB wareDB = new WareDB(context);
    @BindView(R.id.wareinfo_newwarebutton)
    Button newWarebtn;

    @Override
    protected void onResume() {
        super.onResume();

        wareList = wareDB.getAllWares();
        adaptor = new WareInfoAdaptor(context, wareList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adaptor);
        adaptor.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                Ware ware = wareList.get(position);
                Intent intent = new Intent(context, WareDetails.class);
                intent.putExtra("wareID", ware.getID());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wares_info);
        ButterKnife.bind(this);
        newWarebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewWare.class);
                startActivity(intent);
            }
        });
    }
}
