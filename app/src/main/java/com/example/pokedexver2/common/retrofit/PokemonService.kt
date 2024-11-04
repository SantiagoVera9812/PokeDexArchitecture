package com.example.pokedexver2.common.retrofit

import com.example.pokedexver2.common.entities.PokemonListResponse
import com.example.pokedexver2.common.entities.PokemonNameUI
import com.example.pokedexver2.common.entities.PokemonRetroFitResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): PokemonRetroFitResponse

    @GET("api/v2/pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponse
}