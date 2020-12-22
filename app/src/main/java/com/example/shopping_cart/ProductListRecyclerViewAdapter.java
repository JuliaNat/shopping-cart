package com.example.shopping_cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.myViewHolder> {
    private ArrayList<Product> myDataSet;
    Context context;
    OnCanClickListener myClickListener;

    public ProductListRecyclerViewAdapter(Context cntx, ArrayList<Product> prdct, OnCanClickListener listener){
        context = cntx;
        myDataSet = prdct;
        myClickListener = listener;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView productName, productWeight, productNutrition;
        ImageView deleteCan;
        OnCanClickListener clickListener;

        public myViewHolder(@NonNull View view, OnCanClickListener listener) {
            super(view);
            productName = view.findViewById(R.id.new_product_name);
            productWeight = view.findViewById(R.id.new_product_weight);
            productNutrition = view.findViewById(R.id.new_product_nutrition);
            deleteCan = view.findViewById(R.id.deleteProductImage);

            clickListener = listener;
            deleteCan.setOnClickListener(this);
        }

        public TextView getTextView() {
            return productName;
        }

        @Override
        public void onClick(View v) {
            clickListener.onCanClick(getAdapterPosition());
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ProductListRecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // baut custom layout shopping_cart_list_item.xml und bau damit die view zusammen
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_list_item, viewGroup, false);

        return new myViewHolder(view, myClickListener);
    }

    @Override
    public void onBindViewHolder(myViewHolder viewHolder, final int position) {
        viewHolder.productName.setText(myDataSet.get(position).name);
        viewHolder.productWeight.setText(myDataSet.get(position).weight);
        viewHolder.productNutrition.setText(myDataSet.get(position).nutrition);
    }

    @Override
    public int getItemCount() {
        return myDataSet.size();
    }

    public interface OnCanClickListener {
        void onCanClick(int position);
    }
}
