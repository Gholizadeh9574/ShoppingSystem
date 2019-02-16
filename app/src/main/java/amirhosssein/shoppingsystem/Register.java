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

import amirhosssein.shoppingsystem.database.CartsDB;
import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.models.Carts;
import amirhosssein.shoppingsystem.models.Customer;

public class Register extends AppCompatActivity {

    String TAG="MyApp";
    Context context =this;
    EditText accountName , name , lastname , phone , address , newpass , repeatpass;
    TextView accountnamewarning , passerror;
    Button registerbutt;
    CustomersDB customersDB =new CustomersDB(context);
    CartsDB cartsDB=new CartsDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accountName=findViewById(R.id.newaccountnametext);
        name=findViewById(R.id.newnametext);
        lastname=findViewById(R.id.newlastnametext);
        phone=findViewById(R.id.newphonetext);
        address=findViewById(R.id.newaddresstext);
        newpass=findViewById(R.id.newpasswordtext);
        repeatpass=findViewById(R.id.repeatnewpasswordtext);
        accountnamewarning=findViewById(R.id.accountnamewarnning);
        passerror=findViewById(R.id.passerrorst);
        registerbutt=findViewById(R.id.regiterbutton);

        registerbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountName.getText().toString().isEmpty() || accountnamewarning.getVisibility()==View.VISIBLE)
                    accountName.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (name.getText().toString().isEmpty())
                    name.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (lastname.getText().toString().isEmpty())
                    lastname.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (phone.getText().toString().isEmpty())
                    phone.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (address.getText().toString().isEmpty())
                    address.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (newpass.getText().toString().isEmpty())
                    newpass.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (repeatpass.getText().toString().isEmpty())
                    repeatpass.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                else if (newpass.getText().toString().length()<4)
                    Toast.makeText(Register.this, getResources().getString(R.string.passleghterror), Toast.LENGTH_SHORT).show();
                else if (!newpass.getText().toString().equals(repeatpass.getText().toString())) {
                    Toast.makeText(Register.this, getResources().getString(R.string.entercorrctlyrepass), Toast.LENGTH_SHORT).show();
                    repeatpass.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                }
                else{
                    addCustomerToDatabase();

                }
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!phone.getText().toString().isEmpty()){
                    if (!phone.getText().toString().startsWith("09") || phone.getText().toString().length()!=11){
                        phone.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                        Toast.makeText(context, getResources().getString(R.string.phoneerror), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newpass.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        newpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!newpass.getText().toString().isEmpty()){
                if (newpass.getText().toString().length()<4) {
                    newpass.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                    Toast.makeText(Register.this, getResources().getString(R.string.passleghterror), Toast.LENGTH_LONG).show();
                }
                }
            }
        });

        repeatpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                    if (!repeatpass.getText().toString().equals(newpass.getText().toString()))
                    passerror.setVisibility(View.VISIBLE);
            }
        });
        repeatpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passerror.setVisibility(View.INVISIBLE);
                repeatpass.setBackgroundColor(getResources().getColor(R.color.white));

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        accountName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accountnamewarning.setVisibility(View.INVISIBLE);
                accountName.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {
                String accountname=accountName.getText().toString();
                if (!accountname.isEmpty()) {
                    boolean isExists = customersDB.isAccountNameUsed(accountname);
                    if (isExists)
                        accountnamewarning.setVisibility(View.VISIBLE);
                }
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastname.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                address.setBackgroundColor(getResources().getColor(R.color.white));
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

    public void addCustomerToDatabase(){

        Customer customer =new Customer();

        customer.setAccount_name(accountName.getText().toString());
        customer.setName(name.getText().toString());
        customer.setLastname(lastname.getText().toString());
        customer.setPhone(phone.getText().toString());
        customer.setAddress(address.getText().toString());
        customer.setPassword(newpass.getText().toString());
        customer.setIsactive(true);
        Log.d(TAG, "onClick: befor insert ");
        customersDB.insert(customer);
        Log.d(TAG, "Register onClick : after insert customer");


        Customer customer1=customersDB.getCustomerByAccountName(customer.getAccount_name());
        Carts cart=new Carts();
        int newCartUD=cartsDB.getNewCartID();
        cart.setID(newCartUD);
        cart.setCustomerID(customer1.getID());
        cart.setConfirmStatus(false);
        cart.setSendStatus(false);
        cartsDB.insert(cart);

        dialog();

    }

}
