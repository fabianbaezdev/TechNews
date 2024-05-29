package cl.fabianbaez.technews.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.fabianbaez.technews.domain.model.Hit
import cl.fabianbaez.technews.presentation.list.ListMVI.ListEffect.Navigation
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.DefaultUiState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.DisplayListUiState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.ErrorUiState
import cl.fabianbaez.technews.presentation.list.ListMVI.ListState.LoadingUiState
import cl.fabianbaez.technews.presentation.list.ListViewModel
import cl.fabianbaez.technews.utils.DateUtils


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    viewModel: ListViewModel, onNavigationRequested: (navigationEffect: Navigation) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val refreshing = viewModel.isRefreshing.collectAsState().value
    val refreshState = rememberPullRefreshState(refreshing, viewModel::refresh)

    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text("Tech News")
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when (state) {
                is DisplayListUiState -> {
                    val hits = (state as DisplayListUiState).hits

                    Box(
                        Modifier
                            .pullRefresh(refreshState)
                            .testTag("NewsContainer")
                    ) {

                        LazyColumn(modifier = Modifier.testTag("NewsList")) {
                            itemsIndexed(hits) { index, hit ->

                                val dismissState = remember(hits) {
                                    DismissState(DismissValue.Default) {
                                        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                            viewModel.removeHit(hit)
                                            true
                                        } else false
                                    }
                                }

                                SwipeToDelete(state = dismissState) {
                                    Card(onClick = {
                                        onNavigationRequested(Navigation.HitDetail(id = hit.id))
                                    }) {
                                        CardHit(hit)
                                    }
                                }
                                if (index < (hits.lastIndex ?: 0))
                                    HorizontalDivider(color = Color.Gray, thickness = 1.dp)
                            }
                        }

                        PullRefreshIndicator(
                            refreshing,
                            refreshState,
                            Modifier.align(Alignment.TopCenter)
                        )

                    }
                }

                is ErrorUiState -> {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .testTag("ErrorText"), text = "Error"
                    )
                }

                LoadingUiState -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("LoadingBox")
                    ) {
                        CircularProgressIndicator()
                    }
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
            }
        }
    }
}

@Composable
fun CardHit(hit: Hit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = hit.title,
            modifier = Modifier
                .padding(0.dp, 4.dp)
                .testTag("TitleText"),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )
        Row {
            Text(
                text = hit.author + " - " + DateUtils.getDifference(hit.createdAt),
                modifier = Modifier
                    .testTag("UnderText"),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDelete(
    state: DismissState,
    content: @Composable RowScope.() -> Unit
) {
    SwipeToDismiss(
        state = state,
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
        },
        background = {
            val color by animateColorAsState(
                when (state.targetValue) {
                    DismissValue.Default -> Color.LightGray.copy(alpha = 0.5f)
                    else -> Color.Red
                }
            )

            val scale by animateFloatAsState(
                if (state.targetValue == DismissValue.Default) 0.75f else 1f
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove item",
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = content
    )
}