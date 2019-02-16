package amirhosssein.shoppingsystem;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import amirhosssein.shoppingsystem.database.CustomersDB;
import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.models.Customer;

public class CustomerDetails extends AppCompatActivity {

    Context context=this;
    LinearLayout layout;
    TextView accountNametv,fullNametv,customerIDtv,phonetv,addresstv,totalbuytv;
    Switch aSwitch;
    OrdersDB ordersDB=new OrdersDB(context);
    CustomersDB customersDB=new CustomersDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        final Customer customer= (Customer) getIntent().getSerializableExtra("customer");
        layout =findViewById(R.id.customerdetails_layout);
        accountNametv=findViewById(R.id.customerdetails_accountnametv);
        fullNametv=findViewById(R.id.customerdetails_fullnametv);
        customerIDtv=findViewById(R.id.customerdetails_idtv);
        phonetv=findViewById(R.id.customerdetails_phonetv);
        addresstv=findViewById(R.id.customerdetails_addresstv);
        totalbuytv=findViewById(R.id.customerdetails_totalbuytv);
        aSwitch=findViewById(R.id.customerdetails_isactiveswitch);

        if (customer.isIsactive()){
            aSwitch.setChecked(true);
            layout.setBackgroundColor(getResources().getColor(R.color.cardcolorfullstock));
        }
        else {
            aSwitch.setChecked(false);
            layout.setBackgroundColor(getResources().getColor(R.color.cardcoloremptystok));
        }

        String accountName="نام کاربری : "+customer.getAccount_name();
        accountNametv.setText(accountName);
        String fullName=customer.getName()+" "+customer.getLastname();
        fullNametv.setText(fullName);
        final String customerID="شناسه کاربری : "+customer.getID();
        customerIDtv.setText(customerID);
        String phone ="تلفن همراه : "+customer.getPhone();
        phonetv.setText(phone);
        String address=" آدرس : "+customer.getAddress();
        addresstv.setText(address);

        int totalbuy=ordersDB.getTotalOrdersPriceForCustomerByCustomerID(customer.getID());
        String totalbuystrg="میزان خرید : "+totalbuy+" تومان";
        totalbuytv.setText(totalbuystrg);


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout.setBackgroundColor(getResources().getColor(R.color.cardcolorfullstock));
                    customer.setIsactive(true);
                    customersDB.update(customer,customer.getID());
                }
                else {
                    layout.setBackgroundColor(getResources().getColor(R.color.cardcoloremptystok));
                    customer.setIsactive(false);
                    customersDB.update(customer,customer.getID());
                }
            }
        });


    }
}
