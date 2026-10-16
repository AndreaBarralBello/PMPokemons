package com.example.pokemons;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdaptador extends RecyclerView.Adapter <PokemonAdaptador.ViewHolder> {


    List<Pokemon> itemList = new ArrayList<>();

    public PokemonAdaptador (List<Pokemon> itemList){
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public PokemonAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_pantalla_pokemons_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdaptador.ViewHolder holder, int position) {

        holder.tvPokemonsNombre.setText(itemList.get(position).getNombre());
        Log.e("Andrea", itemList.get(position).getNombre());
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
