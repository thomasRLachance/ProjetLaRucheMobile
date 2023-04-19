package com.example.projetlaruchemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projetlaruchemobile.data.model.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SalesActivity extends AppCompatActivity {

    private ArrayList<ProductModel> productModels;
    private ListView productListView;
    private TextView totalTextView;

    private static ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        productListView = (ListView) findViewById(R.id.productListView);
        totalTextView = findViewById(R.id.totalTextView);

        productModels = new ArrayList<>();

        productModels.add(new ProductModel(0, "item0", 1.2));
        productModels.add(new ProductModel(1, "item1", 2.1));
        productModels.add(new ProductModel(2, "item2", 0.7));
        productModels.add(new ProductModel(3, "item3", 1.4));

        adapter = new ProductAdapter(productModels, getApplicationContext(), this);

        productListView.setAdapter(adapter);
    }

    public void updateTotalPrice() {
        double total = 0;
        for (ProductModel product : productModels) {
            total += product.getPrice() * product.getQuantity();
        }
        totalTextView.setText(NumberFormat.getCurrencyInstance().format(total));
    }
}