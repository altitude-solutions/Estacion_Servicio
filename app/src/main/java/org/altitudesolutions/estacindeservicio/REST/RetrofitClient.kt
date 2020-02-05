package org.altitudesolutions.estacindeservicio.REST

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
//    private const val BASE_URL = "http://192.168.0.3:4200"
    private const val BASE_URL = "http://200.105.171.52:3000"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", "")
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()


    val Instance: REST_client by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(REST_client::class.java)
    }
}