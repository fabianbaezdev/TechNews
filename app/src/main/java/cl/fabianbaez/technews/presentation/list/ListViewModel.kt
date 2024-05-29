package cl.fabianbaez.technews.presentation.list

import cl.fabianbaez.technews.domain.model.Hit
import cl.fabianbaez.technews.presentation.list.ListMVI.ListAction.GetHitsAction
import cl.fabianbaez.technews.presentation.list.ListMVI.ListAction.HideHitAction
import cl.fabianbaez.technews.presentation.list.ListMVI.ListAction.HitDetailAction
import cl.fabianbaez.technews.presentation.list.ListMVI.ListEffect
import cl.fabianbaez.technews.presentation.list.ListMVI.ListIntent
import cl.fabianbaez.technews.presentation.list.ListMVI.ListIntent.GetHitsIntent
import cl.fabianbaez.technews.presentation.list.ListMVI.ListIntent.HitClicked
import cl.fabianbaez.technews.presentation.list.ListMVI.ListIntent.ReloadHitsIntent
import cl.fabianbaez.technews.presentation.list.ListMVI.ListIntent.RemoveHit
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
) : BaseViewModel<ListState, ListEffect, ListResult, ListIntent>(
    initialState = DefaultUiState
) {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        acceptIntent(GetHitsIntent)
    }

    private fun ListIntent.toAction(): ListMVI.ListAction = when (this) {
        is HitClicked -> HitDetailAction(id)
        GetHitsIntent -> GetHitsAction
        ReloadHitsIntent -> GetHitsAction
        is RemoveHit -> HideHitAction(hit)
    }

    override fun mapIntents(intent: ListIntent): Flow<ListResult> =
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
        publishEffect(ListEffect.Navigation.HitDetail(id = id))
    }

    fun refresh() {
        acceptIntent(ReloadHitsIntent)
    }

    fun removeHit(hit: Hit) {
        acceptIntent(RemoveHit(hit))
    }
}