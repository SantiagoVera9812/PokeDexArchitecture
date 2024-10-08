package com.example.pokedexver2.common.entities

data class Pokemon(val name: String, val height: Int, val weight: Int, val sprite: String)

data class PokemonNameUI(val name: String, val url: String, val id: Int)