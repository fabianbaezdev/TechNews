package cl.fabianbaez.technews.presentation.detail

import cl.fabianbaez.technews.domain.model.Hit
import cl.fabianbaez.technews.presentation.mvi.MviEffect
import cl.fabianbaez.technews.presentation.mvi.MviIntent
import cl.fabianbaez.technews.presentation.mvi.MviResult
import cl.fabianbaez.technews.presentation.mvi.MviState

class DetailMVI {
    sealed class Intent : MviIntent {
        object BackClicked : Intent()
    }


    sealed class MainUiState : MviState {
        object DefaultUiState : MainUiState()
        object LoadingUiState : MainUiState()
        data class DisplayHitUiState(val hit: Hit) : MainUiState()
        data class ErrorUiState(val error: Throwable) : MainUiState()
    }

    sealed class Effect : MviEffect {
        sealed class Navigation : Effect() {
            object BackNavigation : Navigation()
        }
    }

    sealed class MainResult : MviResult {
        object InProgress : MainResult()
        data class Success(val hit: Hit) : MainResult()
        data class Error(val error: Throwable) : MainResult()

    }

}