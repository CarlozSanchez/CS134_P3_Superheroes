package edu.miracosta.cs134.superheroes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import edu.miracosta.cs134.superheroes.model.JSONLoader;
import edu.miracosta.cs134.superheroes.model.Superhero;

public class MainActivity extends AppCompatActivity {

    private List<Superhero> superheroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            superheroList = JSONLoader.loadJSONFromAsset(this);
        }catch(IOException e)
        {
            Log.e("Superhero Main", e.getMessage());
        }

        for (Superhero superhero:superheroList){
            Log.i("superheroes", superhero.toString());
        }
    }
}
