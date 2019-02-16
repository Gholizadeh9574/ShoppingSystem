package amirhosssein.shoppingsystem.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import amirhosssein.shoppingsystem.R;
import amirhosssein.shoppingsystem.database.OrdersDB;
import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.models.OnItemClickListener;
import amirhosssein.shoppingsystem.models.Orders;
import amirhosssein.shoppingsystem.models.Ware;

public class CartForCusAdaptor extends RecyclerView.Adapter<CartForCusAdaptor.ViewHolder> {



    private List<Orders> items;
    private Context context;
    private Orders order=new Orders();
    String TAG="MyApp";
    private WareDB wareDB;
    private OrdersDB ordersDB;
    Ware ware=new Ware();

    public CartForCusAdaptor(List<Orders> items, Context context) {

        this.items =items;
        this.context = context;
        wareDB=new WareDB(context);
        ordersDB=new OrdersDB(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart_for_cus, parent, false);

        return new CartForCusAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        order=items.get(position);
        final int num=position;
        ware=wareDB.getWareByID(order.getWareID());
        int price=ware.getPrice();
        int quantity=order.getQuantity();
        int finalSum=price*quantity;
        holder.wareName.setText(ware.getName());
        holder.quantity.setText("تعداد : "+quantity);
        holder.price.setText("قیمت واحد : "+price);
        holder.sum.setText("قیمت کل : "+finalSum);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Orders deletedorder=items.get(position);
                ordersDB.delete(deletedorder.getID());
                items.remove(num);
                notifyItemRemoved(num);
                notifyItemRangeRemoved(num,items.size());
                Toast.makeText(context, "count : "+items.size(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "position : "+position, Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"order ID : "+order.getID(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView wareName, quantity, price, sum;
        ImageView remove;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            wareName = itemView.findViewById(R.id.cuscart_warename);
            quantity = itemView.findViewById(R.id.cuscart_quantity);
            price = itemView.findViewById(R.id.cuscart_price);
            sum = itemView.findViewById(R.id.cuscart_sum);
            remove = itemView.findViewById(R.id.cuscart_remove);
        }

        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                int adapterPosition = getAdapterPosition();
                mItemClickListener.onItemClick(v, adapterPosition, items.get(adapterPosition));

            }
        }


    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }
}