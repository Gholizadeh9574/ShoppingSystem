package amirhosssein.shoppingsystem.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import amirhosssein.shoppingsystem.R;
import amirhosssein.shoppingsystem.models.OnItemClickListener;
import amirhosssein.shoppingsystem.models.Ware;

public class CusWareRecyclerAdaptor extends RecyclerView.Adapter<CusWareRecyclerAdaptor.ViewHolder> {

    private List<Ware> items;
    private Context context;

    public CusWareRecyclerAdaptor(List<Ware> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ware_item_for_cus, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Ware ware = items.get(position);
        holder.warenametv.setText(ware.getName());
        holder.warepricetv.setText(String.valueOf(ware.getPrice()));
        if (ware.getStock() > 0) {
            holder.warestocktv.setText(R.string.thereis);
        } else {
            holder.warestocktv.setText(R.string.thereisnot);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView warenametv, warepricetv, warestocktv;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            warenametv = itemView.findViewById(R.id.cardwarenametv);
            warepricetv = itemView.findViewById(R.id.cardwarepricetv);
            warestocktv = itemView.findViewById(R.id.cardwarestocktv);
            cardView = itemView.findViewById(R.id.wareforcuscard);

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