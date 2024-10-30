package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.Character
import repository.CharacterRepository

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            try {
                _characters.value = repository.getCharacters().results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
