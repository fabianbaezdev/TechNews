package cl.fabianbaez.technews.data.remote.model

data class RemoteNews(
    val exhaustive: RemoteExhaustive?,
    val exhaustiveNbHits: Boolean?,
    val exhaustiveTypo: Boolean?,
    val hits: List<RemoteHit>?,
    val hitsPerPage: Int?,
    val nbHits: Int?,
    val nbPages: Int?,
    val page: Int?,
    val params: String?,
    val processingTimeMS: Int?,
    val processingTimingsMS: RemoteProcessingTimingsMS?,
    val query: String?,
    val serverTimeMS: Int?
)