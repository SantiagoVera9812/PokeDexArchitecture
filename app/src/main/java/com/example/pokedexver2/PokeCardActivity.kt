package com.example.pokedexver2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.pokedexver2.databinding.ActivityPokeCardBinding

class PokeCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokeCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPokeCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pokemonName = intent.getStringExtra("Pokemon name")
        val pokemonSprite = intent.getStringExtra("Pokemon sprite")
        val pokemonHeight = intent.getIntExtra("Pokemon height", 0)
        val pokemonWeight = intent.getIntExtra("Pokemon weight", 0)

        Log.i("chosen pokemon", "${pokemonName} ${pokemonWeight} ${pokemonHeight} ${pokemonSprite}")

        binding.pokemonName.text = pokemonName
        binding.height.text = pokemonHeight.toString()
        binding.weight.text = pokemonWeight.toString()

        Glide.with(this)
            .load(pokemonSprite).override(120, 120).into(binding.imagePoke)

    }
}