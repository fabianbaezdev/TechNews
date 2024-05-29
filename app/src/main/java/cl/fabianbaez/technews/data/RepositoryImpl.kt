package cl.fabianbaez.technews.data

import cl.fabianbaez.technews.data.remote.model.RemoteHit
import cl.fabianbaez.technews.domain.Repository
import cl.fabianbaez.technews.domain.model.Hit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remote: Remote,
    private val local: Local,
    private val hitMapper: DataHitMapper
) : Repository {
    override fun getHitById(id: String): Flow<Hit> = flow {
        local.getHitById(id).collect {
            emit(with(hitMapper) { it.toDomain() })
        }
    }

    override fun getHits(): Flow<List<Hit>> = flow {
        val remoteHits = try {
            remote.getNews().hits.orEmpty()
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            emptyList<RemoteHit>()
        }
        with(hitMapper) {
            if (remoteHits.isNotEmpty()) {
                local.storeHits(remoteHits.toLocal())
            }
            local.getHits().collect {
                emit(it.toDomain())
            }
        }
    }

    override suspend fun hideHit(hit: Hit): Flow<List<Hit>> = flow {
        local.hideHit(with(hitMapper) { hit.toLocal() }).also {
            local.getHits().collect {
                emit(with(hitMapper) { it.toDomain() })
            }
        }
    }
}