package com.example.mobile.data.remote

import com.example.mobile.domain.models.CreateStockRequest
import com.example.mobile.domain.models.StockDto
import com.example.mobile.domain.models.StockSummaryDto
import com.example.mobile.domain.models.UpdateStockRequest
import retrofit2.Response
import retrofit2.http.*

interface StockApiService {

    // Create stock (Add Inventory)
    @POST("/api/v1/inventory/stocks")
    suspend fun createStock(
        @Body request: CreateStockRequest
    ): Response<StockDto>

    // Get all stocks
    @GET("/api/v1/inventory/stocks")
    suspend fun getStock(): Response<List<StockDto>>

    // Get stock by ID
    @GET("/api/v1/inventory/stocks/{id}")
    suspend fun getStockById(
        @Path("id") id: String
    ): Response<StockDto>

    // Update stock (PATCH - partial update)
    @PATCH("/api/v1/inventory/stocks/{id}")
    suspend fun updateStock(
        @Path("id") id: String,
        @Body request: UpdateStockRequest
    ): Response<StockDto>

    // Delete stock
    @DELETE("/api/v1/inventory/stocks/{id}")
    suspend fun deleteStock(
        @Path("id") id: String
    ): Response<Unit>

    // Get stock summary
    @GET("/api/v1/inventory/stocks/summary")
    suspend fun getStockSummary(): Response<StockSummaryDto>

    // Get expiring stock
    @GET("/api/v1/inventory/stocks/expiring")
    suspend fun getExpiringStock(
        @Query("days") days: Int = 60
    ): Response<List<StockDto>>

}