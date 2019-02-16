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

import java.util.List;

import amirhosssein.shoppingsystem.R;
import amirhosssein.shoppingsystem.models.Customer;
import amirhosssein.shoppingsystem.models.OnItemClickListener;

public class CustomerListAdaptor extends RecyclerView.Adapter<CustomerListAdaptor.ViewHolder> {

    private List<Customer> list;
    private Context context;

    public CustomerListAdaptor(List<Customer> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_customer_list,parent,false);
        return new CustomerListAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer customer=list.get(position);
        String accountName="نام کاربری : "+customer.getAccount_name();
        holder.customerAccountNametv.setText(accountName);
        String fullName="نام : "+customer.getName()+" "+customer.getLastname();
        holder.customerFullnametv.setText(fullName);
        String id=String.valueOf(customer.getID());
        String ID="شناسه کاربری : "+id;
        holder.customerIDtv.setText(ID);
        boolean isActive=customer.isIsactive();
        if (isActive)
            holder.cardView.setBackgroundColor(Color.parseColor("#b0e8a8"));
        else
            holder.cardView.setBackgroundColor(Color.parseColor("#a96060"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView customerAccountNametv,customerFullnametv,customerIDtv;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            customerAccountNametv=itemView.findViewById(R.id.card_customerlist_accountnametv);
            customerFullnametv=itemView.findViewById(R.id.card_customerlist_fullnametv);
            customerIDtv=itemView.findViewById(R.id.card_customerlist_IDtv);
            cardView=itemView.findViewById(R.id.card_customerlist_card);
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
