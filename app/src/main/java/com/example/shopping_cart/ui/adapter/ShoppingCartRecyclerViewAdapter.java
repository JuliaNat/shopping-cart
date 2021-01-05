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
import com.example.shopping_cart.core.entities.Cart;

import java.util.ArrayList;

public class ShoppingCartRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerViewAdapter.myViewHolder> {
    private ArrayList<Cart> myDataSet;
    Context context;

    OnCartClickListener onCartClickListener;
    OnCanClickListener onCanClickListener;

    public ShoppingCartRecyclerViewAdapter(Context cntx, ArrayList<Cart> crt, OnCartClickListener onCartListener, OnCanClickListener onCanListener){
        context = cntx;
        myDataSet = crt;
        onCartClickListener = onCartListener;
        onCanClickListener = onCanListener;
    }

    /**
     * Provides a reference to the type of views that are used
     */
    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cartName;
        ImageView deleteCan;
        OnCartClickListener cartClickListener;
        OnCanClickListener canClickListener;

        public myViewHolder(@NonNull View view, OnCartClickListener onCartClickListener, OnCanClickListener onCanClickListener) {
            super(view);
            cartName = view.findViewById(R.id.shopping_cart_name);
            deleteCan = view.findViewById(R.id.deleteCart);

            cartClickListener = onCartClickListener;
            canClickListener = onCanClickListener;
            deleteCan.setOnClickListener(this);
            cartName.setOnClickListener(this);
        }

        /** OnClick function with distinction of what was clicked on
         * @param v current view clicked on
         */
        @Override
        public void onClick(View v) {
            if(v == cartName) {
                cartClickListener.onCartClick(getAdapterPosition());
            } else if (v == deleteCan) {
                canClickListener.onCanClick(getAdapterPosition());
            }
        }
    }

    // Creates new views invoked by the layout manager
    @NonNull
    @Override
    public ShoppingCartRecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // baut custom layout shopping_cart_list_item.xml und bau damit die view zusammen
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shopping_cart_list_item, viewGroup, false);

        return new myViewHolder(view, onCartClickListener, onCanClickListener);
    }

    /**
     * Replaces the content of a view invoked by the layout manager
     * @param viewHolder individual element in the list defined by myViewHolder object
     * @param position gets data from the dataset to set the text for the view
     */
    @Override
    public void onBindViewHolder(myViewHolder viewHolder, final int position) {
        viewHolder.cartName.setText(myDataSet.get(position).name);
    }

    /**
     * @return size of the dataset invoked by the layout manager
     */
    @Override
    public int getItemCount() {
        return myDataSet.size();
    }

    /**
     * Interface for clicking the cart name
     */
    public interface OnCartClickListener {
        void onCartClick(int position);
    }

    /**
     * Interface for deleting by clicking the can icon
     */
    public interface OnCanClickListener {
        void onCanClick(int position);
    }

}
