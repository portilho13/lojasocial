package com.example.mobile.domain.repository

import com.example.mobile.common.Resource
import com.example.mobile.data.remote.dto.Student
import com.example.mobile.domain.models.CreateStudentRequest

interface StudentRepository {
    suspend fun getStudents(): Resource<List<Student>>
    suspend fun createStudent(request: CreateStudentRequest): Resource<Student>
}