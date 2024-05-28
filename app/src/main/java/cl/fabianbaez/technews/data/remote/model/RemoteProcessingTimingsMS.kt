package cl.fabianbaez.technews.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteProcessingTimingsMS(
    @SerializedName("_request") val request: RemoteRequest?,
    val fetch: RemoteFetch?,
    val total: Int?
)