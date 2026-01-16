package com.example.mobile.presentation.product.types

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.common.Resource
import com.example.mobile.domain.models.CreateProductRequest
import com.example.mobile.domain.models.CreateProductTypeRequest
import com.example.mobile.domain.models.Product
import com.example.mobile.domain.models.ProductType
import com.example.mobile.domain.repository.ProductRepository
import com.example.mobile.presentation.product.ProductEvent
import com.example.mobile.presentation.product.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.mobile.domain.manager.CategoryManager

data class ProductTypeState(
    val productTypes: List<ProductType> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val filteredProductTypes: List<ProductType> = emptyList(),
    val showAddDialog: Boolean = false,
    val selectedProduct: ProductType? = null,
    val isCreating: Boolean = false,
    val isDeleting: Boolean = false,
    val successMessage: String? = null
)



@HiltViewModel
class ProductTypeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryManager: CategoryManager
) : ViewModel() {

    private val _state = MutableStateFlow(ProductTypeState())
    val state: StateFlow<ProductTypeState> = _state.asStateFlow()

    init {
        loadProductTypes()
    }

    fun onEvent(event: ProductTypeEvent) {
        when (event) {
            is ProductTypeEvent.LoadProductTypes -> {
                loadProductTypes()
            }

            is ProductTypeEvent.ShowAddDialog -> {
                _state.update { it.copy(showAddDialog = true, selectedProduct = null) }
            }

            is ProductTypeEvent.HideAddDialog -> {
                _state.update { it.copy(showAddDialog = false, selectedProduct = null) }
            }

            is ProductTypeEvent.CreateProductType -> {
                createProductType(event.description)
            }

            is ProductTypeEvent.RefreshProductTypes -> {
                loadProductTypes(isRefresh = true)
            }
            is ProductTypeEvent.ClearError -> {
                _state.update { it.copy(error = null) }
            }
            is ProductTypeEvent.ClearSuccess -> {
                _state.update { it.copy(successMessage = null) }
            }
        }
    }

    private fun loadProductTypes(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = !isRefresh,
                    isRefreshing = isRefresh,
                    error = null
                )
            }

            when (val result = productRepository.getProductTypes()) {
                is Resource.Success -> {
                    val productTypes = result.data ?: emptyList()
                    _state.update {
                        it.copy(
                            productTypes = productTypes,
                            filteredProductTypes = productTypes, // Sync filtered list
                            isLoading = false,
                            isRefreshing = false,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = result.message ?: "Erro ao carregar Categorias"
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun loadProductTypes() {
        viewModelScope.launch {
            when (val result = productRepository.getProductTypes()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            productTypes = result.data ?: emptyList(),
                            filteredProductTypes = result.data ?: emptyList() // Sync filtered list
                        )
                    }
                }

                is Resource.Error -> {
                    // Não mostra erro se tipos não carregarem
                    // O utilizador ainda pode usar a app
                }

                is Resource.Loading -> {
                    // Loading é tratado no estado principal
                }
            }
        }
    }

    private fun createProductType(description: String) {
        // Validação local
        if (description.isBlank()) {
            _state.update { it.copy(error = "Nome do produto é obrigatório") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isCreating = true, error = null) }

            val request = CreateProductTypeRequest(
                description = description.trim()
            )

            when (val result = productRepository.createProductType(request)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isCreating = false,
                            showAddDialog = false,
                            successMessage = "Categoria criada com sucesso"
                        )
                    }
                    // Recarregar lista
                    loadProductTypes()
                    // Refresh Singleton
                    categoryManager.refresh()
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isCreating = false,
                            error = result.message ?: "Erro ao criar Categoria"
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update { it.copy(isCreating = true) }
                }
            }
        }
    }
}

sealed class ProductTypeEvent {
    object RefreshProductTypes : ProductTypeEvent()
    object LoadProductTypes : ProductTypeEvent()
    object ShowAddDialog : ProductTypeEvent()
    object HideAddDialog : ProductTypeEvent()
    data class CreateProductType(val description: String) : ProductTypeEvent()

    object ClearError : ProductTypeEvent()
    object ClearSuccess : ProductTypeEvent()
}
