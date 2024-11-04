package com.example.pokedexver2.common.entities

data class PokemonRetroFitResponse(val name: String,
                                   val height: Int,
                                   val weight: Int,
                                   val sprites: Sprites)

data class Sprites(val front_default: String)

data class PokemonListResponse(
    val count: Int,
    val countTwo: Int,
    val next: String,
    val previous: String,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)