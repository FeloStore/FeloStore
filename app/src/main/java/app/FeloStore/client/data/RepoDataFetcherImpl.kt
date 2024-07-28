package app.FeloStore.client.data

import android.content.Context
import app.FeloStore.client.R
import app.FeloStore.client.data.net.AppRepoData
import app.FeloStore.client.data.net.RepoData
import app.FeloStore.client.util.verifySignature
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL
import java.security.GeneralSecurityException
import javax.inject.Inject

private val format = Json { ignoreUnknownKeys = true }

class RepoDataFetcherImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val okHttpClient: OkHttpClient,
) : RepoDataFetcher {
    override fun fetchRepoData(): RepoData {
        val repoDataUrl = "$REPOSITORY_URL/repodata.$PUBKEY_VERSION.json"

        val repoDataFile = fetchFileString(URL(repoDataUrl))
        val signature = fetchFileString(URL("$repoDataUrl.sig"))

        if (!verifySignature(REPODATA_PUBKEY, repoDataFile.toByteArray(), signature)) {
            throw GeneralSecurityException(context.getString(R.string.sig_verify_failed))
        }

        return format.decodeFromString(repoDataFile)
    }

    override fun fetchAppRepoData(appId: String): AppRepoData {
        val repoDataFile = fetchFileString(URL("$REPOSITORY_URL/apps/$appId/repodata.json"))

        return format.decodeFromString(repoDataFile)
    }

    private fun fetchFileString(url: URL): String {
        val request = Request.Builder().url(url).build()

        return okHttpClient
            .newCall(request)
            .execute()
            .use { it.body?.string() }
            ?: throw IOException("request body expected but not found")
    }
}
