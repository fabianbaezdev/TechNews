package cl.fabianbaez.technews.domain

import cl.fabianbaez.technews.data.local.model.LocalHit
import cl.fabianbaez.technews.domain.model.Hit
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getHits(): Flow<List<Hit>>
    suspend fun hideHit(hit: LocalHit): Flow<List<Hit>>
}