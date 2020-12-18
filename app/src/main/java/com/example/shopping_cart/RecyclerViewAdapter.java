package com.example.shopping_cart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {
    private ArrayList<Cart> myDataSet;
    Context context;
    OnCartClickListener myClickListener;

    public RecyclerViewAdapter (Context cntx, ArrayList<Cart> crt, OnCartClickListener listener){
        context = cntx;
        myDataSet = crt;
        myClickListener = listener;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cartName;
        OnCartClickListener clickListener;

        public myViewHolder(@NonNull View view, OnCartClickListener listener) {
            super(view);
            cartName = (TextView) view.findViewById(R.id.shopping_cart_name);
            clickListener = listener;
            view.setOnClickListener(this);
        }

        public TextView getTextView() {
            return cartName;
        }

        @Override
        public void onClick(View v) {
            clickListener.onCartClick(getAdapterPosition());
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // baut custom layout shopping_cart_list_item.xml und bau damit die view zusammen
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shopping_cart_list_item, viewGroup, false);

        return new myViewHolder(view, myClickListener);
    }

    @Override
    public void onBindViewHolder(myViewHolder viewHolder, final int position) {
        viewHolder.cartName.setText(myDataSet.get(position).name);
    }

    @Override
    public int getItemCount() {
        return myDataSet.size();
    }


    public interface OnCartClickListener {
        void onCartClick(int position);
    }

}
