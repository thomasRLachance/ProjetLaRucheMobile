package com.example.projetlaruchemobile.data;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.R;

import androidx.annotation.NonNull;

import com.example.projetlaruchemobile.data.model.ProductModel;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<ProductModel> implements View.OnClickListener {

    private ArrayList<ProductModel> dataSet;
    Context mContext;

    public ProductAdapter(ArrayList<ProductModel> data, Context context) {
        super(context, R.layout.product_list, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

    }
}
