package com.example.mobile.presentation.product


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.common.Resource
import com.example.mobile.domain.models.Product
import com.example.mobile.domain.models.ProductType
import com.example.mobile.domain.models.CreateProductRequest
import com.example.mobile.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductState(
    val products: List<Product> = emptyList(),
    val productTypes: List<ProductType> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val showAddDialog: Boolean = false,
    val selectedProduct: Product? = null,
    val isCreating: Boolean = false,
    val isDeleting: Boolean = false,
    val successMessage: String? = null
)

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state.asStateFlow()

    init {
        loadProducts()
        loadProductTypes()
    }

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.SearchQueryChanged -> {
                _state.update { it.copy(searchQuery = event.query) }
                filterProducts(event.query)
            }
            is ProductEvent.LoadProducts -> {
                loadProducts()
            }
            is ProductEvent.RefreshProducts -> {
                loadProducts(isRefresh = true)
            }
            is ProductEvent.ShowAddDialog -> {
                _state.update { it.copy(showAddDialog = true, selectedProduct = null) }
            }
            is ProductEvent.HideAddDialog -> {
                _state.update { it.copy(showAddDialog = false, selectedProduct = null) }
            }
            is ProductEvent.CreateProduct -> {
                createProduct(event.name, event.description, event.typeId)
            }
            is ProductEvent.SelectProduct -> {
                _state.update { it.copy(selectedProduct = event.product) }
            }
            is ProductEvent.ClearError -> {
                _state.update { it.copy(error = null) }
            }
            is ProductEvent.ClearSuccess -> {
                _state.update { it.copy(successMessage = null) }
            }
        }
    }

    private fun loadProducts(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = !isRefresh,
                isRefreshing = isRefresh,
                error = null
            ) }

            when (val result = productRepository.getProducts()) {
                is Resource.Success -> {
                    val products = result.data ?: emptyList()
                    _state.update { it.copy(
                        products = products,
                        filteredProducts = products,
                        isLoading = false,
                        isRefreshing = false,
                        error = null
                    ) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = result.message ?: "Erro ao carregar produtos"
                    ) }
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
                    _state.update { it.copy(
                        productTypes = result.data ?: emptyList()
                    ) }
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

    private fun filterProducts(query: String) {
        val currentProducts = _state.value.products

        if (query.isBlank()) {
            _state.update { it.copy(filteredProducts = currentProducts) }
            return
        }

        val filtered = currentProducts.filter { product ->
            product.name.contains(query, ignoreCase = true) ||
                    product.description?.contains(query, ignoreCase = true) == true ||
                    product.typeDescription?.contains(query, ignoreCase = true) == true
        }

        _state.update { it.copy(filteredProducts = filtered) }
    }

    private fun createProduct(name: String, description: String, typeId: String) {
        // Validação local
        if (name.isBlank()) {
            _state.update { it.copy(error = "Nome do produto é obrigatório") }
            return
        }

        if (typeId.isBlank()) {
            _state.update { it.copy(error = "Tipo de produto é obrigatório") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isCreating = true, error = null) }

            val request = CreateProductRequest(
                name = name.trim(),
                typeId = typeId
            )

            when (val result = productRepository.createProduct(request)) {
                is Resource.Success -> {
                    _state.update { it.copy(
                        isCreating = false,
                        showAddDialog = false,
                        successMessage = "Produto criado com sucesso"
                    ) }
                    // Recarregar lista
                    loadProducts()
                }
                is Resource.Error -> {
                    _state.update { it.copy(
                        isCreating = false,
                        error = result.message ?: "Erro ao criar produto"
                    ) }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isCreating = true) }
                }
            }
        }
    }

//    private fun deleteProduct(productId: String) {
//        viewModelScope.launch {
//            _state.update { it.copy(isDeleting = true, error = null) }
//
//            when (val result = productRepository.deleteProduct(productId)) {
//                is Resource.Success -> {
//                    _state.update {
//                        it.copy(
//                            isDeleting = false,
//                            successMessage = "Produto eliminado com sucesso"
//                        )
//                    }
//                    // Recarregar lista
//                    loadProducts()
//                }
//
//                is Resource.Error -> {
//                    _state.update {
//                        it.copy(
//                            isDeleting = false,
//                            error = result.message ?: "Erro ao eliminar produto"
//                        )
//                    }
//                }
//
//                is Resource.Loading -> {
//                    _state.update { it.copy(isDeleting = true) }
//                }
//            }
//        }
//    }
}

sealed class ProductEvent {
    data class SearchQueryChanged(val query: String) : ProductEvent()
    object LoadProducts : ProductEvent()
    object RefreshProducts : ProductEvent()
    object ShowAddDialog : ProductEvent()
    object HideAddDialog : ProductEvent()
    data class CreateProduct(val name: String, val description: String, val typeId: String) : ProductEvent()
    //data class DeleteProduct(val productId: String) : ProductEvent()
    data class SelectProduct(val product: Product) : ProductEvent()
    object ClearError : ProductEvent()
    object ClearSuccess : ProductEvent()
}