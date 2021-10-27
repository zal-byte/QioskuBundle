package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.zadev.qiosku.Helper.Snacc;
import com.zadev.qiosku.R;
import com.zadev.qiosku.ViewModel.MProduct;
import com.zadev.qiosku.ViewProductActivity;

import java.util.ArrayList;

public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.ViewHolder> {
    private Context activity;
    ArrayList<MProduct> data;

    public MainProductAdapter(Context activity, ArrayList<MProduct> data) {
        this.activity = activity;
        this.data = data;

    }

    @NonNull
    @Override
    public MainProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.mainproductadapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainProductAdapter.ViewHolder holder, int position) {


        holder.p_price.setText(data.get(position).getP_price());
        holder.p_name.setText(data.get(position).getP_name());

        holder.product_card.setOnClickListener(view -> {
            Intent intent = new Intent(this.activity, ViewProductActivity.class);
            intent.putExtra("p_id", data.get(position).getP_id());
            intent.putExtra("p_name", data.get(position).getP_name());
            intent.putExtra("p_description", data.get(position).getP_description());
            intent.putExtra("p_price", data.get(position).getP_price());
            intent.putExtra("p_quantity", data.get(position).getP_quantity());
            intent.putExtra("p_image", data.get(position).getP_image());

            this.activity.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView product_card;
        ImageView p_image;
        MaterialTextView p_price, p_quantity, p_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_card = (MaterialCardView) itemView.findViewById(R.id.product_card);
            p_price = (MaterialTextView) itemView.findViewById(R.id.p_price);
            p_quantity = (MaterialTextView) itemView.findViewById(R.id.p_quantity);

            p_name = (MaterialTextView) itemView.findViewById(R.id.p_name);

            p_image = (ImageView) itemView.findViewById(R.id.p_image);

        }
    }
}
