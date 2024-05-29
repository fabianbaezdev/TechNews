package cl.fabianbaez.technews.domain

import cl.fabianbaez.technews.domain.model.Hit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHitsUseCase @Inject constructor(private val repository: Repository) {
    fun execute(): Flow<List<Hit>> = repository.getHits()
}