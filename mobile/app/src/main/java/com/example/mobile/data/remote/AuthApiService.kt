package com.example.mobile.data.remote

import com.example.mobile.data.remote.dto.LoginResponse
import com.example.mobile.data.remote.dto.RegisterResponse
import com.example.mobile.domain.models.LoginRequest
import com.example.mobile.domain.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {

    //AUTH
    @POST("/api/v1/auth/user/sign-in")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("/api/v1/auth/user/refresh")
    suspend fun refreshToken(
        @Body request: Map<String, String>
    ): Response<LoginResponse>

    @POST("/api/v1/auth/user/logout")
    suspend fun logout(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @POST("/api/v1/auth/user/sign-up")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/v1/auth/student/sign-up")
    suspend fun registerStudent(
        @Body request: RegisterRequest
    ): Response<Unit>

    @POST("api/v1/auth/student/sign-in")
    suspend fun loginStudent(
        @Body request: LoginRequest
    ): Response<Unit>

    @POST("/api/v1/auth/student/refresh")
    suspend fun studentRefreshToken(
        @Body request: Map<String, String>
    ): Response<LoginResponse>

    @POST("/api/v1/auth/student/logout")
    suspend fun studentLogout(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    //Beneficiary

       @POST("api/v1/beneficiaries")
    suspend fun createBeneficiary(
        @Body request: LoginRequest
    ): Response<Unit>

    @GET("api/v1/beneficiaries")
    suspend fun getAllBeneficiaries(
        @Body request: Map<String, String>
    ): Response<Unit>

    @GET("/api/v1/beneficiaries/:id")
    suspend fun getBeneficiaryById(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @PATCH("/api/v1/beneficiaries/:id")
    suspend fun updateBeneficiary(
        @Body request: RegisterRequest
    ): Response<Unit>

    @DELETE("/api/v1/beneficiaries/:id")
    suspend fun deleteBeneficiary(
        @Body request: RegisterRequest
    ): Response<Unit>

    //Campaigns

    @GET("/api/v1/campaigns/active")
    suspend fun getActiveCampaigns(
        @Body request: LoginRequest
    ): Response<Unit>

    @POST("/api/v1/campaigns")
    suspend fun createCampaign(
        @Body request: Map<String, String>
    ): Response<Unit>

    @GET("/api/v1/campaigns?skip=0&take=50")
    suspend fun getAllCampaigns(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @GET("/api/v1/campaigns/:id")
    suspend fun getCampaignById(
        @Body request: RegisterRequest
    ): Response<Unit>

    @PUT("/api/v1/campaigns/:id")
    suspend fun updateCampaign(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @DELETE("/api/v1/campaigns/:id")
    suspend fun updateCampaign(
        @Body request: RegisterRequest
    ): Response<Unit>

    //Donations
    @POST("/api/v1/donations")
    suspend fun createDonation(
        @Body request: Map<String, String>
    ): Response<Unit>

    @GET("/api/v1/donations?skip=0&take=50")
    suspend fun getAllDonations(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

     @GET("/api/v1/donations/:id")
    suspend fun getDonationById(
        @Body request: RegisterRequest
    ): Response<Unit>

    @GET("/api/v1/donations/donor/:donorId")
    suspend fun getDonationsByDonor(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @GET("/api/v1/donations/campaign/:campaignId")
    suspend fun getDonationsByCampaign(
        @Body request: RegisterRequest
    ): Response<Unit>

    @POST("/api/v1/donations/donors")
    suspend fun createDonor(
        @Body request: Map<String, String>
    ): Response<Unit>

    @GET("/api/v1/donations/donors")
    suspend fun getAllDonors(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @GET("/api/v1/donations/donors/:id")
    suspend fun getDonorById(
        @Body request: RegisterRequest
    ): Response<Unit>

    @PUT("/api/v1/donations/donors/:id")
    suspend fun updateDonor(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @DELETE("/api/v1/donations/donors/:id")
    suspend fun deleteDonor(
        @Body request: RegisterRequest
    ): Response<Unit>

    //Inventory

    @POST("/api/v1/inventory/types")
    suspend fun createProductType(
        @Body request: Map<String, String>
    ): Response<Unit>

    @GET("/api/v1/inventory/types")
    suspend fun getAllProductTypes(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @POST("/api/v1/inventory/products")
    suspend fun createProduct(
        @Body request: Map<String, String>
    ): Response<Unit>

    @GET("/api/v1/inventory/products")
    suspend fun getAllProducts(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @POST("/api/v1/inventory/stocks")
    suspend fun createStock(
        @Body request: Map<String, String>
    ): Response<Unit>

    @GET("/api/v1/inventory/stocks")
    suspend fun getAllStocks(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @PATCH("/api/v1/inventory/stocks/:id")
    suspend fun updateStock(
        @Body request: RegisterRequest
    ): Response<Unit>

    @DELETE("/api/v1/inventory/stocks/:id")
    suspend fun deleteStock(
        @Body request: RegisterRequest
    ): Response<Unit>

    @GET("/api/v1/inventory/stocks/expiring?days=:days")
    suspend fun getExpiringStocks(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @GET("/api/v1/inventory/stocks/summary")
    suspend fun getStockSummary(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    //SupportRequests

    @POST("/api/v1/support-requests")
    suspend fun createSupportRequest(
        @Body request: Map<String, String>
    ): Response<Unit>

    @GET("/api/v1/support-requests")
    suspend fun getAllSupportRequests(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @GET("/api/v1/support-requests/me")
    suspend fun getMySupportRequests(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @PATCH("/api/v1/support-requests/:id/status")
    suspend fun updateSupportRequestStatus(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    //Apointments
    @POST("api/v1/appointments")
    suspend fun createAppointment(
        @Body request: Map<String, String>
    ): Response<Unit>

    @GET("api/v1/appointments")
    suspend fun getAllAppointments(
        @Body request: Map<String, String> // {"refreshToken": "..."}
    ): Response<Unit>

    @POST("/api/v1/appointments/:id/send-confirmation")
    suspend fun sendAppointmentConfirmation(
        @Body request: RegisterRequest
    ): Response<Unit>
}
