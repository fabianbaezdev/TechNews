package cl.fabianbaez.technews.data

import cl.fabianbaez.technews.data.local.model.LocalHit
import cl.fabianbaez.technews.data.remote.model.RemoteHit
import cl.fabianbaez.technews.domain.model.Hit
import javax.inject.Inject

class DataHitMapper @Inject constructor() {
    fun List<RemoteHit>.toLocal() = this.map { it.toLocal() }

    fun RemoteHit.toLocal() = LocalHit(
        id = objectID.orEmpty(),
        author = author.orEmpty(),
        createdAt = createdAt.orEmpty(),
        title = storyTitle ?: title.orEmpty(),
        url = storyUrl.orEmpty(),
        hide = false
    )

    fun List<LocalHit>.toDomain() = this.map { it.toDomain() }

    fun LocalHit.toDomain() = Hit(
        id = id,
        author = author,
        createdAt = createdAt,
        title = title,
        url = url,
    )

    fun Hit.toLocal() = LocalHit(
        id = id,
        author = author,
        createdAt = createdAt,
        title = title,
        url = url,
        hide = true
    )
}