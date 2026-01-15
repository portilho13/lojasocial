package com.example.mobile.domain.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("typeId")
    val typeId: String? = null,

    @SerializedName("typeDescription")
    val typeDescription: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null
)

data class ProductType(
    @SerializedName("id")
    val id: String,

    @SerializedName("description")
    val description: String
)

data class CreateProductRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("typeId")
    val typeId: String
)

//data class UpdateProductRequest(
//    @SerializedName("name")
//    val name: String? = null,
//
//    @SerializedName("description")
//    val description: String? = null,
//
//    @SerializedName("productTypeId")
//    val productTypeId: String? = null
//)