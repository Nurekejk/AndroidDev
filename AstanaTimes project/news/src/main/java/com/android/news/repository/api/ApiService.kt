package com.android.news.repository.api

import com.android.news.model.Data
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val API_KEY = "e65ee0938a2a43ebb15923b48faed18d"
private const val BASE_URL = "https://newsapi.org/"

interface ApiService {
    companion object Factory {
        fun create(): ApiService {
            val requestInterceptor = Interceptor {
                val url = it.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apiKey",
                        API_KEY
                    )
                    .build()

                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor it.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java);
        }
    }

    @GET("v2/top-headlines")
    fun getTopHeadlines(@Query("q")keyword: String,
                        @Query("pageSize")pageSize:Int,
                        @Query("page")page:Int): Single<Data>

    @GET("v2/everything")
    fun getEverything(@Query("q")keyword: String,
                      @Query("pageSize")pageSize:Int,
                      @Query("page")page:Int): Single<Data>

}