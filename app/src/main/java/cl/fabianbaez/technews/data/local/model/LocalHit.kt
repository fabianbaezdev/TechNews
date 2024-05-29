package cl.fabianbaez.technews.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = HIT_TABLE)
data class LocalHit(
    @PrimaryKey
    val id: String,
    val author: String,
    val createdAt: String,
    val title: String,
    val url: String,
    val hide: Boolean
)

const val HIT_TABLE = "hit_table"

