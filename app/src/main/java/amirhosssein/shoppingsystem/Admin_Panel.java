package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import amirhosssein.shoppingsystem.models.Admin;

public class Admin_Panel extends AppCompatActivity {

    Context context=this;
    Button ordersbtn ,waresbtn , customersbtn ,accountPanel , adminsbtn;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__panel);
        final Admin admin = (Admin) getIntent().getSerializableExtra("admin");
        ordersbtn=findViewById(R.id.ordersforconfirmbutton);
        waresbtn=findViewById(R.id.waresforadminbutton);
        customersbtn=findViewById(R.id.customersforadminbutton);
        accountPanel=findViewById(R.id.adminaccountpanelbutton);
        adminsbtn=findViewById(R.id.adminsbutton);

        if (admin.isMainAdmin()) {
            adminsbtn.setVisibility(View.VISIBLE);
        }
        ordersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(context,CartsForAdmins.class);
                intent.putExtra("admin",admin);
                startActivity(intent);
            }
        });

        waresbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(context,WaresInfo.class);
                startActivity(intent);
            }
        });

        customersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(context,CustomersList.class);
                startActivity(intent);
            }
        });

        accountPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(context,AdminAccountPanel.class);
                intent.putExtra("admin",admin);
                startActivityForResult(intent,1);
            }
        });

        adminsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(context,AdminsList.class);
                intent.putExtra("admin",admin);
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
