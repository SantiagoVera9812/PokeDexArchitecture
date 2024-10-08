package com.example.pokedexver2.listPokemonModule.model

import com.example.pokedexver2.common.apiRequest.PokeApiDatabase
import com.example.pokedexver2.common.entities.Pokemon

class ListPokemonsRepository(private val db: PokeApiDatabase) {

    suspend fun requestPokemonPage(page: Int): ListPokemonState{

        return try {

            val result = db.pokeApiRequest(page)

            ListPokemonState.RequestPokemonPage(result)

        } catch (e: Exception) {

            ListPokemonState.Fail("Error request")
        }

    }

    suspend fun requestPokemonDetails(idInt: Int): ListPokemonState {

        return try {

            val pokemonChoose = db.fetchPokemonDetails(idInt)

            if (pokemonChoose != null) {
                ListPokemonState.RequestPokemonDetails(pokemonChoose)
            } else {
                ListPokemonState.Fail("No pokemon with that id")
            }

        } catch (e: Exception){
            ListPokemonState.Fail("Fail fetch details")
        }
    }
}