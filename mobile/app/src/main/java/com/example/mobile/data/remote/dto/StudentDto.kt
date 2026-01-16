package com.example.mobile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("studentNumber")
    val studentNumber: String,

    @SerializedName("course")
    val course: String,

    @SerializedName("academicYear")
    val academicYear: Int,

    @SerializedName("socialSecurityNumber")
    val socialSecurityNumber: String?,

    @SerializedName("contact")
    val contact: String?,

    @SerializedName("email")
    val email: String
)