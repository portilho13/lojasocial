package com.example.mobile.domain.models

import com.google.gson.annotations.SerializedName

data class CreateStudentRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("studentNumber")
    val studentNumber: String,

    @SerializedName("course")
    val course: String,

    @SerializedName("academicYear")
    val academicYear: Int,

    @SerializedName("socialSecurityNumber")
    val socialSecurityNumber: String,

    @SerializedName("contact")
    val contact: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)