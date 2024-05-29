package cl.fabianbaez.technews.ui.screens

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import cl.fabianbaez.technews.factory.HitFactory.makeHit
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.DefaultUiState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.DisplayListUiState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.ErrorUiState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.LoadingUiState
import cl.fabianbaez.technews.presentation.list.ListViewModel
import cl.fabianbaez.technews.utils.DateUtils
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class ListScreenKtTest {

    @get:Rule(order = 1)
    var composeTestRule = createComposeRule()

    private val viewModel = mockk<ListViewModel>()

    @Test
    fun displayListUiState() {
        val hits = listOf(makeHit())

        every { viewModel.uiState } returns MutableStateFlow(
            DisplayListUiState(hits)
        )
        every { viewModel.isRefreshing } returns MutableStateFlow(false)
        composeTestRule.setContent {
            ListScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("NewsList", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingBox", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun errorUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            ErrorUiState(error = Throwable(""))
        )
        every { viewModel.isRefreshing } returns MutableStateFlow(
            false
        )
        composeTestRule.setContent {
            ListScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("NewsList", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("LoadingBox", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun loadingUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            LoadingUiState
        )
        every { viewModel.isRefreshing } returns MutableStateFlow(false)
        composeTestRule.setContent {
            ListScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("NewsList", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingBox", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun defaultUiState() {
        every { viewModel.uiState } returns MutableStateFlow(
            DefaultUiState
        )
        every { viewModel.isRefreshing } returns MutableStateFlow(false)
        composeTestRule.setContent {
            ListScreen(viewModel = viewModel) {}
        }
        composeTestRule.onNodeWithTag("NewsList", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("ErrorText", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("LoadingBox", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("DefaultBox", useUnmergedTree = true).assertExists()
    }

    @Test
    fun cardBeerComponentExists() {
        val hit = makeHit()
        composeTestRule.setContent {
            CardHit(hit = hit)
        }
        composeTestRule.onNodeWithTag("TitleText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("UnderText", useUnmergedTree = true).assertExists()
    }

    @Test
    fun cardBeerCorrectTexts() {
        val hit = makeHit()
        composeTestRule.setContent {
            CardHit(hit = hit)
        }

        composeTestRule.onNodeWithTag("TitleText", useUnmergedTree = true)
            .assertTextEquals(hit.title)
        composeTestRule.onNodeWithTag("UnderText", useUnmergedTree = true)
            .assertTextEquals(hit.author + " - " + DateUtils.getDifference(hit.createdAt))
    }

}