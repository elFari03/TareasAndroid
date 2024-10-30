package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.Character
import repository.CharacterRepository

class CharacterDetailViewModel(private val characterId: String?) : ViewModel() {
    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    init {
        characterId?.let { fetchCharacterDetails(it) }
    }

    private fun fetchCharacterDetails(id: String) {
        viewModelScope.launch {
            try {
                val characterDetails = CharacterRepository().getCharacterById(id)
                _character.value = characterDetails
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

