package com.example.mobile.domain.repository

import com.example.mobile.common.Resource
import com.example.mobile.domain.models.CreateProductRequest
import com.example.mobile.domain.models.CreateProductTypeRequest
import com.example.mobile.domain.models.Product
import com.example.mobile.domain.models.ProductType
//import com.example.mobile.domain.models.UpdateProductRequest


interface ProductRepository {
    suspend fun getProducts(): Resource<List<Product>>
    suspend fun createProduct(request: CreateProductRequest): Resource<Product>
    suspend fun getProductTypes(): Resource<List<ProductType>>
    suspend fun createProductType(request: CreateProductTypeRequest): Resource<ProductType>

}