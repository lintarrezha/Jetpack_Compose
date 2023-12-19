package com.dicoding.submissionjetpackcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionjetpackcompose.UiState
import com.dicoding.submissionjetpackcompose.data.model.ListHero
import com.dicoding.submissionjetpackcompose.data.repository.HeroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: HeroRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ListHero>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ListHero>> get() = _uiState

    fun getHeroById(heroId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getHeroById(heroId))
        }
    }
}