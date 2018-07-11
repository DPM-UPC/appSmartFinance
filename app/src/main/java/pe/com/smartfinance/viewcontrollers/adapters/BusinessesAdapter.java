package pe.com.smartfinance.viewcontrollers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class BusinessesAdapter extends RecyclerView.Adapter<BusinessesAdapter.ViewHolder> {

    List<Business> businesses;

    public BusinessesAdapter(List<Business> businesses) {
        this.businesses = businesses;
    }

    @NonNull
    @Override
    public BusinessesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_business, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull BusinessesAdapter.ViewHolder holder, final int position) {
        Business business = businesses.get(position);
        holder.updateViewFrom(business);
    }
    @Override
    public int getItemCount() {
        return businesses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView businessCardView;
        TextView nameTextView;
        ImageView pictureImageView;
        Business business;

        public ViewHolder(View itemView) {
            super(itemView);
            businessCardView = (CardView) itemView.findViewById(R.id.businessCardView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            pictureImageView = (ImageView) itemView.findViewById(R.id.pictureImageView);
        }

        public void updateViewFrom(final Business business){
            pictureImageView.setImageResource(business.getPictureId());
            nameTextView.setText(business.getName());
            this.business = business;
            businessCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent mainIntent = new Intent(context, MainActivity.class);
                    mainIntent.putExtras(business.toBundle());
                    context.startActivity(mainIntent);
                }
            });
        }
    }
}
