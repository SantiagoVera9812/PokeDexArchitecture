package com.example.pokedexver2.listPokemonModule.model

import com.example.pokedexver2.common.entities.Pokemon
import com.example.pokedexver2.common.entities.PokemonNameUI

sealed class ListPokemonState {

    data object Init: ListPokemonState()
    data object ShowProgress: ListPokemonState()
    data object HideProgress: ListPokemonState()


    data class Fail(val msg: String): ListPokemonState()

    data class RequestPokemonPage(val list: List<PokemonNameUI>?): ListPokemonState()
    data class RequestPokemonDetails(val pokemon: Pokemon): ListPokemonState()

}