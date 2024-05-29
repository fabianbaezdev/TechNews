package cl.fabianbaez.technews.presentation.detail

import android.util.Log
import cl.fabianbaez.technews.domain.GetHitByIdUseCase
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailAction
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailResult
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailResult.HitResult
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailResult.HitResult.Error
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailResult.HitResult.InProgress
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailResult.HitResult.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class DetailProcessor @Inject constructor(
    private val getHitByIdUseCase: GetHitByIdUseCase
) {
    fun actionProcessor(action: DetailAction): Flow<DetailResult> =
        when (action) {
            DetailAction.BackAction -> backProcessor()
            is DetailAction.GetHitAction -> getHitProcessor(action.id)
        }

    @Suppress("TooGenericExceptionCaught", "USELESS_CAST")
    private fun getHitProcessor(id: String): Flow<HitResult> =
        getHitByIdUseCase
            .execute(id)
            .map { hits ->
                Success(hits)
                        as HitResult
            }.onStart {
                emit(InProgress)
            }.catch { cause ->
                cause.printStackTrace()
                Log.e("getHitProcessor", "Error", cause)
                emit(Error(cause))
            }.flowOn(Dispatchers.IO)

    private fun backProcessor(): Flow<DetailResult> =
        flow { emit(DetailResult.BackResult) }
}