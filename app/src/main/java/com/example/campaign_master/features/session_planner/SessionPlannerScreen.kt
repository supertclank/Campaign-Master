package com.example.campaign_master.features.session_planner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SessionPlannerScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Session Planner",
                fontSize = 24.sp
            )
        }
    }
}
