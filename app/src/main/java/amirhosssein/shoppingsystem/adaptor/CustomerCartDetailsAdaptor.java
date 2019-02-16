package amirhosssein.shoppingsystem.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import amirhosssein.shoppingsystem.R;
import amirhosssein.shoppingsystem.database.WareDB;
import amirhosssein.shoppingsystem.models.Orders;

public class CustomerCartDetailsAdaptor extends RecyclerView.Adapter<CustomerCartDetailsAdaptor.ViewHolder> {

    Context context;
    ArrayList<Orders> list;
    WareDB wareDB;

    public CustomerCartDetailsAdaptor(Context context, ArrayList<Orders> list) {
        this.context = context;
        this.list = list;
        wareDB=new WareDB(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.card_customercartdetails,parent,false);

        return new CustomerCartDetailsAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Orders order=list.get(position);

        String wareName=wareDB.getWareNameByWareID(order.getWareID());
        holder.wareNametv.setText(wareName);

        holder.warequantitytv.setText("تعداد : "+order.getQuantity());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView wareNametv,warequantitytv;

        public ViewHolder(View itemView) {
            super(itemView);
            wareNametv=itemView.findViewById(R.id.card_customercartdetails_warenametv);
            warequantitytv=itemView.findViewById(R.id.card_customercartdetails_warequantitytv);
        }
    }
}
