package ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import models.Character
import viewmodel.CharacterViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.CardDefaults
import repository.CharacterRepository
import viewmodel.CharacterDetailViewModel
import viewmodel.CharacterDetailViewModelFactory
import androidx.compose.foundation.background


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "character_list") {
                    composable("character_list") {
                        val viewModel = CharacterViewModel(CharacterRepository())
                        CharacterListScreen(navController, viewModel)
                    }
                    composable("character_detail/{characterId}") { backStackEntry ->
                        val characterId = backStackEntry.arguments?.getString("characterId")
                        CharacterDetailScreen(characterId)
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterListScreen(navController: NavController, viewModel: CharacterViewModel = viewModel()) {
    val characterList by viewModel.characters.collectAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(
            text = "Rick And Morty API",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Black
        )


        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))


        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7E0))
        ) {
            items(characterList) { character ->
                CharacterCard(character) {
                    navController.navigate("character_detail/${character.id}")
                }
            }
        }
    }
}
@Composable
fun CharacterCard(character: Character, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF4CAF50))
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberImagePainter(character.image),
                    contentDescription = character.name,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = character.name, style = MaterialTheme.typography.titleMedium, color = Color.Black)
                    Text(text = character.species, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                    Text(text = "Status: ${character.status}", style = MaterialTheme.typography.bodySmall, color = Color.Black)
                }
            }
        }
    }
}
@Composable
fun CharacterDetailScreen(characterId: String?) {

    val viewModel: CharacterDetailViewModel = viewModel(factory = CharacterDetailViewModelFactory(characterId))
    val character by viewModel.character.collectAsState()

    character?.let { character ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = character.name ?: "",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Image(
                painter = rememberImagePainter(character.image),
                contentDescription = character.name,
                modifier = Modifier.size(256.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFA5D6A7))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Species: ${character.species}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp)
                    )
                    Text(
                        text = "Status: ${character.status}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp)
                    )
                    Text(
                        text = "Gender: ${character.gender}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp)
                    )
                    Text(
                        text = "Location: ${character.location?.name}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp)
                    )
                }
            }
        }
    } ?: run {

        CircularProgressIndicator()
    }
}


