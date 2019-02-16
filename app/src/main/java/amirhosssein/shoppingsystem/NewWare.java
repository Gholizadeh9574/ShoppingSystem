package amirhosssein.shoppingsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.database.WareGroupDB;
import amirhosssein.shoppingsystem.models.Ware;

public class NewWare extends AppCompatActivity {

    Context context=this;

    EditText wareNametext,wareParice,wareStock;
    Spinner spinner;
    Button saveWarebtn;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    WareDB wareDB=new WareDB(context);
    WareGroupDB wareGroupDB=new WareGroupDB(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ware);

        wareNametext=findViewById(R.id.newware_warenametext);
        wareParice=findViewById(R.id.newware_warepricetext);
        wareStock=findViewById(R.id.newware_stoktext);
        saveWarebtn=findViewById(R.id.newware_savewarebutton);

        list=wareGroupDB.getAllWareGroupName();

        spinner=findViewById(R.id.newware_spinnerforGW);

        adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        saveWarebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wareGroupNUM=spinner.getSelectedItemPosition();
                if (!wareNametext.getText().toString().isEmpty()){
                    if (wareGroupNUM!=0){
                        if (!wareParice.getText().toString().isEmpty() && Integer.parseInt(wareParice.getText().toString())>-1){
                            if (!wareStock.getText().toString().isEmpty()&&Integer.parseInt(wareStock.getText().toString())>-1){
                                Ware ware=new Ware();
                                ware.setName(wareNametext.getText().toString());
                                ware.setPrice(Integer.valueOf(wareParice.getText().toString()));
                                ware.setStock(Integer.valueOf(wareStock.getText().toString()));
                                ware.setInHolding(0);

                                wareDB.insert(ware);

//                                dialog();

                            }
                            else{
                                wareStock.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                                Toast.makeText(context, getResources().getText(R.string.entervalidvalue), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            wareParice.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                            Toast.makeText(context, getResources().getText(R.string.entervalidvalue), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        spinner.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                        Toast.makeText(context, getResources().getText(R.string.entervalidvalue), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    wareNametext.setBackgroundColor(getResources().getColor(R.color.edittextwarning));
                    Toast.makeText(context, getResources().getText(R.string.entervalidvalue), Toast.LENGTH_SHORT).show();
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
        wareParice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wareParice.setBackgroundColor(getResources().getColor(R.color.white));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        wareStock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wareStock.setBackgroundColor(getResources().getColor(R.color.white));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void dialog(){
        AlertDialog.Builder myAlertDialog=new AlertDialog.Builder(this);
        myAlertDialog.setTitle(getResources().getString(R.string.wareseved));
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
