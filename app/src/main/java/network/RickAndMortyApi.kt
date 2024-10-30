package network

import models.CharacterResponse
import models.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): Character

}
