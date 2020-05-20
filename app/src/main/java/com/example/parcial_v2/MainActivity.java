package com.example.parcial_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.parcial_v2.Data.Adapter;
import com.example.parcial_v2.Data.Music;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView RecyclerView;
    private Adapter adapterMusic;
    private ArrayList<Music> musics;

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String USGS_REQUEST_URL = "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=b284db959637031077380e7e2c6f2775&format=json";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musics = new ArrayList<Music>();

        if(getIntent().getStringExtra("NewMusic") != null){
            //capturo los datos que envio desde el otro activity
            String name = getIntent().getStringExtra("NewMusic");
            String duration = getIntent().getStringExtra("duration");
            String autor = getIntent().getStringExtra("autor");

            //los agrego al arraylist que me muestra en el RecyclerView
            add_new_music(new Music(name, autor, duration));
        }

        queue = Volley.newRequestQueue(this);
        get_Music_Data();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_music();
            }
        });
    }

    public void add_new_music(Music music){
        Music.Musicas.MusicStatic.add(music);
        for (Music musica : Music.Musicas.MusicStatic){
            musics.add(musica);
        }
    }

    public void add_music(){
        Toast.makeText(getApplicationContext(), "Add music", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, addMusic.class);
        startActivity(intent);
    }

    private void get_Music_Data(){
        JsonObjectRequest request  = new JsonObjectRequest(Request.Method.GET, USGS_REQUEST_URL, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //code of return the api
                        try {
                            RecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
                            RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                            JSONObject jsonObjectContacts = response.getJSONObject("tracks");
                            JSONArray jsonArrayTrack = jsonObjectContacts.getJSONArray("track");

                            for(int i=0; i < jsonArrayTrack.length(); i++){

                                JSONObject JsonObjectMusic = jsonArrayTrack.getJSONObject(i);
                                String name = JsonObjectMusic.getString("name");
                                String duration = JsonObjectMusic.getString("duration");

                                //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                                musics.add(new Music(name,"", duration ));
                            }

                            adapterMusic = new Adapter(musics);
                            RecyclerView.setAdapter(adapterMusic);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //code in error
                Log.d(LOG_TAG, "error en volley");
                error.printStackTrace();
            }
        });
        this.queue.add(request);
    }


}
