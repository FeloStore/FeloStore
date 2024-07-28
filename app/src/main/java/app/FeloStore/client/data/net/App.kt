package app.FeloStore.client.data.net

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App(
    val name: String,
    @SerialName("min_version_code") val minVersionCode: Int,
    @SerialName("signing_cert_hashes") val signingCertHashes: List<String>,
)
