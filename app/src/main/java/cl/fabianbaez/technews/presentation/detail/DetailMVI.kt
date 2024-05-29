package cl.fabianbaez.technews.presentation.detail

import cl.fabianbaez.technews.domain.model.Hit
import cl.fabianbaez.technews.presentation.mvi.MviAction
import cl.fabianbaez.technews.presentation.mvi.MviEffect
import cl.fabianbaez.technews.presentation.mvi.MviIntent
import cl.fabianbaez.technews.presentation.mvi.MviResult
import cl.fabianbaez.technews.presentation.mvi.MviState

class DetailMVI {
    sealed class DetailIntent : MviIntent {
        data object BackClicked : DetailIntent()
        data class GetHitIntent(val id: String) : DetailIntent()
    }


    sealed class DetailState : MviState {
        data object DefaultUiState : DetailState()
        data object LoadingUiState : DetailState()
        data class DisplayHitUiState(val hit: Hit) : DetailState()
        data class ErrorUiState(val error: Throwable) : DetailState()
    }

    sealed class DetailEffect : MviEffect {
        sealed class Navigation : DetailEffect() {
            data object BackNavigation : Navigation()
        }
    }

    sealed class DetailResult : MviResult {
        sealed class HitResult : DetailResult() {
            data object InProgress : HitResult()
            data class Success(val hit: Hit) : HitResult()
            data class Error(val error: Throwable) : HitResult()
        }

        data object BackResult : DetailResult()
    }

    sealed class DetailAction : MviAction {

        data class GetHitAction(val id: String) : DetailAction()
        data object BackAction : DetailAction()

    }

}