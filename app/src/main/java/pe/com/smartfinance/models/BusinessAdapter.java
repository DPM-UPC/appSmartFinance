package pe.com.smartfinance.models;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.com.smartfinance.R;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {

    List<Business> business;

    public void setBusiness(List<Business> business){
        this.business = business;
    }

    @Override
    public BusinessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.card_business, parent, false));
    }
    @Override
    public void onBindViewHolder(BusinessAdapter.ViewHolder holder, int position) {
        holder.nameTextView.setText(business.get(position).getName());
        holder.pictureImageView.setImageResource(business.get(position).getPictureid());
    }
    @Override
    public int getItemCount() {
        return business.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView businessCardView;
        TextView nameTextView;
        ImageView pictureImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            businessCardView = (CardView) itemView.findViewById(R.id.businessCardView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            pictureImageView = (ImageView) itemView.findViewById(R.id.pictureImageView);
        }
    }
}
