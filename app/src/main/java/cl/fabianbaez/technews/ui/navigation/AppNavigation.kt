package cl.fabianbaez.technews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.fabianbaez.technews.ui.navigation.Navigation.Args.HIT_ID

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Navigation.Routes.LIST
    ) {
        composable(
            route = Navigation.Routes.LIST
        ) {
            ListScreenDestination(navController)
        }

        composable(
            route = Navigation.Routes.HIT
        ) {
            DetailScreenDestination(navController)
        }
    }
}

object Navigation {

    object Args {
        const val HIT_ID = "hit_id"
    }

    object Routes {
        const val LIST = "list"
        const val HIT = "$LIST/{$HIT_ID}"
    }

}

fun NavController.navigateToDetail(id: String) {
    navigate(route = "${Navigation.Routes.LIST}/$id")
}