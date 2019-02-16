package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import amirhosssein.shoppingsystem.database.AdminDB;
import amirhosssein.shoppingsystem.models.Admin;

public class NewAdmin extends AppCompatActivity {

    Context context=this;

    TextView newAdminIDtv , passERROR;
    EditText adminNametext,adminLastNametext, passtext, repasstext;
    Switch mainSwitch;
    Button saveButton;

    int adminID;

    AdminDB adminDB=new AdminDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin);

        newAdminIDtv=findViewById(R.id.newadmin_adminIDtv);
        passERROR=findViewById(R.id.newadmin_passerrortv);
        adminNametext=findViewById(R.id.newadmin_adminnametext);
        adminLastNametext=findViewById(R.id.newadmin_adminlastnametext);
        mainSwitch=findViewById(R.id.newadmin_mainadminswitch);
        passtext =findViewById(R.id.newadmin_passtext);
        repasstext =findViewById(R.id.newadmin_repasstext);
        saveButton=findViewById(R.id.newadmin_saveadminbtn);

        adminID=adminDB.getNewAdminID();

        newAdminIDtv.setText("شناسه مدیر جدید : "+adminID);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!adminNametext.getText().toString().isEmpty() && adminNametext.getText().toString().length()>3){
                    if (!adminLastNametext.getText().toString().isEmpty() && adminLastNametext.getText().toString().length()>4){
                        if (!passtext.getText().toString().isEmpty() && passtext.getText().toString().length()>3){
                            if (repasstext.getText().toString().equals(passtext.getText().toString())){
                                Admin admin=new Admin();
                                admin.setID(adminID);
                                admin.setName(adminNametext.getText().toString());
                                admin.setLastname(adminLastNametext.getText().toString());
                                admin.setPassword(passtext.getText().toString());
                                admin.setMainAdmin(mainSwitch.isChecked());
                                admin.setAcvive(true);

                                adminDB.insert(admin);
                                dialog();

                            }
                            else {
                                repasstext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                                Toast.makeText(context, "مقدار صحیح را وارد کنید", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            passtext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                            Toast.makeText(context, "مقدار صحیح را وارد کنید", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        adminLastNametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                        Toast.makeText(context, "مقدار صحیح را وارد کنید", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    adminNametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                    Toast.makeText(context, "مقدار صحیح را وارد کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });


        repasstext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String repassString= repasstext.getText().toString();
                if (!repassString.equals("") && !repassString.equals(passtext.getText().toString())){
                    passERROR.setVisibility(View.VISIBLE);
                    repasstext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                }
            }
        });


        adminNametext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adminNametext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        adminLastNametext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adminLastNametext.setBackgroundColor(getResources().getColor(R.color.white));
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
        repasstext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                repasstext.setBackgroundColor(getResources().getColor(R.color.white));
                passERROR.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void dialog(){
        AlertDialog.Builder myAlertDialog=new AlertDialog.Builder(this);
        myAlertDialog.setTitle(getResources().getString(R.string.registercompleted));
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
