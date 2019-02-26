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
import amirhosssein.shoppingsystem.models.Admin;
import amirhosssein.shoppingsystem.models.OnItemClickListener;

public class AdminListAdaptor extends RecyclerView.Adapter<AdminListAdaptor.ViewHolder> {


    ArrayList<Admin> list;
    Context context;

    public AdminListAdaptor(Context context, ArrayList<Admin> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_adminlist, parent, false);
        return new AdminListAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Admin admin = list.get(position);

        String strgFullName = admin.getName() + " " + admin.getLastname();
        holder.adminFullNametv.setText(strgFullName);

        String strgID = "شناسه کاربری : " + admin.getID();
        holder.adminIDtv.setText(strgID);

        if (admin.isAcvive())
            holder.cardView.setBackgroundColor(Color.parseColor("#b0e8a8"));
        else
            holder.cardView.setBackgroundColor(Color.parseColor("#a96060"));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView adminFullNametv, adminIDtv;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            adminFullNametv = itemView.findViewById(R.id.card_adminlist_fullnametv);
            adminIDtv = itemView.findViewById(R.id.card_adminlist_adminidtv);
            cardView = itemView.findViewById(R.id.card_adminlist_card);

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
