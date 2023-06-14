package com.example.projetlaruchemobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetlaruchemobile.data.model.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SalesActivity extends AppCompatActivity {

    private ArrayList<ProductModel> productModels;
    private ListView productListView;
    private TextView totalTextView;
    private TextView lastSavedTimeTextView;
    private Button saveButton;

    private static ProductAdapter adapter;

    private ApiService apiService;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        // Initialize Retrofit and ApiService
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Initialize SharedPreferences
        sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        // Retrieve token from local storage
        String token = sharedPref.getString("token", "");
        int locationId = sharedPref.getInt("locationId", 0);


        productListView = findViewById(R.id.productListView);
        totalTextView = findViewById(R.id.totalTextView);
        lastSavedTimeTextView = findViewById(R.id.lastSavedTimeTextView);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            String timeString = String.format("%02d:%02d", hour, minute);

            lastSavedTimeTextView.setText(timeString);

            for(ProductModel productModel : productModels) {
                SalesRequest request = new SalesRequest(productModel.getId(), productModel.getQuantity());

                Call<Void> call = apiService.updateSales(request, "Bearer " + token);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Handle successful sales update
                            System.out.println(response);
                        } else {
                            // Handle sales update failure
                            System.out.println("fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Handle network or API call failure
                        System.out.println("fail");
                    }
                });
            }
        });

        productModels = new ArrayList<>();

        final SalesActivity activity = this;

        Call<List<ProductLocation>> call = apiService.getProductLocations(locationId);
        call.enqueue(new Callback<List<ProductLocation>>() {
            @Override
            public void onResponse(Call<List<ProductLocation>> call, Response<List<ProductLocation>> response) {
                if (response.isSuccessful()) {
                    List<ProductLocation> productLocations = response.body();

                    for(ProductLocation productLocation : productLocations) {
                        ProductModel productModel = new ProductModel(productLocation.getProductLocationId(), productLocation.getProduct().getName(), productLocation.getPrice().doubleValue());
                        productModels.add(productModel);
                    }

                    adapter = new ProductAdapter(productModels, getApplicationContext(), activity);

                    productListView.setAdapter(adapter);

                } else {
                    // Handle unsuccessful response
                    System.out.println("fail");
                }
            }

            @Override
            public void onFailure(Call<List<ProductLocation>> call, Throwable t) {
                // Handle API call failure
                System.out.println("fail");
            }
        });

        /*
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
         */

        //adapter = new ProductAdapter(productModels, getApplicationContext(), this);

        //productListView.setAdapter(adapter);
    }

    public void updateTotalPrice() {
        double total = 0;
        for (ProductModel product : productModels) {
            total += product.getPrice() * product.getQuantity();
        }
        totalTextView.setText(NumberFormat.getCurrencyInstance().format(total));
    }
}