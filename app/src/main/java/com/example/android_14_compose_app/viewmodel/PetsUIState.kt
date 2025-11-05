package com.example.android_14_compose_app.viewmodel

import com.example.android_14_compose_app.data.Cat

data class PetsUIState(
    val isLoading: Boolean = false,
    val pets: List<Cat> = emptyList(),
    val error: String? = null
)
