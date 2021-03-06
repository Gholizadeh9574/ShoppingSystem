package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.models.Customer;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;


public class Account_Panel extends AppCompatActivity {
    @BindView(R.id.renewaccountnametext)
    EditText accountNametext;
    @BindView(R.id.renewnametext)
    EditText nametext;
    @BindView(R.id.renewlastnametext)
    EditText lastNametext;
    @BindView(R.id.renewphonetext)
    EditText phonetext;
    @BindView(R.id.renewaddresstext)
    EditText addresstext;
    @BindView(R.id.renewpasswordtext)
    EditText passtext;
    @BindView(R.id.rerepeatpasswordtext)
    EditText repeatPasstext;
    @BindView(R.id.changeccountdetailsbutton)
    Button saveChangesbtn;
    @BindView(R.id.deletaccounbutton)
    Button deletAccountbtn;
    @BindView(R.id.reaccontnamewarning)
    TextView accuntNameERRORtv;
    @BindView(R.id.acpanel_passERRORtv)
    TextView repeatPassERRORtv;

    Context context = this;
    Customer customer;
    CustomersDB customersDB = new CustomersDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__panel);
        int enterCusID = getIntent().getIntExtra("customerID", 0);
        customer = customersDB.getCustomerByID(enterCusID);

        ButterKnife.bind(this);

        accountNametext.setText(customer.getAccount_name());
        nametext.setText(customer.getName());
        lastNametext.setText(customer.getLastname());
        phonetext.setText(customer.getPhone());
        addresstext.setText(customer.getAddress());
        passtext.setText(customer.getPassword());


        phonetext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!phonetext.getText().toString().isEmpty()) {
                    if (!phonetext.getText().toString().startsWith("09") || phonetext.getText().toString().length() != 11) {
                        phonetext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                        Toast.makeText(context, getResources().getString(R.string.phoneerror), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        phonetext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phonetext.setBackgroundColor(getResources().getColor(R.color.white));
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
        passtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!passtext.getText().toString().isEmpty()) {
                    if (passtext.getText().toString().length() < 4) {
                        passtext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                        Toast.makeText(context, getResources().getString(R.string.passleghterror), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        repeatPasstext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!repeatPasstext.getText().toString().isEmpty()) {
                    if (!repeatPasstext.getText().toString().equals(passtext.getText().toString()))
                        repeatPassERRORtv.setVisibility(VISIBLE);
                }
            }
        });
        repeatPasstext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                repeatPassERRORtv.setVisibility(View.INVISIBLE);
                repeatPasstext.setBackgroundColor(getResources().getColor(R.color.white));

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        accountNametext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accuntNameERRORtv.setVisibility(View.INVISIBLE);
                accountNametext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {
                String accountname = accountNametext.getText().toString();
                if (!accountname.isEmpty()) {
                    boolean isExists = customersDB.isAccountNameUsed(accountname);
                    if (isExists)
                        accuntNameERRORtv.setVisibility(VISIBLE);
                }
            }
        });

        nametext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nametext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastNametext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastNametext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addresstext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addresstext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveChangesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountNametext.getText().toString().isEmpty() ||
                        accuntNameERRORtv.getVisibility() == VISIBLE)
                    accountNametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (nametext.getText().toString().isEmpty())
                    nametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (lastNametext.getText().toString().isEmpty())
                    lastNametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (phonetext.getText().toString().isEmpty())
                    phonetext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (addresstext.getText().toString().isEmpty())
                    addresstext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (passtext.getText().toString().isEmpty())
                    passtext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (repeatPasstext.getText().toString().isEmpty())
                    repeatPasstext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (passtext.getText().toString().length() < 4)
                    Toast.makeText(context, getResources().getString(R.string.passleghterror), Toast.LENGTH_SHORT).show();
                else if (repeatPassERRORtv.getVisibility() == VISIBLE)
                    Toast.makeText(context, getResources().getString(R.string.entercorrctlyrepass), Toast.LENGTH_SHORT).show();
                else {
                    Customer newcustomer = new Customer();
                    newcustomer.setID(customer.getID());
                    newcustomer.setAccount_name(accountNametext.getText().toString());
                    newcustomer.setName(nametext.getText().toString());
                    newcustomer.setLastname(lastNametext.getText().toString());
                    newcustomer.setAddress(addresstext.getText().toString());
                    newcustomer.setPhone(phonetext.getText().toString());
                    newcustomer.setIsactive(true);
                    newcustomer.setPassword(passtext.getText().toString());
                    customersDB.update(newcustomer, customer.getID());

                    changedialog();

                }

            }
        });

        deletAccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletAccountDialog();

            }
        });


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

    private void deletAccountDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setTitle(getResources().getString(R.string.deletaccountwarnning));
        myAlertDialog.setCancelable(true);
        myAlertDialog.setPositiveButton(getResources().getString(R.string.ok), null);
        myAlertDialog.setNegativeButton(getResources().getString(R.string.cancel), null);

        final AlertDialog alertDialog = myAlertDialog.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button dialogButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        customersDB.delet(customer.getID());

                        alertDialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });

        alertDialog.show();
    }

}