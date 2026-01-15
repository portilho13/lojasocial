package com.example.mobile.data.remote

import com.example.mobile.domain.models.CreateProductRequest
import com.example.mobile.domain.models.CreateProductTypeRequest
import com.example.mobile.domain.models.Product
import com.example.mobile.domain.models.ProductType
import com.example.mobile.domain.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ProductApiService {

    @POST("/api/v1/inventory/types")
    suspend fun createProductType(
        @Body request: CreateProductTypeRequest
    ): Response<ProductType>

    @GET("/api/v1/inventory/types")
    suspend fun getProductTypes(): Response<List<ProductType>>

    @POST("/api/v1/inventory/products")
    suspend fun createProduct(
        @Body request: CreateProductRequest
    ): Response<Product>

    @GET("/api/v1/inventory/products")
    suspend fun getProducts(): Response<List<Product>>

//    @POST("/api/v1/inventory/stocks")
//    suspend fun createStock(
//        @Body request: Map<String, String>
//    ): Response<Unit>
//
//    @GET("/api/v1/inventory/stocks")
//    suspend fun getAllStocks(
//        @Body request: Map<String, String> // {"refreshToken": "..."}
//    ): Response<Unit>
//
//    @PATCH("/api/v1/inventory/stocks/:id")
//    suspend fun updateStock(
//        @Body request: RegisterRequest
//    ): Response<Unit>
//
//    @DELETE("/api/v1/inventory/stocks/:id")
//    suspend fun deleteStock(
//        @Body request: RegisterRequest
//    ): Response<Unit>
//
//    @GET("/api/v1/inventory/stocks/expiring?days=:days")
//    suspend fun getExpiringStocks(
//        @Body request: Map<String, String> // {"refreshToken": "..."}
//    ): Response<Unit>
//
//    @GET("/api/v1/inventory/stocks/summary")
//    suspend fun getStockSummary(
//        @Body request: Map<String, String> // {"refreshToken": "..."}
//    ): Response<Unit>
}