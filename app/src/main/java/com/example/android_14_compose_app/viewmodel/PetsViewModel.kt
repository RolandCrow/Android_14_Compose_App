package com.example.android_14_compose_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_14_compose_app.api.NetworkResult
import com.example.android_14_compose_app.api.asResult
import com.example.android_14_compose_app.data.Cat
import com.example.android_14_compose_app.repository.PetsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.PriorityQueue
import java.util.Random

class PetsViewModel(
    private val repository: PetsRepository
): ViewModel() {
    val petsUIState = MutableStateFlow(PetsUIState())
    private val _favoritePets = MutableStateFlow<List<Cat>>(emptyList())
    val favoritePets: StateFlow<List<Cat>> get() = _favoritePets

    init {
        getPets()
    }

    fun getPets() {
        petsUIState.value = PetsUIState(isLoading = true)
        viewModelScope.launch {
            repository.getPets().asResult().collect { result ->
                when(result) {
                    is NetworkResult.Success -> {
                        petsUIState.update {
                            it.copy(isLoading = false, pets = result.data)
                        }
                    }
                    is NetworkResult.Error -> {
                        petsUIState.update {
                            it.copy(isLoading = false, error = result.error)
                        }
                    }
                }
            }
        }
    }

    fun updatePet(cat: Cat) {
        viewModelScope.launch {
            repository.updatePet(cat)
        }
    }

    fun getFavoritePets() {
        viewModelScope.launch {
            repository.getFavoritePets().collect {
                _favoritePets.value = it
            }
        }
    }













}