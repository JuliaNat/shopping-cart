package com.example.shopping_cart.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.R;
import com.example.shopping_cart.core.entities.Product;

import java.util.ArrayList;

public class OldProductListRecyclerViewAdapter extends RecyclerView.Adapter<OldProductListRecyclerViewAdapter.myViewHolder> {
    private ArrayList<Product> myDataSet;
    Context context;

    public OldProductListRecyclerViewAdapter(Context cntx, ArrayList<Product> prdct) {
        context = cntx;
        myDataSet = prdct;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView oldProductName;
        CheckBox productSelected;

        public myViewHolder(View view) {
            super(view);
            oldProductName = view.findViewById(R.id.old_product_name);
            productSelected = view.findViewById(R.id.select_old_product);
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.old_product_list_item, viewGroup, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder viewHolder, final int position) {
        String oldProductName = new StringBuilder()
                .append(myDataSet.get(position).name)
                .append(" ")
                .append(myDataSet.get(position).weight)
                .append(" ")
                .append(myDataSet.get(position).nutrition)
                .toString();

        viewHolder.oldProductName.setText(oldProductName);
    }

    @Override
    public int getItemCount() {
        return myDataSet.size();
    }
}
