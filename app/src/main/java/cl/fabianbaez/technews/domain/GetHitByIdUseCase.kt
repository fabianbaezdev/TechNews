package cl.fabianbaez.technews.domain

import cl.fabianbaez.technews.domain.model.Hit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHitByIdUseCase @Inject constructor(private val repository: Repository) {
    fun execute(id: String): Flow<Hit> = repository.getHitById(id)
}