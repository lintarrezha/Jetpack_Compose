package com.dicoding.submissionjetpackcompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionjetpackcompose.UiState
import com.dicoding.submissionjetpackcompose.data.model.ListHero
import com.dicoding.submissionjetpackcompose.data.repository.HeroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repository: HeroRepository
):ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ListHero>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<ListHero>>> get() = _uiState
    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllHero() {
        viewModelScope.launch {
            repository.getAllHero().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
                .collect {
                    listHero -> _uiState.value = UiState.Success(listHero)
                }
        }
    }

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            val sortedHero = repository.searchHero(_query.value)
            _uiState.value = UiState.Success(sortedHero)
        }
    }
}