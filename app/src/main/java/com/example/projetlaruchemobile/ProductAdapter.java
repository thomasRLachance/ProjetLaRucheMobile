package com.example.projetlaruchemobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetlaruchemobile.data.model.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<ProductModel> {

    private ArrayList<ProductModel> dataSet;
    Context mContext;
    private SalesActivity activity;

    public ProductAdapter(ArrayList<ProductModel> data, Context context, SalesActivity activity) {
        super(context, R.layout.product_list, data);
        this.dataSet = data;
        this.mContext = context;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_list, parent, false);
        }

        ProductModel product = getItem(position);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView countTextView = convertView.findViewById(R.id.countTextView);
        TextView priceTextView = convertView.findViewById(R.id.priceTextView);

        Button addButton = convertView.findViewById(R.id.addButton);
        Button minButton = convertView.findViewById(R.id.minButton);

        addButton.setOnClickListener(v -> {
            product.incrementQuantity();
            notifyDataSetChanged();
        });

        minButton.setOnClickListener(v -> {
            if(product.getQuantity() > 0) {
                product.reduceQuantity();
                notifyDataSetChanged();
            }
        });

        nameTextView.setText(product.getName());
        countTextView.setText(String.valueOf(product.getQuantity()));
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(product.getPrice() * product.getQuantity()));
        activity.updateTotalPrice();

        return convertView;
    }
}
