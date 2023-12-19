package com.dicoding.submissionjetpackcompose.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.submissionjetpackcompose.UiState
import com.dicoding.submissionjetpackcompose.ViewModelFactory
import com.dicoding.submissionjetpackcompose.data.di.Injection
import com.dicoding.submissionjetpackcompose.data.model.ListHero
import com.dicoding.submissionjetpackcompose.ui.component.Item
import com.dicoding.submissionjetpackcompose.ui.component.Search

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateToDetail: (Long) -> Unit,
){
    val query by viewModel.query
    Box(modifier = modifier.fillMaxSize()) {
        Column {
            Search(query = query, onQueryChange = viewModel::search, modifier = Modifier)
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        viewModel.getAllHero()
                    }
                    is UiState.Success -> {
                        ListHero(
                            listHero = uiState.data,
                            modifier = modifier,
                            navigateToDetail = navigateToDetail,
                        )
                    }
                    is UiState.Error -> {

                    }
                }
            }
        }
    }
}

@Composable
fun ListHero(
    listHero: List<ListHero>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    Box(modifier = modifier) {
        LazyColumn{
            items(listHero) { data ->
                Item(
                    image = data.listHero.image,
                    name = data.listHero.name,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.listHero.id)
                    }
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}