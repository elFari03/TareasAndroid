package repository

import models.Character
import network.RetrofitClient
import models.CharacterResponse

class CharacterRepository {
    suspend fun getCharacters(): CharacterResponse {
        return RetrofitClient.apiService.getCharacters()
    }

    suspend fun getCharacterById(id: String): Character {
        return RetrofitClient.apiService.getCharacterById(id)
    }
}
