package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import amirhosssein.shoppingsystem.database.AdminDB;
import amirhosssein.shoppingsystem.models.Admin;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.VISIBLE;

public class AdminAccountPanel extends AppCompatActivity {

    Context context = this;
    Admin admin;
    AdminDB adminDB = new AdminDB(context);

    @OnClick(R.id.adminaccpanel_savecahngebtn)
    public void save() {
        if (nametext.getText().toString().isEmpty())
            nametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
        else if (lastNametext.getText().toString().isEmpty())
            lastNametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
        else if (passtext.getText().toString().isEmpty())
            passtext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
        else if (passtext.getText().toString().length() < 4)
            Toast.makeText(context, getResources().getString(R.string.passleghterror), Toast.LENGTH_SHORT).show();
        else if (passErrortv.getVisibility() == VISIBLE)
            Toast.makeText(context, getResources().getString(R.string.entercorrctlyrepass), Toast.LENGTH_SHORT).show();
        else if (repasstext.getText().toString().trim().isEmpty())
            repasstext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
        else if (!repasstext.getText().toString().trim().equals(passtext.getText().toString().trim()))
            Toast.makeText(context, getResources().getString(R.string.entercorrctlyrepass), Toast.LENGTH_SHORT).show();
        else {
            Admin newAdmin = new Admin();
            newAdmin.setID(admin.getID());
            newAdmin.setName(nametext.getText().toString());
            newAdmin.setLastname(lastNametext.getText().toString());
            newAdmin.setAcvive(true);
            newAdmin.setMainAdmin(admin.isMainAdmin());
            newAdmin.setPassword(passtext.getText().toString());
            adminDB.update(newAdmin, admin.getID());

            changedialog();

        }
    }

    @OnClick(R.id.adminaccpanel_deletaccountbtn)
    public void delete() {
        deletDialog();
    }

    @BindView(R.id.adminaccpanel_adminIDtv)
    TextView adminIDtv;
    @BindView(R.id.adminaccpanel_passERROR)
    TextView passErrortv;
    @BindView(R.id.adminaccpanel_newnametext)
    EditText nametext;
    @BindView(R.id.adminaccpanel_newlastnametext)
    EditText lastNametext;
    @BindView(R.id.adminaccpanel_passtext)
    EditText passtext;
    @BindView(R.id.adminaccpanel_repasstext)
    EditText repasstext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_accunt_panel);

        ButterKnife.bind(this);
        admin = adminDB.getAdminByID(getIntent().getIntExtra("adminID", 0));

        String stringAdminID = "شناسه کاربری : " + admin.getID();
        adminIDtv.setText(stringAdminID);

        nametext.setText(admin.getName());
        lastNametext.setText(admin.getLastname());
        passtext.setText(admin.getPassword());

    }

    private void deletDialog() {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setTitle(getResources().getString(R.string.deletaccountwarnning));
        myAlertDialog.setCancelable(false);
        myAlertDialog.setNegativeButton(getResources().getString(R.string.cancel), null);
        myAlertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                adminDB.delet(admin.getID());
                setResult(RESULT_OK);
                finish();
            }
        });

        final AlertDialog alertDialog = myAlertDialog.create();
        alertDialog.show();
    }

    private void changedialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
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
