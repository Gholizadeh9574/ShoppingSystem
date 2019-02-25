package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import amirhosssein.shoppingsystem.database.AdminDB;
import amirhosssein.shoppingsystem.models.Admin;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Admin_Panel extends AppCompatActivity {

    Intent intent;
    Context context=this;
    Admin admin;


    @OnClick({R.id.ordersforconfirmbutton,R.id.waresforadminbutton,R.id.customersforadminbutton,R.id.adminaccountpanelbutton,R.id.adminsbutton})
    public void onclick(View view){
        switch (view.getId()) {
            case R.id.ordersforconfirmbutton:
                intent = new Intent(context, CartsForAdmins.class);
                intent.putExtra("adminID", admin.getID());
                startActivity(intent);
                break;
            case R.id.waresforadminbutton:
                intent = new Intent(context, WaresInfo.class);
                startActivity(intent);
                break;
            case R.id.customersforadminbutton:
                intent =new Intent(context,CustomersList.class);
                startActivity(intent);
                break;
            case R.id.adminaccountpanelbutton:
                intent=new Intent(context,AdminAccountPanel.class);
                intent.putExtra("adminID",admin.getID());
                startActivityForResult(intent,1);
                break;
            case R.id.adminsbutton:
                intent =new Intent(context,AdminsList.class);
                intent.putExtra("adminID",admin.getID());
                startActivity(intent);
                break;
                }
        }

    AdminDB adminDB=new AdminDB(context);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__panel);
        admin=adminDB.getAdminByID(getIntent().getIntExtra("adminID",0));

        ButterKnife.bind(this);
        Button adminsbtn=findViewById(R.id.adminsbutton);

        if (admin.isMainAdmin()) {
            adminsbtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
            finish();
    }
}
