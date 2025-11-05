package com.example.android_14_compose_app.navigation

sealed class Screens(val route: String) {
    data object PetScreen: Screens("pets")
    data object PetDetailScreen: Screens("petDetails")
    data object FavoritePetsScreen: Screens("favorite")
}