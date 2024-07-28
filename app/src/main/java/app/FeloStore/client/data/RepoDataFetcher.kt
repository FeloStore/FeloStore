package app.FeloStore.client.data

import app.FeloStore.client.data.net.AppRepoData
import app.FeloStore.client.data.net.RepoData

interface RepoDataFetcher {
    fun fetchRepoData(): RepoData
    fun fetchAppRepoData(appId: String): AppRepoData
}
