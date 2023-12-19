package com.dicoding.submissionjetpackcompose.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.submissionjetpackcompose.R
import com.dicoding.submissionjetpackcompose.UiState
import com.dicoding.submissionjetpackcompose.ViewModelFactory
import com.dicoding.submissionjetpackcompose.data.di.Injection
import com.dicoding.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme

@Composable
fun DetailScreen(
    heroId: Long,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateBack: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getHeroById(heroId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.listHero.image,
                    data.listHero.name,
                    data.listHero.desc,
                    onBackClick = navigateBack
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun DetailContent(
    image: Int,
    name: String,
    desc: String,
    onBackClick:() -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier){
        Column (
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1F)
        ){
            Box {
                AsyncImage(
                    model = image,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold)
                )
                Text(text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailContentPreview(){
    SubmissionJetpackComposeTheme {
//        DetailContent(
//            R.drawable.supomo,
//            R.string.soepomo.toString(),
//            R.string.soepomo_description.toString(),
//            onBackClick = {},
//        )
    }
}