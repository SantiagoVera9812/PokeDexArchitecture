package com.example.pokedexver2.listPokemonModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedexver2.listPokemonModule.model.ListPokemonsRepository


class ListPokemonViewModelFactory(private val repository: ListPokemonsRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListPokemonViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ListPokemonViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase de view model desconocida")
    }
}