package cl.fabianbaez.technews.domain.model

data class Hit(
    val id: String,
    val author: String,
    val createdAt: String,
    val title: String,
    val url: String
)
