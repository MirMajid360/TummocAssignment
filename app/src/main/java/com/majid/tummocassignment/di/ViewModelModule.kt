package com.majid.tummocassignment.di

import com.majid.tummocassignment.data.repository.ProductRepository
import com.majid.tummocassignment.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ViewModelModule {
    @Singleton
    @Provides
    fun providesMainViewModel(repository: ProductRepository): MainViewModel {
        return MainViewModel(repository)
    }


}