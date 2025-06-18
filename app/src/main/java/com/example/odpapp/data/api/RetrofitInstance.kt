package com.example.odpapp.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.odpapp.Secrets

object RetrofitInstance {
    private const val BASE_URL = "https://odp.iddeea.gov.ba:8096/"

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${Secrets.ODP_TOKEN}")
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
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
