package amirhosssein.shoppingsystem.adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import amirhosssein.shoppingsystem.R;
import amirhosssein.shoppingsystem.database.CartsDB;
import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.models.Carts;
import amirhosssein.shoppingsystem.models.OnItemClickListener;
import amirhosssein.shoppingsystem.models.Orders;
import amirhosssein.shoppingsystem.models.Ware;

public class CartsForAdminsAdaptor extends RecyclerView.Adapter<CartsForAdminsAdaptor.ViewHolder> {

    private List<Carts> list;
    private Context context;
    private int adminID;
    private OrdersDB ordersDB;
    private CartsDB cartsDB;
    private WareDB wareDB;

    public CartsForAdminsAdaptor(List<Carts> list, Context context, int adminID) {
        this.list = list;
        this.context = context;
        this.adminID = adminID;
        ordersDB = new OrdersDB(context);
        cartsDB = new CartsDB(context);
        wareDB=new WareDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_carts_for_admin, parent, false);

        return new CartsForAdminsAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Carts cart = list.get(position);
        int totalNum = ordersDB.getTotalQuantityOfWaresByCartID(cart.getID());
        int totalSumPrice = ordersDB.getTotalOrderValueByCartID(cart.getID());
        String customerFullName = cartsDB.getCustomerNameByCartID(cart.getID());
        holder.cusName.setText(customerFullName);
        holder.totalNum.setText("تعداد اجناس : " + totalNum);
        holder.sumPrice.setText("ارزش کل سفارش : " + totalSumPrice + " تومان");
        holder.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carts selectedCart = list.get(position);
                selectedCart.setSendStatus(true);
                selectedCart.setAdminID(adminID);
                cartsDB.update(selectedCart, selectedCart.getID());
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeRemoved(position, list.size());
                updateWaresByCartIDWhenCartSent(cart.getID());
                Toast.makeText(context, "سفارش شماره " + selectedCart.getID() + " ارسال خواهد شد", Toast.LENGTH_LONG).show();

            }
        });

        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carts selectedCart = list.get(position);
                cartsDB.delete(selectedCart.getID());
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeRemoved(position, list.size());
                updateWaresByCartIDWhenCartCanceled(cart.getID());
                Toast.makeText(context, "سفارش شماره : " + selectedCart.getID() + " حذف شد.", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cusName, totalNum, sumPrice;
        Button okButton, cancelButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cusName = itemView.findViewById(R.id.card_cartsforadmin_customernametv);
            totalNum = itemView.findViewById(R.id.card_cartsforadmin_totalnumtv);
            sumPrice = itemView.findViewById(R.id.card_cartsforadmin_sumpricetv);
            okButton = itemView.findViewById(R.id.card_cartsforadmin_okbutton);
            cancelButton = itemView.findViewById(R.id.card_cartsforadmin_cancelbutton);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                int adapterPosition = getAdapterPosition();
                mItemClickListener.onItemClick(v, adapterPosition, list.get(adapterPosition));
            }
        }
    }

        private OnItemClickListener mItemClickListener;

        public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
            this.mItemClickListener = itemClickListener;
        }

        private void updateWaresByCartIDWhenCartSent(int cartID){
            ArrayList<Orders> list=ordersDB.getOrderByCartID(cartID);
            for (int i = 0; i <list.size() ; i++) {
                Orders order=list.get(i);
                Ware ware=wareDB.getWareByID(order.getWareID());
                int oldinhold=ware.getInHolding();
                int newhold=oldinhold-order.getQuantity();
                ware.setInHolding(newhold);
                wareDB.update(ware,ware.getID());


            }
        }

        private void updateWaresByCartIDWhenCartCanceled(int cartID){

            ArrayList<Orders> list=ordersDB.getOrderByCartID(cartID);
            for (int i = 0; i <list.size() ; i++) {
                Orders order = list.get(i);
                Ware ware = wareDB.getWareByID(order.getWareID());
                int oldinhold=ware.getInHolding();
                int oldstock=ware.getStock();
                int newinhold=oldinhold-order.getQuantity();
                int newstock=oldstock+order.getQuantity();
                ware.setInHolding(newinhold);
                ware.setStock(newstock);
                wareDB.update(ware,ware.getID());
            }
        }
}