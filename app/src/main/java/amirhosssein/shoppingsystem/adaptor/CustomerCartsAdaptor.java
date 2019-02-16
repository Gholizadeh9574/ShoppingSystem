package amirhosssein.shoppingsystem.adaptor;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.R;
import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.models.Carts;
import amirhosssein.shoppingsystem.models.OnItemClickListener;

public class CustomerCartsAdaptor extends RecyclerView.Adapter<CustomerCartsAdaptor.ViewHolder> {

     Context context;
     ArrayList<Carts> list;
     OrdersDB ordersDB;

    public CustomerCartsAdaptor(Context context, ArrayList<Carts> list) {
        this.context = context;
        this.list = list;
        ordersDB=new OrdersDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_customercarts, parent, false);

        return new CustomerCartsAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Carts cart =list.get(position);

        String id="شناسه سبدخرید : "+cart.getID();
        holder.cartIDtv.setText(id);


        int totalQuantity=ordersDB.getTotalQuantityOfWaresByCartID(cart.getID());
        String quantity="تعداد اجناس در سبد خرید : "+totalQuantity+" عدد";
        holder.totalOrderQuantity.setText(quantity);

        int orderPrice=ordersDB.getTotalOrdersPriceForCustomerByCustomerID(cart.getCustomerID());
        String totalOrderPrice="بهای کل سفارش : "+orderPrice;
        holder.totalOrderPrice.setText(totalOrderPrice);

        boolean isSent=cart.isSendStatus();
        if (isSent)
            holder.cardView.setBackgroundColor(Color.parseColor("#b0e8a8"));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cartIDtv , totalOrderQuantity , totalOrderPrice;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cartIDtv=itemView.findViewById(R.id.card_customercart_cartidtv);
            totalOrderQuantity=itemView.findViewById(R.id.card_customercart_orderquantitytv);
            totalOrderPrice=itemView.findViewById(R.id.card_customercart_totalorderpricetv);
            cardView=itemView.findViewById(R.id.card_customercart_card);
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
}
