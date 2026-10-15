package com.example.pokemons;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class PokemonsAdaptador extends RecyclerView.Adapter <PokemonsAdaptador.ViewHolder> {


    List<Pokemon> itemList = new ArrayList<>();

    public PokemonsAdaptador(List<Pokemon> itemList){
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public PokemonsAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_pantalla_pokemons_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonsAdaptador.ViewHolder holder, int position) {

        Pokemon pokemonActual = itemList.get(position);

        holder.tvPokemonsNombre.setText(pokemonActual.getNombre());

        holder.tvPokemonsNombre.setText(pokemonActual.getTipos().getFirst());
        if(pokemonActual.getImagen() != null){
    //si ya tiene la imagen la cargo, sino la pido
            holder.ivPokemons.setImageBitmap(pokemonActual.getImagen());
        }else {
            //creamos una request
            RequestQueue queue = Volley.newRequestQueue(holder.itemView.getContext());
            //cargo la imagen
            ImageRequest imageRequest = new ImageRequest(
                    pokemonActual.getUrlImagen(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {

                            //lo guardo en la lista de pokemons
                            pokemonActual.setImagen(bitmap);
                            //lo pintamos
                            holder.ivPokemons.setImageBitmap(bitmap);

                        }
                    },
                    0,
                    0,
                    //si tuviese que decodificar la imagen
                    null,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }
            );

            queue.add(imageRequest);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivPokemons;
        TextView tvPokemonsNombre;
        TextView tvPokemonsTipo;

        public ViewHolder (@NonNull View itemView){

            super(itemView);

            tvPokemonsNombre = itemView.findViewById(R.id.tvPokemonsNombre);
            ivPokemons = itemView.findViewById(R.id.ivPokemons);
            tvPokemonsTipo = itemView.findViewById(R.id.tvPokemonsTipo);
        }


    }
}
