package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.Intent;
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

import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.models.Customer;

public class MainActivity extends AppCompatActivity {

    Context context=this;

    String TAG="MyApp";
    EditText accountnametext,passwordtext;
    Button enterbtn;
    TextView adminenterst,registerst,forgetst,wrongaccoutpass;
    Intent intent;

    CustomersDB customersDB=new CustomersDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountnametext=findViewById(R.id.enteraccountname);
        passwordtext=findViewById(R.id.entrypassword);
        enterbtn=findViewById(R.id.entercustombutton);
        adminenterst=findViewById(R.id.adminentrytext);
        registerst=findViewById(R.id.regitertext);
        forgetst=findViewById(R.id.forgetpasswordtext);
        wrongaccoutpass=findViewById(R.id.wrongaccontpass);


        enterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountnametext.getText().toString().isEmpty())
                    accountnametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                if (passwordtext.getText().toString().isEmpty())
                    passwordtext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                if (!accountnametext.getText().toString().isEmpty() && !passwordtext.getText().toString().isEmpty()) {
                    String accountname = accountnametext.getText().toString();
                    String password = passwordtext.getText().toString();
                    boolean isExists = customersDB.logIn(accountname, password);
                    Log.d(TAG, "onClick: " + isExists);
                    if (isExists) {
                        Customer customer = customersDB.getCustomerByAccountName(accountname);

                        if (customer.isIsactive()) {
                            Log.i(TAG, "onClick: Active");
                            intent = new Intent(context, CustomerFirstPage.class);
                            intent.putExtra("customer", customer);
                            Log.i(TAG, "onClick: after Intent");
                            startActivity(intent);
                        }
                        else{
                            Log.i(TAG, "onClick: Active = false");
                            wrongaccoutpass.setText(getResources().getString(R.string.notallow));
                            wrongaccoutpass.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                            Log.i(TAG, "onClick: isExists  = false");
                            wrongaccoutpass.setText(getResources().getString(R.string.wrongaccontnameorpass));
                            wrongaccoutpass.setVisibility(View.VISIBLE);
                        }
                }
            }
        });

        passwordtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordtext.setBackgroundColor(getResources().getColor(R.color.white));
                wrongaccoutpass.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        accountnametext.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accountnametext.setBackgroundColor(getResources().getColor(R.color.white));
                wrongaccoutpass.setVisibility(View.INVISIBLE);
            }

            public void afterTextChanged(Editable s) {}
        });

        adminenterst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context,Admin_Enter.class);
                startActivity(intent);
            }
        });

        registerst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(context,Register.class);
                startActivity(intent);
            }
        });

        forgetst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(context,Forget_Password.class);
                startActivity(intent);
            }
        });


    }
}
