package cl.fabianbaez.technews.presentation.list

import cl.fabianbaez.technews.presentation.list.ListMVI.ListResult
import cl.fabianbaez.technews.presentation.list.ListMVI.ListResult.GetHitsResult
import cl.fabianbaez.technews.presentation.list.ListMVI.ListResult.HideHitsResult
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.DefaultUiState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.DisplayListUiState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.ErrorUiState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.LoadingUiState
import cl.fabianbaez.technews.presentation.mvi.MviReducer
import cl.fabianbaez.technews.presentation.mvi.UnsupportedReduceException
import javax.inject.Inject

class ListReducer @Inject constructor() :
    MviReducer<ListState, ListResult> {
    override fun ListState.reduceWith(result: ListResult): ListState {
        return when (val previousState = this) {
            is DefaultUiState -> previousState reduceWith result
            is LoadingUiState -> previousState reduceWith result
            is DisplayListUiState -> previousState reduceWith result
            is ErrorUiState -> previousState reduceWith result
        }
    }

    private infix fun LoadingUiState.reduceWith(result: ListResult): ListState {
        return when (result) {
            is GetHitsResult.Error -> ErrorUiState(result.error)
            is GetHitsResult.Success -> DisplayListUiState(result.hits)
            is HideHitsResult.Error -> ErrorUiState(result.error)
            is HideHitsResult.Success -> DisplayListUiState(result.hits)
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun DefaultUiState.reduceWith(result: ListResult): ListState {
        return when (result) {
            GetHitsResult.InProgress -> LoadingUiState
            HideHitsResult.InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun DisplayListUiState.reduceWith(result: ListResult): ListState {
        return when (result) {
            GetHitsResult.InProgress -> LoadingUiState
            HideHitsResult.InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    private infix fun ErrorUiState.reduceWith(result: ListResult): ListState {
        return when (result) {
            GetHitsResult.InProgress -> LoadingUiState
            HideHitsResult.InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

}