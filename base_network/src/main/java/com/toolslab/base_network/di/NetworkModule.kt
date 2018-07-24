package com.toolslab.base_network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.toolslab.base_network.ApiEndpoint
import com.toolslab.base_network.CoworkingMapService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideCoworkingMapService(retrofit: Retrofit): CoworkingMapService {
        return retrofit.create(CoworkingMapService::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(ApiEndpoint.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = BODY // TODO  if (DEBUG) BODY else NONE
        return httpLoggingInterceptor
    }

    @Provides
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        builder.setPrettyPrinting() // TODO if (DEBUG)
        return builder.create()
    }

}
