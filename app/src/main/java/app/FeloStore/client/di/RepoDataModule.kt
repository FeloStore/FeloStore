package app.FeloStore.client.di

import app.FeloStore.client.data.RepoDataFetcher
import app.FeloStore.client.data.RepoDataFetcherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoDataModule {
    @Binds
    abstract fun bindRepoDataFetcher(repoDataFetcherImpl: RepoDataFetcherImpl): RepoDataFetcher
}
