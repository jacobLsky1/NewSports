package com.jacoblip.andriod.newsports.daggerHilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

   // @Singleton
   // @Provides
    //fun providesRepository() = Repository()

    @Singleton
    @Provides
    fun getContext( @ApplicationContext context: Context) = context



}