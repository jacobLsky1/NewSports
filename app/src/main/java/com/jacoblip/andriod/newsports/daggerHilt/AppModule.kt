package com.jacoblip.andriod.newsports.daggerHilt

import android.content.Context
import com.jacoblip.andriod.newsports.data.services.repositorys.MainRepository
import com.jacoblip.andriod.newsports.data.services.repositorys.MatchRepository
import com.jacoblip.andriod.newsports.data.services.repositorys.SplashRepository
import com.jacoblip.andriod.newsports.data.services.repositorys.TeamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun getContext( @ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun getMainRepository() = MainRepository()

    @Singleton
    @Provides
    fun getSplashRepository() = SplashRepository()

    @Singleton
    @Provides
    fun getMatchRepository() = MatchRepository()

    @Singleton
    @Provides
    fun getTeamRepository() = TeamRepository()

}