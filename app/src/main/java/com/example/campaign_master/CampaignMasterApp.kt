package com.example.campaign_master

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.campaign_master.navigation.NavGraph
import com.example.campaign_master.navigation.NavBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignMasterApp() {
    val navController = rememberNavController()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Campaign Master")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showSheet = true }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavGraph(navController = navController)
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState
            ) {
                NavBottomSheet(
                    onNavigate = { route ->
                        navController.navigate(route)
                        showSheet = false
                    }
                )
            }
        }
    }
}