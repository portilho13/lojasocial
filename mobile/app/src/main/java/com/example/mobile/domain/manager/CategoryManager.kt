package com.example.mobile.domain.manager

import com.example.mobile.common.Resource
import com.example.mobile.domain.models.ProductType
import com.example.mobile.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryManager @Inject constructor(
    private val repository: ProductRepository
) {
    private val _categories = MutableStateFlow<List<ProductType>>(emptyList())
    val categories: StateFlow<List<ProductType>> = _categories.asStateFlow()

    private var isLoaded = false

    suspend fun loadCategories(forceRefresh: Boolean = false) {
        if (isLoaded && !forceRefresh) return

        when (val result = repository.getProductTypes()) {
            is Resource.Success -> {
                _categories.value = result.data ?: emptyList()
                isLoaded = true
            }
            is Resource.Error -> {
                // Determine logic for error, maybe keep old data or clear
                // For now, logging or silent failure as existing UI handles its own flows often
            }
            is Resource.Loading -> {}
        }
    }

    suspend fun refresh() {
        loadCategories(forceRefresh = true)
    }

    fun getCategoryById(id: String): ProductType? {
        return _categories.value.find { it.id == id }
    }
}
