package cl.fabianbaez.technews.presentation.list

import cl.fabianbaez.technews.domain.model.Hit
import cl.fabianbaez.technews.presentation.list.ListMVI.ListResult
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.DefaultUiState
import cl.fabianbaez.technews.presentation.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val processor: ListProcessor,
    private val reducer: ListReducer,
) : BaseViewModel<ListState, ListMVI.ListEffect, ListResult, ListMVI.ListIntent>(
    initialState = DefaultUiState
) {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        acceptIntent(ListMVI.ListIntent.GetHitsIntent)
    }

    private fun ListMVI.ListIntent.toAction(): ListMVI.ListAction = when (this) {
        is ListMVI.ListIntent.HitClicked -> ListMVI.ListAction.HitDetailAction(id)
        ListMVI.ListIntent.GetHitsIntent -> ListMVI.ListAction.GetHitsAction
        ListMVI.ListIntent.ReloadHitsIntent -> ListMVI.ListAction.GetHitsAction
        is ListMVI.ListIntent.RemoveHit -> ListMVI.ListAction.HideHitAction(hit)
    }

    override fun mapIntents(intent: ListMVI.ListIntent): Flow<ListResult> =
        processor.actionProcessor(intent.toAction())

    override fun reduceUiState(
        previousState: ListState,
        result: ListResult
    ): ListState = when (result) {
        is ListResult.DetailResult -> {
            hitClicked(result.id)
            previousState
        }

        else -> with(reducer) { previousState reduceWith result }
    }

    private fun hitClicked(id: String) {
        publishEffect(ListMVI.ListEffect.Navigation.HitDetail(id = id))
    }

    fun refresh() {
        acceptIntent(ListMVI.ListIntent.ReloadHitsIntent)
    }

    fun removeHit(hit: Hit) {
        acceptIntent(ListMVI.ListIntent.RemoveHit(hit))
    }
}