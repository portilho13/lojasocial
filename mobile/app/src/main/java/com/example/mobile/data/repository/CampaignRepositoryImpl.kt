package com.example.mobile.data.repository

import com.example.mobile.common.Resource
import com.example.mobile.data.remote.CampaignApiService
import com.example.mobile.data.remote.dto.CreateCampaignDto
import com.example.mobile.domain.model.Campaign
import com.example.mobile.domain.repository.CampaignRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CampaignRepositoryImpl @Inject constructor(
    private val apiService: CampaignApiService
) : CampaignRepository {

    override suspend fun getCampaigns(): Resource<List<Campaign>> {
        return try {
            val response = apiService.getCampaigns()

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.map { it.toDomain() })
            } else {
                Resource.Error(
                    message = when (response.code()) {
                        401 -> "Sessão expirada. Faça login novamente"
                        403 -> "Sem permissão para visualizar campanhas"
                        404 -> "Campanhas não encontradas"
                        else -> "Erro ao carregar campanhas: ${response.message()}"
                    }
                )
            }
        } catch (e: HttpException) {
            Resource.Error(
                message = when (e.code()) {
                    401 -> "Sessão expirada"
                    500 -> "Erro no servidor"
                    else -> "Erro de conexão"
                }
            )
        } catch (e: IOException) {
            Resource.Error(message = "Sem conexão à internet")
        } catch (e: Exception) {
            Resource.Error(message = "Erro inesperado: ${e.localizedMessage}")
        }
    }

    override suspend fun createCampaign(campaign: CreateCampaignDto): Resource<Campaign> {
        return try {
            val response = apiService.createCampaign(campaign)

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.toDomain())
            } else {
                Resource.Error(
                    message = when (response.code()) {
                        400 -> "Dados inválidos"
                        409 -> "Campanha já existe"
                        else -> "Erro ao criar campanha: ${response.message()}"
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

    override suspend fun deleteCampaign(id: String): Resource<Unit> {
        return try {
            val response = apiService.deleteCampaign(id)

            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error(
                    message = when (response.code()) {
                        404 -> "Campanha não encontrada"
                        409 -> "Não é possível eliminar campanha com doações"
                        else -> "Erro ao eliminar campanha"
                    }
                )
            }
        } catch (e: Exception) {
            Resource.Error(message = "Erro: ${e.localizedMessage}")
        }
    }
}
