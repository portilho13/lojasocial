package com.example.mobile.data.repository

import com.example.mobile.common.Resource
import com.example.mobile.data.remote.StockApiService
import com.example.mobile.domain.models.CreateStockRequest
import com.example.mobile.domain.models.StockDto
import com.example.mobile.domain.models.StockSummaryDto
import com.example.mobile.domain.models.UpdateStockRequest
import com.example.mobile.domain.repository.StockRepository
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val apiService: StockApiService
) : StockRepository {

    override suspend fun createStock(request: CreateStockRequest): Resource<StockDto> {
        return try {
            val response = apiService.createStock(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun getAllStock(): Resource<List<StockDto>> {
        return try {
            val response = apiService.getStock()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun getStockById(id: String): Resource<StockDto> {
        return try {
            val response = apiService.getStockById(id)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun updateStock(id: String, request: UpdateStockRequest): Resource<StockDto> {
        return try {
            val response = apiService.updateStock(id, request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun deleteStock(id: String): Resource<Unit> {
        return try {
            val response = apiService.deleteStock(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun getStockSummary(): Resource<StockSummaryDto> {
        return try {
            val response = apiService.getStockSummary()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun getExpiringStock(days: Int): Resource<List<StockDto>> {
        return try {
            val response = apiService.getExpiringStock(days)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}