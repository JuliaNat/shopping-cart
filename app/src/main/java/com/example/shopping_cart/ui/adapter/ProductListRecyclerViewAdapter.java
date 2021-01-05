package com.example.shopping_cart.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.R;
import com.example.shopping_cart.core.entities.Product;

import java.util.ArrayList;

public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.myViewHolder> {
    private ArrayList<Product> myDataSet;
    Context context;

    OnCanClickListener onCanClickListener;

    public ProductListRecyclerViewAdapter(Context cntx, ArrayList<Product> prdct, OnCanClickListener onCanClickListener){
        context = cntx;
        myDataSet = prdct;
        this.onCanClickListener = onCanClickListener;
    }

    /**
     * Provides a reference to the type of views that are used
     */
    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView productName, productWeight, productUnit;
        ImageView deleteCan;
        OnCanClickListener canClickListener;

        public myViewHolder(@NonNull View view, OnCanClickListener onCanClickListener) {
            super(view);
            productName = view.findViewById(R.id.new_product_name);
            productWeight = view.findViewById(R.id.new_product_weight);
            productUnit = view.findViewById(R.id.new_product_unit);
            deleteCan = view.findViewById(R.id.deleteProductImage);

            canClickListener = onCanClickListener;
            deleteCan.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            canClickListener.onCanClick(getAdapterPosition());
        }
    }

    // Creates new views invoked by the layout manager
    @NonNull
    @Override
    public ProductListRecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Creates a new view which defines the UI of the product_list_item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_list_item, viewGroup, false);

        return new myViewHolder(view, onCanClickListener);
    }

    /**
     * Replaces the content of a view invoked by the layout manager
     * @param viewHolder individual element in the list defined by myViewHolder object
     * @param position gets data from the dataset to set the text for the view
     */
    @Override
    public void onBindViewHolder(myViewHolder viewHolder, final int position) {
        viewHolder.productName.setText(myDataSet.get(position).name);
        viewHolder.productWeight.setText(myDataSet.get(position).weight);
        viewHolder.productUnit.setText(myDataSet.get(position).unit);
    }

    /**
     * @return size of the dataset invoked by the layout manager
     */
    @Override
    public int getItemCount() {
        return myDataSet.size();
    }

    /**
     * Interface for deleting by clicking the can icon
     */
    public interface OnCanClickListener {
        void onCanClick(int position);
    }
}
