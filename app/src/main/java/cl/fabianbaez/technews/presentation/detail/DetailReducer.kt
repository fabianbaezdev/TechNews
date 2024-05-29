package cl.fabianbaez.technews.presentation.detail

import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailResult
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailResult.HitResult.Error
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailResult.HitResult.InProgress
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailResult.HitResult.Success
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState.DefaultUiState
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState.DisplayHitUiState
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState.ErrorUiState
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState.LoadingUiState
import cl.fabianbaez.technews.presentation.mvi.MviReducer
import cl.fabianbaez.technews.presentation.mvi.UnsupportedReduceException
import javax.inject.Inject

class DetailReducer @Inject constructor() : MviReducer<DetailState, DetailResult> {
    override fun DetailState.reduceWith(result: DetailResult): DetailState {
        return when (val previousState = this) {
            is DefaultUiState -> previousState reduceWith result
            is DisplayHitUiState -> previousState reduceWith result
            is ErrorUiState -> previousState reduceWith result
            is LoadingUiState -> previousState reduceWith result
        }
    }

    private infix fun LoadingUiState.reduceWith(result: DetailResult): DetailState {
        return when (result) {
            is Error -> ErrorUiState(result.error)
            is Success -> DisplayHitUiState(result.hit)
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun DefaultUiState.reduceWith(result: DetailResult): DetailState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun DisplayHitUiState.reduceWith(result: DetailResult): DetailState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun ErrorUiState.reduceWith(result: DetailResult): DetailState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

}