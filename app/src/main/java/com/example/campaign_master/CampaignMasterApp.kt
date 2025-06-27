package com.example.campaign_master

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campaign_master.theme.CampaignMasterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignMasterApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campaign Master") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
            }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Text(
            text = "Welcome, Dungeon Master!",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCampaignMasterApp() {
    CampaignMasterTheme {
        CampaignMasterApp()
    }
}