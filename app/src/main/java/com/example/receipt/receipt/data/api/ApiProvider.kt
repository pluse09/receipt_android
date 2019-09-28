package com.example.receipt.receipt.data.api

import android.content.SharedPreferences
import com.example.receipt.receipt.commons.Configuration
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiProvider {

    companion object {

        fun create(): API {
            val retrofit = createRetrofit()
            return retrofit.create(API::class.java)
        }
        fun createRetrofit(): Retrofit {
            val moshi = createMoshi()
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(loggingInterceptor)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)

            val baseURL = Configuration.API_URL
            return Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(baseURL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        fun createMoshi(): Moshi {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }
    }

}

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class, IllegalArgumentException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Accept-Charset", "utf-8")
            .header("accept", "application/json")
            .build()

        return chain.proceed(request)
    }

}