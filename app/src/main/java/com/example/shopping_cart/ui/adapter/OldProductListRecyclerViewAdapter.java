package com.example.shopping_cart.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.R;
import com.example.shopping_cart.core.entities.Product;

import java.util.ArrayList;

public class OldProductListRecyclerViewAdapter extends RecyclerView.Adapter<OldProductListRecyclerViewAdapter.myViewHolder> {
    private ArrayList<Product> myDataSet;
    public ArrayList<Product> checkedProducts = new ArrayList<>();
    Context context;

    public OldProductListRecyclerViewAdapter(Context cntx, ArrayList<Product> prdct) {
        context = cntx;
        myDataSet = prdct;
    }

    /**
     * Provides a reference to the type of views that are used
     */
    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView oldProductName, oldProductWeight, oldProductUnit;
        CheckBox productSelected;

        public myViewHolder(@NonNull View view) {
            super(view);
            // For every UI component a java object
            oldProductName = view.findViewById(R.id.old_product_name);
            oldProductWeight = view.findViewById(R.id.old_product_weight);
            oldProductUnit = view.findViewById(R.id.old_product_unit);
            productSelected = view.findViewById(R.id.select_old_product);
        }
    }

    // Creates new views invoked by the layout manager
    @NonNull
    @Override
    public OldProductListRecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Creates a new view which defines the UI of the old_product_list_item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.old_product_list_item, viewGroup, false);

        return new myViewHolder(view);
    }


    /**
     * Replaces the content of a view invoked by the layout manager
     * @param viewHolder individual element in the list defined by myViewHolder object
     * @param position gets data from the dataset to set the text for the view
     */
    @Override
    public void onBindViewHolder(final myViewHolder viewHolder, final int position) {
        viewHolder.oldProductName.setText(myDataSet.get(position).name);
        viewHolder.oldProductWeight.setText(myDataSet.get(position).weight);
        viewHolder.oldProductUnit.setText(myDataSet.get(position).unit);


        viewHolder.productSelected.setOnCheckedChangeListener(null);
        viewHolder.productSelected.setChecked(myDataSet.get(position).isChecked);
        viewHolder.productSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Called when the checked state of a compound button has changed
             * @param buttonView The compound button view whose state has changed
             * @param isChecked The new checked state of buttonView
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myDataSet.get(position).setChecked(isChecked);
                viewHolder.productSelected.setChecked(myDataSet.get(position).isChecked);
               if (isChecked) {
                    checkedProducts.add(myDataSet.get(position));
                } else if (checkedProducts.contains(myDataSet.get(position))) {
                    checkedProducts.remove(myDataSet.get(position));
                }
            }
        });
    }

    /**
     * @return size of the dataset invoked by the layout manager
     */
    @Override
    public int getItemCount() {
        return myDataSet.size();
    }

    public ArrayList<Product> getCheckedProducts() {
        return checkedProducts;
    }
}
