package amirhosssein.shoppingsystem.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import amirhosssein.shoppingsystem.R;
import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.models.Orders;

public class CartDetailsAdaptor extends RecyclerView.Adapter<CartDetailsAdaptor.ViewHolder>{

    private List<Orders> list;
    private Context context;
    WareDB wareDB;


    public CartDetailsAdaptor(List<Orders> list, Context context) {
        this.list = list;
        this.context = context;
        wareDB=new WareDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart_details, parent, false);

        return new CartDetailsAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Orders order=list.get(position);
        String wareName=wareDB.getWareNameByWareID(order.getWareID());
        int totalPrice=wareDB.getTotalPriceByOrderID(order.getID());
        holder.wareName.setText(wareName+"");
        holder.wareQuantity.setText("تعداد : "+order.getQuantity());
        holder.wareTotalPrice.setText("مبلغ نهایی : "+totalPrice);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView wareName , wareQuantity , wareTotalPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            wareName=itemView.findViewById(R.id.card_cartdetails_warenametv);
            wareQuantity=itemView.findViewById(R.id.card_cartdetails_wareorderquantitytv);
            wareTotalPrice=itemView.findViewById(R.id.card_cartdetails_wareordertotalpricetv);
        }
    }
}