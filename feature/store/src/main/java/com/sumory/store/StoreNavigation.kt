package com.sumory.store

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sumory.store.view.StoreScreen

const val storeRoute = "storeRoute"

fun NavGraphBuilder.storeScreen() {
    composable(storeRoute) {
        StoreScreen()
    }
}