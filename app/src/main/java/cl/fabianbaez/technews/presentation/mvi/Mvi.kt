package cl.fabianbaez.technews.presentation.mvi


interface MviIntent
interface MviState
interface MviEffect
interface MviResult
interface MviAction

interface MviReducer<TUiState : MviState, TResult : MviResult> {
    infix fun TUiState.reduceWith(result: TResult): TUiState
}
