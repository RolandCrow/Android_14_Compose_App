package com.example.android_14_compose_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android_14_compose_app.screens.FavoritePetsScreen
import com.example.android_14_compose_app.screens.PetDetailsScreen
import com.example.android_14_compose_app.screens.PetsScreen
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(
    contentType: ContentType,
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.PetScreen.route
    ) {
       composable(Screens.PetScreen.route) {
           PetsScreen(
               onPetClicked = { cat ->
                   navHostController.navigate(
                       "${Screens.FavoritePetsScreen.route}/${Json.encodeToString(cat)}"
                   )
               },
               contentType = contentType
           )
       }
        composable(
            route = "${Screens.PetDetailScreen.route}/{cat}",
            arguments = listOf(
                navArgument("cat") {
                    type = NavType.StringType
                }
            )
        ) {
            PetDetailsScreen(
                onBackPressed = {
                    navHostController.popBackStack()
                },
                cat = Json.decodeFromString(it.arguments?.getString("cat") ?: "")
            )
        }
        composable(Screens.FavoritePetsScreen.route) {
            FavoritePetsScreen(
                onPetsClicked = { cat->
                    navHostController.navigate(
                        "${Screens.PetDetailScreen.route}/{${Json.encodeToString(cat)}}"
                    )
                }
            )
        }
    }
}