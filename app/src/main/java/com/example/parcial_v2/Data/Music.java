package com.example.parcial_v2.Data;

import java.util.ArrayList;

public class Music {

    public String title;
    public String autor;
    public String duration;

    public Music(String title, String autor, String duration){
        this.autor = autor;
        this.title = title;
        this.duration = duration;
    }


    public static class Musicas{
        //mi variable statica para registrar las nuevas canciones a la playlist
        public static ArrayList<Music> MusicStatic = new ArrayList<Music>();
    }
}
