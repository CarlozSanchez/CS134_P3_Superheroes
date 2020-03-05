/**
 *  Programmer: Sabas Sanchez
 *  File: JSONLoader.java
 *  Date: 3/4/2020
 *  Project 3 - Superheroes
 *
 *  Represents a superheroes with name, power and image name to be used in a quiz app.
 *
 */
package edu.miracosta.cs134.superheroes.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JSONLoader {

    public static final String  JSON_FILE_NAME ="superheroes/cs134superheroes.json";
    private static final String OBJECT = "CS134Superheroes";
    private static final String NAME = "Name";
    private static final String POWER = "Superpower";
    private static final String ONE_THING = "OneThing";
    public static final String FILE_NAME = "FileName";



    public static List<Superhero> loadJSONFromAsset(Context context) throws IOException{

        // Create a list that will hold the hereos from JSON file.
        List<Superhero> allSuperheroes = new ArrayList<Superhero>();

        String json = null;
        InputStream is = context.getAssets().open(JSON_FILE_NAME);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        try{
            JSONObject jsonRootObject = new JSONObject(json);
            JSONArray allSuperheroesJSON = jsonRootObject.getJSONArray(OBJECT);

            JSONObject superheroJSON;
            int count = allSuperheroesJSON.length();

            String name, power, oneThing, fileName;

            // Iterate through JSON array and load heroes to array list
            for (int i = 0; i < count; i++) {
                superheroJSON = allSuperheroesJSON.getJSONObject(i);
                name = superheroJSON.getString(NAME);
                power = superheroJSON.getString(POWER);
                oneThing = superheroJSON.getString(ONE_THING);
                fileName = superheroJSON.getString(FILE_NAME);
                allSuperheroes.add(new Superhero(name, power, oneThing, fileName));
            }

        }catch(JSONException e)
        {
            Log.e("Superheroes", e.getMessage());
        }

        return allSuperheroes;
    }
}
