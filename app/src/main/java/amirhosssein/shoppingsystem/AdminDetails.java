package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import amirhosssein.shoppingsystem.database.AdminDB;
import amirhosssein.shoppingsystem.models.Admin;

public class AdminDetails extends AppCompatActivity {


    Context context=this;
    TextView adminIDtv , fullNametv;
    Switch mainAdminswch,isActiveswch;
    Button savebtn;

    Admin admin;

    AdminDB adminDB=new AdminDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);

        admin= (Admin) getIntent().getSerializableExtra("admin");

        adminIDtv=findViewById(R.id.admindetails_adminIDtv);
        fullNametv =findViewById(R.id.admindetails_fullnametv);
        mainAdminswch=findViewById(R.id.admindetails_mainadminswitch);
        isActiveswch=findViewById(R.id.admindetails_isactiveswitch);
        savebtn=findViewById(R.id.admindetails_savebtn);

        String strgAdminID="شناسه کاربری : "+admin.getID();
        adminIDtv.setText(strgAdminID);

        String strgFullName=admin.getName()+" "+admin.getLastname();
        fullNametv.setText(strgFullName);

        mainAdminswch.setChecked(admin.isMainAdmin());

        isActiveswch.setChecked(admin.isAcvive());

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin newAdmin=admin;
                newAdmin.setMainAdmin(mainAdminswch.isChecked());
                newAdmin.setAcvive(isActiveswch.isChecked());
                adminDB.update(newAdmin,admin.getID());
                dialog();
            }
        });



    }

    private void dialog(){
        AlertDialog.Builder myAlertDialog=new AlertDialog.Builder(this);
        myAlertDialog.setTitle(getResources().getString(R.string.chengeisdone));
        myAlertDialog.setCancelable(false);
        myAlertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        final AlertDialog alertDialog=myAlertDialog.create();
        alertDialog.show();
    }

}
