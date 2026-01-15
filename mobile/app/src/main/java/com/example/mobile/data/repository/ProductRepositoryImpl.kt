package com.example.mobile.data.repository


import com.example.mobile.data.remote.ProductApiService
import com.example.mobile.domain.models.CreateProductRequest
import com.example.mobile.domain.models.Product
import com.example.mobile.domain.models.ProductType
//import com.example.mobile.domain.models.UpdateProductRequest
import com.example.mobile.domain.repository.ProductRepository
import com.example.mobile.common.Resource
import com.example.mobile.domain.models.CreateProductTypeRequest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ProductApiService
) : ProductRepository {

    override suspend fun getProducts(): Resource<List<Product>> {
        return try {
            val response = apiService.getProducts()

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(
                    message = when (response.code()) {
                        401 -> "Não autorizado. Faça login novamente"
                        403 -> "Sem permissão para visualizar produtos"
                        404 -> "Produtos não encontrados"
                        else -> "Erro ao carregar produtos: ${response.message()}"
                    }
                )
            }
        } catch (e: HttpException) {
            Resource.Error(
                message = when (e.code()) {
                    401 -> "Sessão expirada. Faça login novamente"
                    403 -> "Sem permissão para visualizar produtos"
                    500 -> "Erro no servidor. Tente novamente mais tarde"
                    else -> "Erro de conexão: ${e.message()}"
                }
            )
        } catch (e: IOException) {
            Resource.Error(message = "Sem conexão à internet. Verifique a sua rede")
        } catch (e: Exception) {
            Resource.Error(message = "Erro inesperado: ${e.localizedMessage}")
        }
    }



//    override suspend fun getProductById(id: String): Resource<Product> {
//        return try {
//            val response = apiService.getProductById(id)
//
//            if (response.isSuccessful && response.body() != null) {
//                Resource.Success(response.body()!!)
//            } else {
//                Resource.Error(message = "Produto não encontrado")
//            }
//        } catch (e: Exception) {
//            Resource.Error(message = "Erro ao carregar produto: ${e.localizedMessage}")
//        }
//    }

    override suspend fun getProductTypes(): Resource<List<ProductType>> {
        return try {
            val response = apiService.getProductTypes()

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(message = "Tipos de produto não encontrados")
            }
        } catch (e: Exception) {
            Resource.Error(message = "Erro ao carregar tipos: ${e.localizedMessage}")
        }
    }

    override suspend fun createProduct(request: CreateProductRequest): Resource<Product> {
        return try {
            val response = apiService.createProduct(request)

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(
                    message = when (response.code()) {
                        400 -> "Dados inválidos. Verifique as informações"
                        409 -> "Produto já existe"
                        422 -> "Dados fornecidos não são válidos"
                        else -> "Erro ao criar produto: ${response.message()}"
                    }
                )
            }
        } catch (e: HttpException) {
            Resource.Error(
                message = when (e.code()) {
                    400 -> "Dados inválidos"
                    409 -> "Produto já existe"
                    500 -> "Erro no servidor"
                    else -> "Erro de conexão: ${e.message()}"
                }
            )
        } catch (e: IOException) {
            Resource.Error(message = "Sem conexão à internet")
        } catch (e: Exception) {
            Resource.Error(message = "Erro inesperado: ${e.localizedMessage}")
        }
    }

    override suspend fun createProductType(request: CreateProductTypeRequest): Resource<ProductType> {
        return try {
            val response = apiService.createProductType(request)

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(
                    message = when (response.code()) {
                        400 -> "Dados inválidos. Verifique as informações"
                        409 -> "Categoria já existe"
                        422 -> "Dados fornecidos não são válidos"
                        else -> "Erro ao criar categoria: ${response.message()}"
                    }
                )
            }
        } catch (e: HttpException) {
            Resource.Error(
                message = when (e.code()) {
                    400 -> "Dados inválidos"
                    409 -> "Categoria já existe"
                    500 -> "Erro no servidor"
                    else -> "Erro de conexão: ${e.message()}"
                }
            )
        } catch (e: IOException) {
            Resource.Error(message = "Sem conexão à internet")
        } catch (e: Exception) {
            Resource.Error(message = "Erro inesperado: ${e.localizedMessage}")
        }
    }

//    override suspend fun updateProduct(id: String, request: UpdateProductRequest): Resource<Product> {
//        return try {
//            val response = apiService.updateProduct(id, request)
//
//            if (response.isSuccessful && response.body() != null) {
//                Resource.Success(response.body()!!)
//            } else {
//                Resource.Error(message = "Erro ao atualizar produto")
//            }
//        } catch (e: Exception) {
//            Resource.Error(message = "Erro ao atualizar produto: ${e.localizedMessage}")
//        }
//    }
//
//    override suspend fun deleteProduct(id: String): Resource<Unit> {
//        return try {
//            val response = apiService.deleteProduct(id)
//
//            if (response.isSuccessful) {
//                Resource.Success(Unit)
//            } else {
//                Resource.Error(
//                    message = when (response.code()) {
//                        404 -> "Produto não encontrado"
//                        403 -> "Sem permissão para eliminar produto"
//                        409 -> "Produto em uso. Não pode ser eliminado"
//                        else -> "Erro ao eliminar produto"
//                    }
//                )
//            }
//        } catch (e: Exception) {
//            Resource.Error(message = "Erro ao eliminar produto: ${e.localizedMessage}")
//        }
//    }
}