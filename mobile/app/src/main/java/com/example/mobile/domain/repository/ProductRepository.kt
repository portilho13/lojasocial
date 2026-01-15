package com.example.mobile.domain.repository

import com.example.mobile.common.Resource
import com.example.mobile.domain.models.CreateProductRequest
import com.example.mobile.domain.models.Product
import com.example.mobile.domain.models.ProductType
//import com.example.mobile.domain.models.UpdateProductRequest


interface ProductRepository {
    suspend fun getProducts(): Resource<List<Product>>
    //suspend fun getProductById(id: String): Resource<Product>
    suspend fun getProductTypes(): Resource<List<ProductType>>
    suspend fun createProduct(request: CreateProductRequest): Resource<Product>
    //suspend fun updateProduct(id: String, request: UpdateProductRequest): Resource<Product>
    //suspend fun deleteProduct(id: String): Resource<Unit>
}