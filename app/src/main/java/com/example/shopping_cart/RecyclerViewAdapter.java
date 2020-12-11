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

    public RecyclerViewAdapter (Context cntx, ArrayList<Cart> crt){
        context = cntx;
        myDataSet = crt;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView cartName;

        public myViewHolder(@NonNull View view) {
            super(view);
            cartName = (TextView) view.findViewById(R.id.shopping_cart_name);

            // onListClickListener
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Custom Adapter", "Shopping Cart " + getAdapterPosition() + "clicked!!");
                }
            });
        }

        public TextView getTextView() {
            return cartName;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // baut custom layout shopping_cart_list_item.xml und bau damit die view zusammen
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shopping_cart_list_item, viewGroup, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder viewHolder, final int position) {
        viewHolder.cartName.setText(myDataSet.get(position).name);
    }

    @Override
    public int getItemCount() {
        return myDataSet.size();
    }

}
