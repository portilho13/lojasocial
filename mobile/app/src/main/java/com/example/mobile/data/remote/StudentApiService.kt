package com.example.mobile.data.remote

import com.example.mobile.data.remote.dto.Student
import com.example.mobile.domain.models.CreateStudentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StudentApiService {

    @GET("api/v1/beneficiaries")
    suspend fun getStudents(): Response<List<Student>>

    @POST("api/v1/beneficiaries")
    suspend fun createStudent(@Body request: CreateStudentRequest): Response<Student>
}