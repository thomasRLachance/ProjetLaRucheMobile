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

        productModels.add(new ProductModel(0, "Repas Chaud", 2.0));
        productModels.add(new ProductModel(1, "Sub'Vanier", 2.0));
        productModels.add(new ProductModel(2, "Jus de fruits", 0.5));
        productModels.add(new ProductModel(3, "Smoothie", 0.0));
        productModels.add(new ProductModel(4, "Gatorade", 1.0));
        productModels.add(new ProductModel(5, "Collation", 1.0));
        productModels.add(new ProductModel(6, "Fruit", 0.5));
        productModels.add(new ProductModel(7, "Lait au chocolat", 1.0));
        productModels.add(new ProductModel(8, "Jus de légumes", 0.5));
        productModels.add(new ProductModel(9, "Salade", 1.0));
        productModels.add(new ProductModel(10, "Yop", 1.0));
        productModels.add(new ProductModel(11, "Thé glacé", 1.0));
        productModels.add(new ProductModel(12, "Soupe", 0.0));

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