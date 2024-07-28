package app.FeloStore.client.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.NetworkType
import app.FeloStore.client.data.PreferencesManager
import app.FeloStore.client.data.Theme
import app.FeloStore.client.workers.AutoUpdateWorker
import app.FeloStore.client.workers.RepositoryRefreshWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val preferencesManager: PreferencesManager) :
    ViewModel() {
    val dynamicColor = preferencesManager.dynamicColor
    val theme = preferencesManager.theme
    var automaticUpdates = preferencesManager.automaticUpdates
    val updaterNetworkType = preferencesManager.networkType

    suspend fun setDynamicColor(dynamicColor: Boolean) =
        preferencesManager.setDynamicColor(dynamicColor)

    suspend fun setTheme(theme: Theme) = preferencesManager.setTheme(theme)

    suspend fun setUpdaterNetworkType(context: Context, networkType: NetworkType) {
        preferencesManager.setNetworkType(networkType.name)
        RepositoryRefreshWorker.enqueue(context, networkType)
        AutoUpdateWorker.enqueue(context, networkType)
    }

    suspend fun setAutomaticUpdates(automaticUpdates: Boolean) =
        preferencesManager.setAutomaticUpdates(automaticUpdates)
}
