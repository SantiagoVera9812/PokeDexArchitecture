package com.example.pokedexver2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedexver2.common.apiRequest.PokeApiDatabase
import com.example.pokedexver2.common.entities.PokemonNameUI
import com.example.pokedexver2.databinding.ActivityMainBinding
import com.example.pokedexver2.listPokemonModule.ListPokemonViewModel
import com.example.pokedexver2.listPokemonModule.ListPokemonViewModelFactory
import com.example.pokedexver2.listPokemonModule.intent.ListPokemonIntent
import com.example.pokedexver2.listPokemonModule.model.ListPokemonState
import com.example.pokedexver2.listPokemonModule.model.ListPokemonsRepository
import com.example.pokedexver2.listPokemonModule.view.OnPokemonClick
import com.example.pokedexver2.listPokemonModule.view.PokemonListNameAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnPokemonClick {

    private lateinit var vm: ListPokemonViewModel
    private lateinit var bindung : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindung = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindung.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewModel()
        setupRecyclerView()
        setupButtons()
        setupObservers()

    }

    private fun setupObservers() {

        lifecycleScope.launch {
            vm.state.collect{ state ->

                when(state){

                    is ListPokemonState.Init -> {}
                    is ListPokemonState.ShowProgress -> { showProgress(true)}
                    is ListPokemonState.HideProgress -> { showProgress(false)}
                    is ListPokemonState.RequestPokemonPage -> { bindung.pokemonNamesList.adapter = PokemonListNameAdapter(state.list, this){ pokemon ->

                        this.launch {
                            vm.channel.send(ListPokemonIntent.RequestPokemonDetails(pokemon.id))
                        }
                    }}
                    is ListPokemonState.Fail -> {showMsg(Integer.parseInt(state.msg))}
                    is ListPokemonState.RequestPokemonDetails -> {

                        val intent = Intent(baseContext, PokeCardActivity::class.java ).apply {
                            putExtra("Pokemon name", state.pokemon.name)
                            putExtra("Pokemon height", state.pokemon.height)
                            putExtra("Pokemon weight", state.pokemon.weight)
                            putExtra("Pokemon sprite", state.pokemon.sprite)
                        }
                        startActivity(intent)
                    }
                }

            }
        }
    }


    private fun setupButtons() {
        var page = 0

            lifecycleScope.launch {
                vm.channel.send(ListPokemonIntent.RequestPokemonPage(page))
                updateBackButtonVisibility(page)
            }

        with(bindung){
            backButton.setOnClickListener{
                lifecycleScope.launch {
                    page -= 1
                    vm.channel.send(ListPokemonIntent.RequestPokemonPage(page))
                    updateBackButtonVisibility(page)

                }
            }

            nextButton.setOnClickListener{
                lifecycleScope.launch {

                    page += 1
                    vm.channel.send(ListPokemonIntent.RequestPokemonPage(page))
                    updateBackButtonVisibility(page)

                }
            }
        }
    }

    private fun setupRecyclerView() {
        bindung.pokemonNamesList.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel() {
        vm = ViewModelProvider(this, ListPokemonViewModelFactory(ListPokemonsRepository(PokeApiDatabase())))[ListPokemonViewModel::class.java]
    }

    private fun updateBackButtonVisibility(page: Int) {
        with(bindung) {
            if (page == 0) {
                backButton.visibility = View.GONE
            } else {
                backButton.visibility = View.VISIBLE
            }

            if (page == 65) {
                Log.i("page", page.toString())
                nextButton.visibility = View.GONE
            } else {
                nextButton.visibility = View.VISIBLE
            }
        }
    }

    private fun showProgress(isVisible: Boolean) {
        bindung.contentProgress.root.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showMsg(msgRes: Int) {
        Snackbar.make(bindung.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    override fun onPokemonClick(pokemon: PokemonNameUI) {

        Log.i("clicked pokemon", pokemon.toString())
    }

}