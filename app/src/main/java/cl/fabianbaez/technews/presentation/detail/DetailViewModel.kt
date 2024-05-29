package cl.fabianbaez.technews.presentation.detail

import androidx.lifecycle.SavedStateHandle
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailAction.BackAction
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailAction.GetHitAction
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailEffect
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailEffect.Navigation.BackNavigation
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailIntent
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailIntent.GetHitIntent
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailResult
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState.DefaultUiState
import cl.fabianbaez.technews.presentation.mvi.BaseViewModel
import cl.fabianbaez.technews.ui.navigation.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val processor: DetailProcessor,
    private val reducer: DetailReducer,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailState, DetailEffect, DetailResult, DetailIntent>(
    initialState = DefaultUiState
) {

    init {
        val hitId = savedStateHandle.get<String>(Navigation.Args.HIT_ID).orEmpty()
        acceptIntent(GetHitIntent(hitId))

    }

    private fun DetailIntent.toAction(): DetailMVI.DetailAction = when (this) {
        DetailIntent.BackClicked -> BackAction
        is GetHitIntent -> GetHitAction(id)
    }

    override fun mapIntents(intent: DetailIntent): Flow<DetailResult> =
        processor.actionProcessor(intent.toAction())

    override fun reduceUiState(
        previousState: DetailState,
        result: DetailResult
    ): DetailState = when (result) {
        is DetailResult.BackResult -> {
            backClicked()
            previousState
        }

        else -> with(reducer) { previousState reduceWith result }
    }

    private fun backClicked(): Flow<DetailResult> {
        publishEffect(BackNavigation)
        return emptyFlow()
    }
}