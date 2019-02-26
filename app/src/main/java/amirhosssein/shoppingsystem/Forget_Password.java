package amirhosssein.shoppingsystem;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.models.Customer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Forget_Password extends AppCompatActivity {

    Context context=this;
    @BindView(R.id.forget_accuntnametext)
    EditText accountNametext;
    @BindView(R.id.forget_button)
    Button sendPass;
    CustomersDB customersDB=new CustomersDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);

        ButterKnife.bind(this);

        sendPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!accountNametext.getText().toString().isEmpty()) {
                    String strgAccountName = accountNametext.getText().toString();
                    Customer customer = customersDB.getCustomerByAccountName(strgAccountName);
                    if (customer == null)
                        Toast.makeText(context, "نام کاربری اشتباه است", Toast.LENGTH_SHORT).show();
                    else {

                        Random rnd=new Random();
                        String newPass="";
                        for (int i = 0; i <4 ; i++) {
                            int num=rnd.nextInt(90)+10;
                            String strgNum=String.valueOf(num);
                            newPass+=strgNum;
                        }

                        customer.setPassword(newPass);
                        Toast.makeText(context, "پیامکی حاوی رمز به شماره شما ارسال خواهد شد", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else {
                    accountNametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                }
            }
        });

        accountNametext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accountNametext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
