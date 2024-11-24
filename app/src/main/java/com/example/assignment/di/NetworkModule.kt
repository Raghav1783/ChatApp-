package com.example.assignment.di

import com.example.assignment.network.AuthApi
import com.example.assignment.network.HeaderIntercepetor
import com.example.assignment.network.MessageApi
import com.example.assignment.utils.Constants.BASE_URL
import com.example.assignment.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofitbuilder: Retrofit.Builder): AuthApi {
        return retrofitbuilder.build().create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageApi(retrofitbuilder: Retrofit.Builder,okHttpClient: OkHttpClient): MessageApi {
        return retrofitbuilder.client(okHttpClient).build().create(MessageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(headerInterceptor: HeaderIntercepetor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(tokenManager: TokenManager): HeaderIntercepetor {
        return HeaderIntercepetor(tokenManager)
    }

}