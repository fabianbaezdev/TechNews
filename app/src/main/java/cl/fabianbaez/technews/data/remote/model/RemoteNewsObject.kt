package cl.fabianbaez.technews.data.remote.model

data class RemoteNewsObject(
    val fullyHighlighted: Boolean?,
    val matchLevel: String?,
    val matchedWords: List<String>?,
    val value: String?
)