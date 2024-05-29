package cl.fabianbaez.technews.data

import cl.fabianbaez.technews.data.local.model.LocalHit
import kotlinx.coroutines.flow.Flow

interface Local {
    suspend fun storeHits(values: List<LocalHit>)
    suspend fun getHitById(id: String): Flow<LocalHit>

    suspend fun getHits(): Flow<List<LocalHit>>
    suspend fun hideHit(hit: LocalHit)

}