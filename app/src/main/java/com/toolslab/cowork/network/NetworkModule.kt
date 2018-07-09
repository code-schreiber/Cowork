package com.toolslab.cowork.network

import dagger.Module
import dagger.Provides
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(ApiEndpoint.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    fun provideOkHttpClient(authenticator: Authenticator): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .authenticator(authenticator)
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    @Provides
    fun provideAuthenticator(): Authenticator {
        // TODO implement Authenticator
        return Authenticator.NONE
    }

}
