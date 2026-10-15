package com.example.pokemons;

import android.graphics.Bitmap;

import java.util.List;

public class Pokemon {

    String nombre;
    int numero;
    List<String> habilidades;
    List<String> tipos;
    String urlImagen;
    //bitmap es la imagen en bits
    //pero no se carga la imagen al crear el objeto(tardaría mucho) pq son muchos objetos
    //se descarga la imagen cuando se muestra por pantalla
    Bitmap imagen;

    public Pokemon(String nombre, int numero, List<String> habilidades, List<String> tipos, String urlImagen) {
        this.nombre = nombre;
        this.numero = numero;
        this.habilidades = habilidades;
        this.tipos = tipos;
        this.urlImagen = urlImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }

    public List<String> getTipos() {
        return tipos;
    }

    public void setTipos(List<String> tipos) {
        this.tipos = tipos;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }


    @Override
    public String toString() {
        return "#" + this.numero + " - " + this.nombre;
    }
}
