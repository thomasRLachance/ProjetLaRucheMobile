package com.example.projetlaruchemobile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/login")
    Call<TokenResponse> login(@Body LoginRequest request);

    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username, @Header("Authorization") String token);

    @PUT("/sales")
    Call<Void> updateSales(@Body SalesRequest request, @Header("Authorization") String token);

    @GET("/locations/{locationId}/productLocations")
    Call<List<ProductLocation>> getProductLocations(
            @Path("locationId") int locationId
    );
}
