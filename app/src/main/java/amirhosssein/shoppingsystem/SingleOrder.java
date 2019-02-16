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
import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.models.Customer;
import amirhosssein.shoppingsystem.models.Orders;
import amirhosssein.shoppingsystem.models.Ware;

public class SingleOrder extends AppCompatActivity {


    Context context = this;
    TextView warename, sumprice;
    EditText reqQuantitytext;
    Button addorder;
    int stok;
    int price;
    int finalQuantity;
    int requestQuantity;
    CartsDB cartDB = new CartsDB(context);
    OrdersDB ordersDB = new OrdersDB(context);
    WareDB wareDB=new WareDB(context);
    Ware ware;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_order);

        ware = (Ware) getIntent().getSerializableExtra("ware");
        customer = (Customer) getIntent().getSerializableExtra("customer");
        warename = findViewById(R.id.so_warename);
        sumprice = findViewById(R.id.so_sumpricetv);
        reqQuantitytext = findViewById(R.id.so_quantitytext);
        addorder = findViewById(R.id.addorderbtn);
        warename.setText(ware.getName());
        sumprice.setText("ارزش سفارش شما : " + ware.getPrice() + " تومان");
        stok = ware.getStock();
        price = ware.getPrice();

        reqQuantitytext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int orderValue;
                if (reqQuantitytext.getText().toString().isEmpty()) {
                    Log.i("MyApp", "onTextChanged: 2222");
                    sumprice.setText("");
                } else if (stok - Integer.parseInt(reqQuantitytext.getText().toString()) >= 0) {
                    Log.i("MyApp", "onTextChanged: 1111");
                    int num = Integer.valueOf(reqQuantitytext.getText().toString());
                    orderValue = num * price;
                    sumprice.setTextColor(getResources().getColor(R.color.textgreen));
                    sumprice.setText("ارزش سفارش شما : " + orderValue + " تومان");
                } else {
                    sumprice.setTextColor(getResources().getColor(R.color.textviewwarnning));
                    sumprice.setText(getResources().getText(R.string.notenoughstock));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQuantity = Integer.valueOf(reqQuantitytext.getText().toString());
                customDialog();
            }
        });

    }

    private void customDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("تایید نهایی");
        alertDialogBuilder.setMessage(ware.getName() + " به  تعداد " + requestQuantity);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok), null);
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), null);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button dialogButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        addOrderToDatabase();

                        finalConfirmDialog();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        alertDialog.show();
    }

    private void finalConfirmDialog() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("سفارش شما در سبد خرید ثبت شد");
        alertBuilder.setPositiveButton(getResources().getString(R.string.ok), null);
        final AlertDialog alert = alertBuilder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button dialogButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });
        alert.show();
    }

    private void addOrderToDatabase() {
        int cartID = cartDB.getOpenCartIDByCustomerID(customer.getID());
        Orders order = ordersDB.getOrder_WareInCart(cartID, ware.getID());
        if (order.getID() == 0) {
            Orders order1 = new Orders();
            order1.setCartID(cartID);
            order1.setWareID(ware.getID());
            finalQuantity = requestQuantity;
            order1.setQuantity(finalQuantity);
            ordersDB.insert(order1);
            int oldstock=ware.getStock();
            int newtock=oldstock-finalQuantity;
            int oldinhold=ware.getInHolding();
            int newinholding=oldinhold+finalQuantity;
            ware.setStock(newtock);
            ware.setInHolding(newinholding);
            wareDB.update(ware,ware.getID());
        } else {
            finalQuantity = requestQuantity + order.getQuantity();
            Orders order1 = new Orders(cartID, ware.getID(), finalQuantity);
            ordersDB.update(order1, order.getID());
            int oldstock=ware.getStock();
            int newtock=oldstock-finalQuantity;
            int oldinhold=ware.getInHolding();
            int newinholding=oldinhold+finalQuantity;
            ware.setStock(newtock);
            ware.setInHolding(newinholding);
            wareDB.update(ware,ware.getID());
        }
        wareDB.addRequestQuantityToInHold(ware.getID(),finalQuantity);
    }
}

