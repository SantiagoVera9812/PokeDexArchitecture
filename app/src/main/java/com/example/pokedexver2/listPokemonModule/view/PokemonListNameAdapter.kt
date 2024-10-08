package com.example.pokedexver2.listPokemonModule.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexver2.R
import com.example.pokedexver2.common.entities.PokemonNameUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PokemonListNameAdapter(private val pokemonNameUIList: List<PokemonNameUI>?, private val coroutineScope: CoroutineScope, private  val onPokemonClick : OnPokemonClickHandler):
    RecyclerView.Adapter<PokemonListNameAdapter.PokeViewHolder>() {


    inner class PokeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val nombreText: TextView = itemView.findViewById(R.id.pokemonName)
        val idText: TextView = itemView.findViewById(R.id.pokemonId)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_name_int, parent, false)
        return PokeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonNameUIList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {

        val pokemon = pokemonNameUIList?.get(position)
        if (pokemon != null) {
            holder.nombreText.text = pokemon.name
        }
        if (pokemon != null) {
            holder.idText.text = pokemon.id.toString()
        }

        holder.itemView.setOnClickListener{
            coroutineScope.launch {
                if (pokemon != null) {
                    onPokemonClick(pokemon)
                }
            }
        }

    }
}

typealias OnPokemonClickHandler = (PokemonNameUI) -> Unit