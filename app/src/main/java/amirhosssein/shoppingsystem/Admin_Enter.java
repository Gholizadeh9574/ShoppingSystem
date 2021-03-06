package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import amirhosssein.shoppingsystem.database.AdminDB;
import amirhosssein.shoppingsystem.models.Admin;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Admin_Enter extends AppCompatActivity {

    @BindView(R.id.enteradminaccountname)
    EditText adminidtext;
    @BindView(R.id.entryadminpassword)
    EditText passtext;
    @BindView(R.id.enteradnimbutton)
    Button enter;
    @BindView(R.id.adminnotactiv)
    TextView adminNotActive;

    Context context = this;
    AdminDB adminDB = new AdminDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__enter);

        ButterKnife.bind(this);

        adminidtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adminidtext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passtext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminID = adminidtext.getText().toString();
                String password = passtext.getText().toString();
                if (adminID.isEmpty())
                    adminidtext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                if (password.isEmpty())
                    passtext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                if (!adminID.isEmpty() && !password.isEmpty()) {

                    boolean isExists = adminDB.login(adminID, password);
                    if (isExists) {
                        Admin admin = adminDB.getAdminByID(Integer.valueOf(adminID));
                        if (admin.isAcvive()) {
                            Intent intent = new Intent(getApplicationContext(), Admin_Panel.class);
                            intent.putExtra("adminID", admin.getID());
                            startActivity(intent);
                        } else {
                            adminNotActive.setText(getResources().getString(R.string.notallow));
                            adminNotActive.setVisibility(View.VISIBLE);
                        }
                    } else {
                        adminNotActive.setText(getResources().getString(R.string.wrongaccontnameorpass));
                        adminNotActive.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        adminidtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                adminNotActive.setVisibility(View.INVISIBLE);
            }
        });

        passtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                adminNotActive.setVisibility(View.INVISIBLE);
            }
        });


    }
}