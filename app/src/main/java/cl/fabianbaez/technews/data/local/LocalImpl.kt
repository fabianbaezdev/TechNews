package cl.fabianbaez.technews.data.local

import cl.fabianbaez.technews.data.Local
import cl.fabianbaez.technews.data.local.database.DatabaseBuilder
import cl.fabianbaez.technews.data.local.model.LocalHit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalImpl @Inject constructor(
    private val databaseBuilder: DatabaseBuilder
) : Local {
    override suspend fun storeHits(values: List<LocalHit>) {
        databaseBuilder.hitDao().insertAll(values)
    }

    override suspend fun getHits(): Flow<List<LocalHit>> = flow {
        val hits = databaseBuilder.hitDao().getAll()
        emit(hits)
    }

    override suspend fun hideHit(hit: LocalHit) {
        databaseBuilder.hitDao().updateHit(hit.copy(hide = true))
    }
}