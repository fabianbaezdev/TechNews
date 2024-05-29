package cl.fabianbaez.technews.domain

import cl.fabianbaez.technews.domain.model.Hit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HideHitUseCase @Inject constructor(private val repository: Repository) {
    suspend fun execute(hit: Hit): Flow<List<Hit>> = repository.hideHit(hit)
}