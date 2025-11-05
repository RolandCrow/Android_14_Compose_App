package com.example.android_14_compose_app.navigation

sealed interface ContentType {
    data object List: ContentType
    data object ListAndDetail: ContentType
}