package cl.fabianbaez.technews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cl.fabianbaez.technews.presentation.detail.DetailMVI
import cl.fabianbaez.technews.presentation.detail.DetailViewModel
import cl.fabianbaez.technews.ui.screens.DetailScreen

@Composable
fun DetailScreenDestination(navController: NavController) {
    val viewModel = hiltViewModel<DetailViewModel>()
      DetailScreen(
          viewModel = viewModel,
      ) { navigationEffect ->
          if (navigationEffect is DetailMVI.DetailEffect.Navigation.BackNavigation) {
              navController.popBackStack()
          }
      }
}