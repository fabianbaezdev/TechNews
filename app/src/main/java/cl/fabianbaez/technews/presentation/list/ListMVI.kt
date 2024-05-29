package cl.fabianbaez.technews.presentation.list

import cl.fabianbaez.technews.domain.model.Hit
import cl.fabianbaez.technews.presentation.mvi.MviAction
import cl.fabianbaez.technews.presentation.mvi.MviEffect
import cl.fabianbaez.technews.presentation.mvi.MviIntent
import cl.fabianbaez.technews.presentation.mvi.MviResult
import cl.fabianbaez.technews.presentation.mvi.MviState

class ListMVI {
    sealed class ListIntent : MviIntent {
        data object ReloadHitsIntent : ListIntent()
        data object GetHitsIntent : ListIntent()
        data class HitClicked(val id: String) : ListIntent()
        data class RemoveHit(val hit: Hit) : ListIntent()
    }


    sealed class ListState : MviState {
        data object DefaultUiState : ListState()
        data object LoadingUiState : ListState()
        data class DisplayListUiState(val hits: List<Hit>) : ListState()
        data class ErrorUiState(val error: Throwable) : ListState()
    }

    sealed class ListEffect : MviEffect {
        sealed class Navigation : ListEffect() {
            data class HitDetail(val id: String) : Navigation()
        }
    }

    sealed class ListResult : MviResult {

        sealed class GetHitsResult : ListResult() {
            data object InProgress : GetHitsResult()
            data class Success(val hits: List<Hit>) : GetHitsResult()
            data class Error(val error: Throwable) : GetHitsResult()
        }

        sealed class HideHitsResult : ListResult() {
            data object InProgress : HideHitsResult()
            data class Success(val hits: List<Hit>) : HideHitsResult()
            data class Error(val error: Throwable) : HideHitsResult()
        }

        data class DetailResult(val id: String) : ListResult()
    }

    sealed class ListAction : MviAction {

        data object GetHitsAction : ListAction()
        data class HitDetailAction(val id: String) : ListAction()
        data class HideHitAction(val hit: Hit) : ListAction()

    }
}