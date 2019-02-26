package amirhosssein.shoppingsystem;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.database.WareGroupDB;
import amirhosssein.shoppingsystem.models.Ware;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WareDetails extends AppCompatActivity {

    Context context = this;

    @BindView(R.id.waredetails_wareIDtv)
    TextView wareIDtv;
    @BindView(R.id.waredetails_waregrouptv)
    TextView wareGroupNametv;
    @BindView(R.id.waredetails_salesamounttv)
    TextView wareSalesAmounttv;
    @BindView(R.id.waredetails_inholdtv)
    TextView wareInHoldtv;
    @BindView(R.id.waredetails_warenametext)
    EditText wareNametext;
    @BindView(R.id.waredetails_warepricetext)
    EditText warePricetext;
    @BindView(R.id.waredetails_instocknumtext)
    EditText stocktext;
    @BindView(R.id.waredetails_saveChangesbutton)
    Button saveChangesbtn;

    WareGroupDB wareGroupDB = new WareGroupDB(context);
    OrdersDB ordersDB = new OrdersDB(context);
    WareDB wareDB = new WareDB(context);

    Ware ware = new Ware();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_details);
        ware = wareDB.getWareByID(getIntent().getIntExtra("wareID", 0));
        ButterKnife.bind(this);

        String wareGroupName = wareGroupDB.getGroupNameByGroupID(ware.getWareGroupID());
        int salseAmount = ordersDB.getSalseAmountByWareID(ware.getID());
        wareIDtv.setText("شناسه کالا : " + ware.getID());
        wareGroupNametv.setText("گروه : " + wareGroupName);
        wareInHoldtv.setText("تعداد در سبد خرید : " + ware.getInHolding() + " واحد");
        wareSalesAmounttv.setText("میزان فروش : " + salseAmount + " واحد");

        wareNametext.setText(ware.getName());
        stocktext.setText(String.valueOf(ware.getStock()));
        warePricetext.setText(String.valueOf(ware.getPrice()));

        saveChangesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wareNametext.getText().toString().isEmpty() && wareNametext.getText().toString().length() > 4) {
                    if (Integer.parseInt(stocktext.getText().toString()) >= 0 && !stocktext.getText().toString().isEmpty()) {
                        if (Integer.parseInt(warePricetext.getText().toString()) > 0 && !warePricetext.getText().toString().isEmpty()) {
                            ware.setName(wareNametext.getText().toString());
                            ware.setStock(Integer.valueOf(stocktext.getText().toString()));
                            ware.setPrice(Integer.valueOf(warePricetext.getText().toString()));
                            wareDB.update(ware, ware.getID());
                            Toast.makeText(context, "تغییرات انجام شد", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        stocktext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                        Toast.makeText(context, "تعداد وارد شده صحیح نیست.", Toast.LENGTH_SHORT).show();
                    }
                } else if (wareNametext.getText().toString().length() <= 4) {
                    wareNametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                    Toast.makeText(context, "طول نام وارد شده کمتر از حد مجاز است", Toast.LENGTH_SHORT).show();
                } else if (wareNametext.getText().toString().isEmpty()) {
                    wareNametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                }
            }
        });

        wareNametext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wareNametext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        stocktext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                stocktext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        warePricetext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                warePricetext.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
