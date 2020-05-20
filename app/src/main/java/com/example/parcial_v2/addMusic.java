package com.example.parcial_v2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.parcial_v2.Data.Music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class addMusic extends AppCompatActivity {
    private Music music;

    private Button btnBuscar;
    private EditText txtBuscar;
    private RequestQueue queue;

    //txt
    private EditText tvCancion;
    private EditText tvArtista;
    private EditText tvAlbum;
    private EditText tvDireccion;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        queue = Volley.newRequestQueue(this);

        txtBuscar = (EditText) findViewById(R.id.edtBuscar);
        tvCancion = (EditText) findViewById(R.id.tvCancion);
        tvArtista = (EditText) findViewById(R.id.tvArtista);
        tvAlbum = (EditText) findViewById(R.id.tvAlbum);
        tvDireccion = (EditText) findViewById(R.id.tvDuracion);
        info = (TextView) findViewById(R.id.info);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetMusic();
            }
        });
    }

    public void addMusic(){
        //con el metodo addMusic() paso el nombre de la cancion al MainActivity, para eso valido que no se pasen datos nulos

        if(tvCancion.getText().toString() != ""){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("NewMusic", tvCancion.getText().toString());
            intent.putExtra("duration", tvDireccion.getText().toString());
            intent.putExtra("autor", tvArtista.getText().toString());
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "not", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_addmusic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save:
                Toast.makeText(getApplicationContext(), "Add music", Toast.LENGTH_LONG).show();
                addMusic();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void GetMusic(){
        String buscar = txtBuscar.getText().toString();
        String URL = "https://api.deezer.com/search?q="+buscar;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArrayData = response.getJSONArray("data");
                            JSONObject jsonObjectMusic = jsonArrayData.optJSONObject(0);
                            String title = jsonObjectMusic.getString("title");
                            int duration = jsonObjectMusic.getInt("duration");
                            JSONObject jsonObjectArtist = jsonObjectMusic.getJSONObject("artist");
                            String autor = jsonObjectArtist.getString("name");

                            tvCancion.setText(title);
                            tvArtista.setText(autor);
                            tvAlbum.setText(title);
                            tvDireccion.setText(duration+"");

                            music = new Music(title, autor, duration+"");

                        } catch (JSONException e) {
                            Log.d(MainActivity.class.getSimpleName(), "error al parsear");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(MainActivity.class.getSimpleName(), "error en el volley responce");
            }
        });
        this.queue.add(jsonObjectRequest);
    }


}
