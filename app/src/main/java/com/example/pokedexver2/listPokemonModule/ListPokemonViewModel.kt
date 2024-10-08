package com.example.pokedexver2.listPokemonModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexver2.listPokemonModule.intent.ListPokemonIntent
import com.example.pokedexver2.listPokemonModule.model.ListPokemonState
import com.example.pokedexver2.listPokemonModule.model.ListPokemonsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ListPokemonViewModel(private val repository: ListPokemonsRepository): ViewModel() {

    private val _state = MutableStateFlow<ListPokemonState>(ListPokemonState.Init)

    val state: StateFlow<ListPokemonState> = _state

    val channel = Channel<ListPokemonIntent> { Channel.UNLIMITED }

    init {
        setUpIntent()
    }

    private fun setUpIntent() {

        viewModelScope.launch{
            channel.consumeAsFlow()
                .collect{ i ->

                    when(i) {

                        is ListPokemonIntent.RequestPokemonPage -> requestPokemon(i.page)
                        is ListPokemonIntent.RequestPokemonDetails -> { requestDetails(i.pokeiD)}
                    }

                }

        }


    }

    private suspend fun requestDetails(pokeiD: Int) {

        _state.value = ListPokemonState.ShowProgress

        try {
            _state.value = repository.requestPokemonDetails(pokeiD)
        } finally {
            _state.value = ListPokemonState.HideProgress
        }

    }

    private suspend fun requestPokemon(page: Int) {

        _state.value = ListPokemonState.ShowProgress

        try {
            _state.value = repository.requestPokemonPage(page)
        } finally {
            _state.value = ListPokemonState.HideProgress
        }

    }
}