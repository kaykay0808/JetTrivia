package com.example.jettrivia.di

import com.example.jettrivia.network.QuestionApi
import com.example.jettrivia.repository.QuestionRepository
import com.example.jettrivia.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// this is a dagger hilt module
// module creates the provider ->
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Add all the provider for our application here.
    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionApi) = QuestionRepository(api)

    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }
}
