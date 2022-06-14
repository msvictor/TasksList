package com.example.tasks.infra

import com.example.tasks.core.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InternetClientHelper private constructor() {
    companion object {
        private lateinit var retrofit: Retrofit

        private var personKey = ""

        private var tokenKey = ""

        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                chain.proceed(
                    chain
                        .request()
                        .newBuilder()
                        .addHeader(Constants.API.HEADER.PERSON_KEY, personKey)
                        .addHeader(Constants.API.HEADER.TOKEN_KEY, tokenKey)
                        .build()
                )
            }

            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Constants.API.BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }

        fun addHeaders(token: String, personKey: String) {
            this.personKey = personKey
            this.tokenKey = token
        }

        fun <T> createService(serviceClass: Class<T>): T {
            return getRetrofitInstance().create(serviceClass)
        }
    }
}