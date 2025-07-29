package com.example.campaign_master

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.campaign_master.navigation.NavBottomSheet
import com.example.campaign_master.navigation.NavGraph
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignMasterApp() {
    val navController = rememberNavController()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { coroutineScope.launch { sheetState.show() } }
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 72.dp)
        ) {
            NavGraph(navController = navController)
        }

        if (sheetState.isVisible) {
            ModalBottomSheet(
                onDismissRequest = { coroutineScope.launch { sheetState.hide() } },
                sheetState = sheetState
            ) {
                NavBottomSheet(
                    onNavigate = { route ->
                        navController.navigate(route)
                        coroutineScope.launch { sheetState.hide() }
                    }
                )
            }
        }
    }
}