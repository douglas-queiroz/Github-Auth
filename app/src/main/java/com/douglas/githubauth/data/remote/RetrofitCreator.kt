package com.douglas.githubauth.data.remote

import com.douglas.githubauth.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCreator {

    companion object {

        private fun provideGson(): Gson =
            GsonBuilder()
                .setLenient()
                .create()

        private fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        fun createRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API)
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(provideOkHttpClient())
                .build()
    }
}
