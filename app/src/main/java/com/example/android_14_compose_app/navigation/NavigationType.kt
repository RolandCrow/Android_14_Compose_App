package com.example.android_14_compose_app.navigation

interface NavigationType {
    data object BottomNavigation: NavigationType
    data object NavigationDrawer: NavigationType
    data object NavigationRail: NavigationType
}