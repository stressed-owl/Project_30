package com.delusional_bear.project30

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delusional_bear.project30.data.Challenge
import com.delusional_bear.project30.model.DataSource
import com.delusional_bear.project30.ui.theme.Project30Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project30Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ProjectApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun Project30LightThemePreview() {
    Project30Theme {
        ProjectApp()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            ProjectTopAppBar()
        },
        modifier = modifier,
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            items(DataSource.dataList) { challenge ->
                ProjectCard(challenge = challenge)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = modifier,
                style = MaterialTheme.typography.displaySmall,
            )
        },
    )
}

@Composable
fun ProjectCard(
    modifier: Modifier = Modifier,
    challenge: Challenge,
) {
    var clicked by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (clicked) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer
    )
    val cardCornerRadius by animateDpAsState(
        targetValue = if (clicked) 16.dp else 4.dp
    )
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable { clicked = !clicked },
        shape = RoundedCornerShape(cardCornerRadius),
    ) {
        Box(modifier = Modifier.background(color = color)) {
            if (clicked) {
                Text(
                    text = stringResource(id = challenge.challenge),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            } else {
                Text(
                    text = "Day ${challenge.day}",
                    modifier = modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    style = MaterialTheme.typography.displayLarge,
                )
            }
        }
    }
}