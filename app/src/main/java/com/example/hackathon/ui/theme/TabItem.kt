package com.example.hackathon.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hackathon.AnalysisScreen
import com.example.hackathon.CalendarScreen
import com.example.hackathon.ProfileScreen

typealias ComposableFun = @Composable ()->Unit

sealed class TabItem(val title:String,val icons:ImageVector, val screens:ComposableFun) {

    object Calender : TabItem(title = "Calendar",icons= Icons.Default.Star, screens = { CalendarScreen()})
    object Analysis: TabItem(title = "Analysis",icons = Icons.Default.Info, screens={ AnalysisScreen()})
    object Profile : TabItem(title = "Profile",icons= Icons.Default.AccountCircle, screens = { ProfileScreen()})


}