package cl.fabianbaez.technews.data

import cl.fabianbaez.technews.domain.Repository
import cl.fabianbaez.technews.domain.model.Hit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remote: Remote,
    private val local: Local,
    private val hitMapper: DataHitMapper
) : Repository {
    override fun getHits(): Flow<List<Hit>> = flow {
        val remoteHits = remote.getNews().hits.orEmpty()
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