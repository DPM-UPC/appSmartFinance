package pe.com.smartfinance.viewcontrollers.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.Business;
import pe.com.smartfinance.viewcontrollers.activities.MainActivity;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {

    List<Business> business;

    public void setBusiness(List<Business> business){
        this.business = business;
    }

    @Override
    public BusinessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_business, parent, false));
    }
    @Override
    public void onBindViewHolder(final BusinessAdapter.ViewHolder holder, final int position) {
        holder.nameTextView.setText(business.get(position).getName());
        holder.pictureImageView.setImageResource(business.get(position).getPictureid());
        holder.businessCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(v.getContext(), MainActivity.class);
                mainIntent.putExtra("titleBusiness", business.get(position).getName());
                v.getContext().startActivity(mainIntent);
            }
        });
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
