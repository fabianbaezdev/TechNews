package cl.fabianbaez.technews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cl.fabianbaez.technews.presentation.list.ListMVI
import cl.fabianbaez.technews.presentation.list.ListViewModel
import cl.fabianbaez.technews.ui.screens.ListScreen

@Composable
fun ListScreenDestination(navController: NavController) {
    val viewModel = hiltViewModel<ListViewModel>()
    ListScreen(
        viewModel = viewModel,
    ) { navigationEffect ->
        if (navigationEffect is ListMVI.ListEffect.Navigation.HitDetail) {
            navController.navigateToDetail(navigationEffect.id)
        }
    }
}