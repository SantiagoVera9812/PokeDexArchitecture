package com.example.pokedexver2.listPokemonModule.intent

sealed class ListPokemonIntent {

    data class RequestPokemonPage(val page: Int) : ListPokemonIntent()
    data class RequestPokemonDetails(val pokeiD: Int) : ListPokemonIntent()


}