package com.example.mobile.domain.repository

import com.example.mobile.common.Resource
import com.example.mobile.domain.models.CreateStockRequest
import com.example.mobile.domain.models.StockDto
import com.example.mobile.domain.models.StockSummaryDto
import com.example.mobile.domain.models.UpdateStockRequest

interface StockRepository {
    suspend fun createStock(request: CreateStockRequest): Resource<StockDto>
    suspend fun getAllStock(): Resource<List<StockDto>>
    suspend fun getStockById(id: String): Resource<StockDto>
    suspend fun updateStock(id: String, request: UpdateStockRequest): Resource<StockDto>
    suspend fun deleteStock(id: String): Resource<Unit>
    suspend fun getStockSummary(): Resource<StockSummaryDto>
    suspend fun getExpiringStock(days: Int): Resource<List<StockDto>>
}