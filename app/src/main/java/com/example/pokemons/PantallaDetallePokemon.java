package com.example.pokemons;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PantallaDetallePokemon extends AppCompatActivity {
    ImageView imPokemonDetalle;
    TextView txtNombreDetalle;
    TextView txtNumDetalle;
    TextView txtTiposDetalle;
    TextView txtAlturaDetalle;
    TextView txtPesoDetalle;
    TextView txtDescripcionDetalle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pokemon);

        //Recibimos el Pokemon del Intent


        imPokemonDetalle = findViewById(R.id.imPokemonDetalle);
        txtNombreDetalle = findViewById(R.id.txtNombreDetalle);
        txtNumDetalle = findViewById(R.id.txtNumDetalle);
        txtTiposDetalle = findViewById(R.id.txtTiposDetalle);
        txtAlturaDetalle = findViewById(R.id.txtAlturaDetalle);
        txtPesoDetalle = findViewById(R.id.txtPesoDetalle);
        txtDescripcionDetalle =findViewById(R.id.txtDescripcionDetalle);


        //necesitamos el id para acceder a cada url de cada pokemon
        int id = getIntent().getIntExtra("id", -1);

        //El nombre y la url ya la tenemos
        String name = getIntent().getStringExtra("nombre");
        String urlImagen = getIntent().getStringExtra("urlImagen");


        //Hacemos la llamada a la API
        RequestQueue queue = Volley.newRequestQueue(this);



        //cargamos la imagen con Volley
        if(urlImagen != null){
            ImageRequest imageRequest = new ImageRequest(
                    urlImagen,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            imPokemonDetalle.setImageBitmap(bitmap);
                        }
                    },
                    0,
                    0,
                    null,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            volleyError.printStackTrace();
                        }
                    }
            );

            queue.add(imageRequest);
        }

        //la nueva url con el detalle de cada pokemon
        String urlDetalle = "https://pokeapi.co/api/v2/pokemon/"+id;

        JsonObjectRequest requestDetalle = new JsonObjectRequest(
                Request.Method.GET,
                urlDetalle,
                null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            //Nombre
                            String nombre = response.getString("name").toUpperCase();
                            txtNombreDetalle.setText(nombre);

                            //Altura
                            int altura = response.getInt("height");
                            txtAlturaDetalle.setText((String.valueOf(altura)+ "   decímetros"));

                            //Peso
                            int peso = response.getInt("weight");
                            txtPesoDetalle.setText((String.valueOf(peso)+ "   hectogramos"));

                            //Tipos, es un array
                            JSONArray tipos = response.getJSONArray("types");
                            List<String> listaTipos = new ArrayList<>();

                            for (int i = 0; i < tipos.length(); i++) {
                                listaTipos.add(String.valueOf(tipos.getJSONObject(i)
                                        .getJSONObject("type")
                                        .getString("name")));
                            }

                            txtTiposDetalle.setText(String.join(",", listaTipos));

                            }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    },
                new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();

            }
        }
        );
        queue.add(requestDetalle);

        String urlDescripcion = "https://pokeapi.co/api/v2/pokemon-species/"+id;


        JsonObjectRequest requestDescripcion = new JsonObjectRequest(
                Request.Method.GET,
                urlDescripcion,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //lista de las descripciones
                            JSONArray entradas = response.getJSONArray("flavor_text_entries");

                            String textoFinal = null;
                            //cogemos el texto, índice 0
                            for (int i = 0; i < entradas.length(); i++) {
                                String textoDescripcion = entradas.getJSONObject(i)
                                        .getJSONObject("language")
                                        .getString("name");

                                //lenguaje es
                                if (textoDescripcion.equals("es")) {
                                    textoFinal = entradas.getJSONObject(i)
                                            .getString("flavor_text");

                                    txtDescripcionDetalle.setText(textoFinal);
                                    break;
                                }

                            }
                            //si no existe texto en es
                            if(textoFinal == null){
                                for (int i = 0; i < entradas.length() ; i++) {
                                    JSONObject entrada = entradas.getJSONObject(i);
                                    if ("en".equals(entrada.getJSONObject("language")
                                            .getString("name")))
                                    {
                                        textoFinal = entrada.getString("flavor_text");
                                    }
                                        break;

                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                }
        );


        queue.add(requestDescripcion);
    }
}