package com.example.mobile.data.repository

import com.example.mobile.data.remote.StudentApiService
import com.example.mobile.domain.models.CreateStudentRequest
import com.example.mobile.domain.repository.StudentRepository
import com.example.mobile.common.Resource
import com.example.mobile.data.remote.dto.Student
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val apiService: StudentApiService
) : StudentRepository {

    override suspend fun getStudents(): Resource<List<Student>> {
        return try {
            val response = apiService.getStudents()

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(
                    message = when (response.code()) {
                        401 -> "Não autorizado. Faça login novamente"
                        403 -> "Sem permissão para visualizar estudantes"
                        404 -> "Estudantes não encontrados"
                        else -> "Erro ao carregar estudantes: ${response.message()}"
                    }
                )
            }
        } catch (e: HttpException) {
            Resource.Error(
                message = when (e.code()) {
                    401 -> "Sessão expirada. Faça login novamente"
                    403 -> "Sem permissão para visualizar estudantes"
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

    override suspend fun createStudent(request: CreateStudentRequest): Resource<Student> {
        return try {
            val response = apiService.createStudent(request)

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(
                    message = when (response.code()) {
                        400 -> "Dados inválidos. Verifique as informações"
                        409 -> "Estudante já existe"
                        422 -> "Dados fornecidos não são válidos"
                        else -> "Erro ao criar estudante: ${response.message()}"
                    }
                )
            }
        } catch (e: HttpException) {
            Resource.Error(
                message = when (e.code()) {
                    400 -> "Dados inválidos"
                    409 -> "Estudante já existe"
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
}