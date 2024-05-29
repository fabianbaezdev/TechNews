package cl.fabianbaez.technews.ui.screens

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.fabianbaez.technews.presentation.detail.DetailMVI
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailEffect.Navigation.BackNavigation
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState.DefaultUiState
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState.DisplayHitUiState
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState.ErrorUiState
import cl.fabianbaez.technews.presentation.detail.DetailMVI.DetailState.LoadingUiState
import cl.fabianbaez.technews.presentation.detail.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onNavigationRequested: (navigationEffect: DetailMVI.DetailEffect.Navigation) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (state) {

        is ErrorUiState -> {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .testTag("ErrorText"),
                text = "Error"
            )
        }

        LoadingUiState -> {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .testTag("LoadingText"),
                text = "Loading"
            )
        }

        DefaultUiState -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("DefaultBox")
            ) {
                CircularProgressIndicator()
            }
        }

        is DisplayHitUiState -> {
            val hit = (state as DisplayHitUiState).hit
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(
                                text = "Back",
                                modifier = Modifier
                                    .testTag("BackText")
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { onNavigationRequested(BackNavigation) }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "BackIcon"
                                )
                            }
                        }
                    )
                },
                modifier = Modifier
                    .testTag("HitDetail")
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        text = hit.title,
                        modifier = Modifier
                            .padding(10.dp)
                            .testTag("TitleText")
                    )
                    AndroidView(factory = {
                        WebView(it).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }
                    }, update = {
                        it.loadUrl(hit.url)
                    }, modifier = Modifier
                        .testTag("WebView")
                    )
                }
            }
        }
    }


}