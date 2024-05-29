package cl.fabianbaez.technews.presentation.list

import android.util.Log
import cl.fabianbaez.technews.domain.GetHitsUseCase
import cl.fabianbaez.technews.domain.HideHitUseCase
import cl.fabianbaez.technews.domain.model.Hit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ListProcessor @Inject constructor(
    private val getHitsUseCase: GetHitsUseCase,
    private val hideHitUseCase: HideHitUseCase
) {
    fun actionProcessor(action: ListMVI.ListAction): Flow<ListMVI.ListResult> =
        when (action) {
            ListMVI.ListAction.GetHitsAction -> getHitsProcessor()
            is ListMVI.ListAction.HitDetailAction -> detailProcessor(action.id)
            is ListMVI.ListAction.HideHitAction -> hideHitProcessor(action.hit)
        }

    @Suppress("TooGenericExceptionCaught", "USELESS_CAST")
    private fun getHitsProcessor(): Flow<ListMVI.ListResult> =
        getHitsUseCase
            .execute()
            .map { hits ->
                ListMVI.ListResult.GetHitsResult.Success(hits)
                        as ListMVI.ListResult
            }.onStart {
                emit(ListMVI.ListResult.GetHitsResult.InProgress)
            }.catch { cause ->
                cause.printStackTrace()
                Log.e("getHitsProcessor", "Error", cause)
                emit(ListMVI.ListResult.GetHitsResult.Error(cause))
            }.flowOn(Dispatchers.IO)

    private fun hideHitProcessor(hit: Hit) = flow<ListMVI.ListResult> {
        hideHitUseCase.execute(hit).collect { hits ->
            emit(ListMVI.ListResult.HideHitsResult.Success(hits))
        }
    }.onStart {
        emit(ListMVI.ListResult.HideHitsResult.InProgress)
    }.catch { cause ->
        cause.printStackTrace()
        Log.e("hideHitProcessor", "Error", cause)
        emit(ListMVI.ListResult.HideHitsResult.Error(cause))
    }.flowOn(Dispatchers.IO)

    private fun detailProcessor(id: String): Flow<ListMVI.ListResult> =
        flow { emit(ListMVI.ListResult.DetailResult(id)) }

}