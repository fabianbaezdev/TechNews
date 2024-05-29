package cl.fabianbaez.technews.ui.screens

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import cl.fabianbaez.technews.factory.HitFactory
import cl.fabianbaez.technews.presentation.detail.DetailMVI
import cl.fabianbaez.technews.presentation.detail.DetailViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {
    @get:Rule(order = 1)
    var composeTestRule = createComposeRule()

    private val viewModel = mockk<DetailViewModel>()

    @Test
    fun errorUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.DetailState.ErrorUiState(
                error = Throwable(
                    ""
                )
            )
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("HitDetail", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("LoadingText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun loadingUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.DetailState.LoadingUiState
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("HitDetail", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun defaultUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.DetailState.DefaultUiState
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("HitDetail", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertExists()
    }

    @Test
    fun displayBeerUiState() {
        val hit = HitFactory.makeHit()
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.DetailState.DisplayHitUiState(hit)
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("HitDetail", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun beerDetailComponentExists() {
        val hit = HitFactory.makeHit()
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.DetailState.DisplayHitUiState(hit)
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithContentDescription("BackIcon").assertExists()
        composeTestRule.onNodeWithTag("BackText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("TitleText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("WebView", useUnmergedTree = true).assertExists()

    }

    @Test
    fun beerDetailCorrectTexts() {
        val hit = HitFactory.makeHit()
        every { viewModel.uiState } returns MutableStateFlow(
            DetailMVI.DetailState.DisplayHitUiState(hit)
        )

        composeTestRule.setContent {
            DetailScreen(viewModel = viewModel) {}
        }

        composeTestRule.onNodeWithTag("TitleText", useUnmergedTree = true)
            .assertTextEquals(hit.title)
    }

}