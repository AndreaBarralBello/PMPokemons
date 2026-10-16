package com.example.pokemons;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

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

    public static void cargaInicialPokemons(ProgressBar pbCarga){


        RequestQueue queue = Volley.newRequestQueue(contexto);
        JsonObjectRequest solicitudPokemons = new JsonObjectRequest(
                "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=5000",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray results = jsonObject.getJSONArray("results");

                            //cantidad máxima de elementos
                            pbCarga.setMax(results.length());
                            for (int i = 0; i < results.length(); i++) {
                                pbCarga.setProgress(i);
                                final int pokemonActual = i;
                                JsonObjectRequest solicitudPokemon = new JsonObjectRequest(
                                        results.getJSONObject(i).getString("url"),
                                        new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject jsonObject) {
                                                try {
                                                    String laURL = jsonObject.getJSONObject("sprites")
                                                            .getString("front_default");
                                                    listaPokemons.get(pokemonActual).setUrlImagen(laURL);
                                                    List<String> tipo = new ArrayList<>();
                                                    JSONArray tipos = jsonObject.getJSONArray("types");
                                                    for (int j = 0; j<tipos.length() ; j++) {
                                                        tipo.add(tipos.getJSONObject(j).getJSONObject("type").getString("name"));

                                                    }
                                                    listaPokemons.get(pokemonActual).getTipos(tipo);
                                                } catch (JSONException e) {

                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {

                                            }
                                        }
                                );
                                listaPokemons.add(new Pokemon(results.getJSONObject(i).getString("name"),
                                        i + 1, null, null, null));
                            }
                            Log.i("Andrea", listaPokemons.toString());

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        queue.add(solicitudPokemons);
    }



}
