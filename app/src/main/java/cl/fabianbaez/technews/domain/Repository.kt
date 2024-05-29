package cl.fabianbaez.technews.domain

import cl.fabianbaez.technews.domain.model.Hit
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getHits(): Flow<List<Hit>>
    suspend fun hideHit(hit: Hit): Flow<List<Hit>>
}