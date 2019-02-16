package amirhosssein.shoppingsystem.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import amirhosssein.shoppingsystem.R;
import amirhosssein.shoppingsystem.models.OnItemClickListener;
import amirhosssein.shoppingsystem.models.Ware;

public class WareInfoAdaptor extends RecyclerView.Adapter<WareInfoAdaptor.ViewHolder> {

    Context context;
    private ArrayList<Ware> list;

    public WareInfoAdaptor(Context context, ArrayList<Ware> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ware_info,parent,false);
        return new WareInfoAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Ware ware=list.get(position);
        holder.wareName.setText(ware.getName());
        holder.warePrice.setText("قیمت : "+ware.getPrice()+" تومان");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView wareName , warePrice;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            wareName=itemView.findViewById(R.id.card_wareinfo_warenametv);
            warePrice=itemView.findViewById(R.id.card_wareinfo_warepricetv);
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
