package com.example.pokemons;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PokemonGestor {

    static Context contexto;
    static List<Pokemon> listaPokemons = new ArrayList<>();

    public static void cargaInicialPokemons(){

        RequestQueue queue = Volley.newRequestQueue(contexto);

        JsonObjectRequest solicitudPokemons = new JsonObjectRequest(
                "https://pokeapi.co/api/v2/pokemon/", // Nota: Bajamos a 151 para evitar que se sature la red local en las pruebas
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray results = jsonObject.getJSONArray("results");
                            for(int i = 0; i < results.length(); i++){
                                final int pokemonActual = i;

                                // CORRECCIÓN 1: Añadimos primero el Pokémon base a la lista para asegurar su posición en memoria
                                listaPokemons.add(new Pokemon(results.getJSONObject(i).getString("name"), i + 1, null, null, null));

                                JsonObjectRequest solicitudPokemon = new JsonObjectRequest(
                                        results.getJSONObject(i).getString("url"),
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject respuestaDetalle) { // CORRECCIÓN 2: Cambiamos a 'respuestaDetalle' para evitar conflicto de nombres
                                                try {
                                                    // Extraemos la URL de la imagen
                                                    String laURL = respuestaDetalle.getJSONObject("sprites").getString("front_default");
                                                    listaPokemons.get(pokemonActual).setUrlImagen(laURL);

                                                    // CORRECCIÓN 3: Extraemos y guardamos la lista de tipos para tu app
                                                    List<String> tipo = new ArrayList<>();
                                                    JSONArray tipos = respuestaDetalle.getJSONArray("types");
                                                    for (int j = 0; j < tipos.length(); j++) {
                                                        tipo.add(tipos.getJSONObject(j).getJSONObject("type").getString("name"));
                                                    }
                                                    listaPokemons.get(pokemonActual).setTipos(tipo);

                                                } catch (JSONException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                Log.e("Andrea", "Error en subpetición: " + volleyError.getMessage());
                                            }
                                        }
                                );

                                // CORRECCIÓN 4: Añadimos la subpetición a la cola (La línea que le faltaba al profesor para activar internet)
                                queue.add(solicitudPokemon);
                            }
                            Log.i("Moncho", listaPokemons.toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Andrea", "Error petición general: " + volleyError.getMessage());
                    }
                }
        );

        queue.add(solicitudPokemons);
    }
}
