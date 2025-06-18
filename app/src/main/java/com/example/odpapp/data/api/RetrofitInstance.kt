package com.example.odpapp.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.odpapp.Secrets
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitInstance {
    private const val BASE_URL = "https://odp.iddeea.gov.ba:8096/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${Secrets.ODP_TOKEN}")
            .addHeader("Accept", "application/json")  // <-- dodano
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)  // <- Dodaj logging

        .build()

    // API za aktivne registracije
    val activeRegistrationsApi: ActiveRegistrationsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActiveRegistrationsApi::class.java)
    }

    // API za registrovana vozila
    val registeredVehiclesApi: RegisteredVehiclesApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegisteredVehiclesApi::class.java)
    }
}
