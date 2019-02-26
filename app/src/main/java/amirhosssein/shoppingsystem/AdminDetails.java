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
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminDetails extends AppCompatActivity {


    Context context = this;

    @BindView(R.id.admindetails_adminIDtv)
    TextView adminIDtv;
    @BindView(R.id.admindetails_fullnametv)
    TextView fullNametv;
    @BindView(R.id.admindetails_mainadminswitch)
    Switch mainAdminswch;
    @BindView(R.id.admindetails_isactiveswitch)
    Switch isActiveswch;
    @BindView(R.id.admindetails_savebtn)
    Button savebtn;

    Admin admin;

    AdminDB adminDB = new AdminDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);


        ButterKnife.bind(this);
        admin = adminDB.getAdminByID(getIntent().getIntExtra("adminID", 0));


        String strgAdminID = "شناسه کاربری : " + admin.getID();
        adminIDtv.setText(strgAdminID);

        String strgFullName = admin.getName() + " " + admin.getLastname();
        fullNametv.setText(strgFullName);

        mainAdminswch.setChecked(admin.isMainAdmin());

        isActiveswch.setChecked(admin.isAcvive());

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin newAdmin = admin;
                newAdmin.setMainAdmin(mainAdminswch.isChecked());
                newAdmin.setAcvive(isActiveswch.isChecked());
                adminDB.update(newAdmin, admin.getID());
                dialog();
            }
        });


    }

    private void dialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle(getResources().getString(R.string.chengeisdone));
        myAlertDialog.setCancelable(false);
        myAlertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        final AlertDialog alertDialog = myAlertDialog.create();
        alertDialog.show();
    }

}
