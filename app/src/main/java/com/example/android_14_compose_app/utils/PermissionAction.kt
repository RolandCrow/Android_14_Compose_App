package com.example.android_14_compose_app.utils

sealed class PermissionAction {
    data object PermissionGranted: PermissionAction()
    data object PermissionDenied: PermissionAction()
}