package com.example.pokedexver2.common.apiRequest

import android.util.Log
import com.example.pokedexver2.common.entities.Pokemon
import com.example.pokedexver2.common.entities.PokemonNameUI
import com.example.pokedexver2.common.retrofit.RetroFitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class PokeApiDatabase {

    suspend fun pokeApiRequest(page: Int): List<PokemonNameUI>? {

        return withContext(Dispatchers.IO) {
            try {
                val offset = page * 20
                val response = RetroFitClient.instance.getPokemonList(offset, 20)
                Log.i("Pokemon page result", response.toString())

                val pokemonList = response.results.map { result ->
                    val id: Int = extractPokemonId(result.url)?.toInt() ?: -1
                    PokemonNameUI(result.name, result.url, id)
                }

                return@withContext pokemonList
            } catch (e: Exception) {
                Log.e("API Request", "Network exception occurred: ${e.message}", e)
                return@withContext null
            }
        }

//        return withContext(Dispatchers.IO) {
//            try {
//
//                val offset = page * 20
//
//                var pokemonList: List<PokemonNameUI> = ArrayList()
//                val responseCode: Int
//
//                val url = URL("https://pokeapi.co/api/v2/pokemon?offset=${offset}&limit=20")
//                val connection = url.openConnection() as HttpURLConnection
//
//                connection.requestMethod = "GET"
//
//                responseCode = connection.responseCode
//
//                var jsonResponse: JSONObject? = null
//
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    val inputStream = connection.inputStream
//                    val response = inputStream.bufferedReader().use { it.readText() }
//
//                    jsonResponse = JSONObject(response)
//                    val resultsArray: JSONArray = jsonResponse.getJSONArray("results")
//                    Log.i("results", resultsArray.toString())
//
//                    for (i in 0 until resultsArray.length()) {
//
//                        val pokemonObject = resultsArray.getJSONObject(i)
//                        val name = pokemonObject.getString("name")
//                        val urlPoke = pokemonObject.getString("url")
//                        val id: Int = extractPokemonId(urlPoke)?.toInt() ?: -1
//                        val newUiIns = PokemonNameUI(name, urlPoke, id)
//                        (pokemonList as ArrayList).add(newUiIns)
//
//
//                        Log.i("pokemon name ui", newUiIns.toString())
//                    }
//
//                    inputStream.close()
//
//                } else {
//                    Log.e("API Request", "Error: $responseCode")
//
//                }
//                connection.disconnect()
//
//                return@withContext pokemonList
//            } catch (e: Exception) {
//                Log.e("API Request", "Network exception occurred: ${e.message}", e)
//                return@withContext null
//            }
    //}


    }

    fun extractPokemonId(url: String): String? {
        val pathSegments = URL(url).path.split("/")
        return pathSegments.find { it.isNotEmpty() && it.all { char -> char.isDigit() } }
    }

    suspend fun fetchPokemonDetails(id: Int): Pokemon? {

        return withContext(Dispatchers.IO) {
            try {
                val response = RetroFitClient.instance.getPokemonDetails(id)

                Pokemon(response.name, response.height, response.weight, response.sprites.front_default)
            } catch (e: Exception) {
                Log.e("Pokemon Details", "Error fetching Pokemon details: ${e.message}")
                null
            }
        }
//        return withContext(Dispatchers.IO) {
//            try {
//
//                val url = URL("https://pokeapi.co/api/v2/pokemon/$id")
//                val connection = url.openConnection() as HttpURLConnection
//                connection.requestMethod = "GET"
//                val responseCode = connection.responseCode
//
//                var newPokemon: Pokemon? = null
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    val inputStream = connection.inputStream
//                    val response = inputStream.bufferedReader().use { it.readText() }
//
//                    val jsonResponse = JSONObject(response)
//                    val name = jsonResponse.getString("name");
//                    val height = jsonResponse.getString("height")
//                    val weight = jsonResponse.getString("weight")
//                    val sprites = jsonResponse.getJSONObject("sprites")
//                    val frontDefault = sprites.getString("front_default")
//
//                    newPokemon = Pokemon(name, height.toInt(), weight.toInt(), frontDefault)
//
//                    inputStream.close()
//                } else {
//                    Log.e("Pokemon Details", "Error: $responseCode")
//                }
//
//                connection.disconnect()
//                return@withContext newPokemon
//            } catch (e: IOException) {
//                // Handle network-related exception here
//                Log.e("Pokemon Details", "Network exception occurred: ${e.message}")
//                return@withContext null
//            }
//        }
    }
}