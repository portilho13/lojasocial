package com.example.mobile.data.repository

import com.example.mobile.common.Resource
import com.example.mobile.data.remote.SupportRequestApiService
import com.example.mobile.data.remote.dto.UpdateStatusDto
import com.example.mobile.domain.models.RequestStatus
import com.example.mobile.domain.models.SupportRequest
import com.example.mobile.domain.repository.SupportRequestRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SupportRequestRepositoryImpl @Inject constructor(
    private val apiService: SupportRequestApiService
) : SupportRequestRepository {

    override suspend fun getRequests(): Resource<List<SupportRequest>> {
        return try {
            val response = apiService.getRequests()

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.map { it.toDomain() })
            } else {
                Resource.Error(
                    message = when (response.code()) {
                        401 -> "Sessão expirada. Faça login novamente"
                        403 -> "Sem permissão"
                        else -> "Erro ao carregar pedidos: ${response.message()}"
                    }
                )
            }
        } catch (e: HttpException) {
            Resource.Error(message = "Erro no servidor")
        } catch (e: IOException) {
            Resource.Error(message = "Sem conexão à internet")
        } catch (e: Exception) {
            Resource.Error(message = "Erro inesperado: ${e.localizedMessage}")
        }
    }

    override suspend fun updateStatus(id: String, status: RequestStatus): Resource<SupportRequest> {
        return try {
            val response = apiService.updateStatus(id, UpdateStatusDto(status.name))

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.toDomain())
            } else {
                Resource.Error(message = "Erro ao atualizar estado")
            }
        } catch (e: Exception) {
            Resource.Error(message = "Erro: ${e.localizedMessage}")
        }
    }
}
